package com.rental.Inventory.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import com.rental.Inventory.dto.request.RentalsRequestDto;
import com.rental.Inventory.dto.response.RentalsResponseDto;
import com.rental.Inventory.entity.*;
import com.rental.Inventory.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.Inventory.service.RentalsService;

@Service
public class RentalsServiceImpl implements RentalsService {

    private final RentalRepository rentalsRepository;
    private final UserRepository usersRepository;
    private final ProductRepository productRepository;
    private final RentalDetailRepository detailRepository;
    private final StockMovementRepository stockMovementRepository;
    private final HistoryRepository historyRepository;

    public RentalsServiceImpl(
            RentalRepository rentalsRepository,
            UserRepository usersRepository,
            ProductRepository productRepository,
            RentalDetailRepository rentalDetailRepository,
            StockMovementRepository stockMovementRepository,
            HistoryRepository historyRepository
    ) {

        this.rentalsRepository = rentalsRepository;
        this.usersRepository = usersRepository;
        this.productRepository = productRepository;
        this.detailRepository = rentalDetailRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.historyRepository = historyRepository;

    }

    @Override
    public RentalsResponseDto create(RentalsRequestDto rental) {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        var username = user.getName();

        Users cashier = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Cashier not found"));

        var product = productRepository.findById(rental.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        var rentals = new Rentals();
        rentals.setUsers(cashier);
        rentals.setRentalDate(toLocalDateTime(rental.rentalDate()));
        rentals.setEndDate(toLocalDateTime(rental.endDate()));
        rentals.setStatus("ONGOING");
        rentals.setTotalAmount(product.getPurchasePrice().multiply(new BigDecimal(rental.quantity())).multiply(new BigDecimal(calculateDays(rentals.getRentalDate(), rentals.getEndDate()))));
        rentals.setRenterName(rental.renterName());
        rentals.setRenterPhone(rental.renterPhone());

        long invoice;
        do {
            invoice = 100000 + new Random().nextInt(900000);
        } while (rentalsRepository.existsByInvoiceNumber(invoice));

        rentals.setInvoiceNumber(invoice);

        rentalsRepository.save(rentals);

        RentalDetail detail = new RentalDetail();
        detail.setRentals(rentals);
        detail.setPrice(productRepository.findById(rental.productId()).orElseThrow(() -> new RuntimeException("Product not found")).getPrice());
        detail.setStatus("UNPAID");
        detail.setProducts(productRepository.findById(rental.productId()).orElseThrow(() -> new RuntimeException("Product not found")));
        detail.setQuantity(rental.quantity());
        detail.setSubtotal(rentals.getTotalAmount());

        detailRepository.save(detail);


        var qty = product.getStock();
        product.setStock(product.getStock() - rental.quantity());
        if (product.getStock() < 0){
            product.setStock(qty);
            productRepository.save(product);
            throw new RuntimeException("insufficient stock");
        }
        productRepository.save(product);

        StockMovements sm = new StockMovements();
        sm.setMovementType("OUT");
        sm.setRefType("RENTAL");
        sm.setRefId(rentals.getInvoiceNumber());
        sm.setQuantity(rental.quantity());
        sm.setNote("Rental created");
        sm.setCreateAt(LocalDateTime.now());
        sm.setUsers(cashier);

        stockMovementRepository.save(sm);

        History history = new History();
        history.setInvoiceNumber(invoice);
        history.setQty(rental.quantity());
        history.setPriceOfProduct(product.getPrice());
        history.setProductName(product.getName());
        history.setProductCategory(product.getCategory().getName());
        history.setStatusPayment("UNPAID");
        history.setStatusProduct("ONGOING");
        history.setSubtotalPrice(rental.totalAmount());
        history.setRentalDate(toLocalDateTime(rental.rentalDate()));

        historyRepository.save(history);

        return toRentalsResponseDto(rentals);
    }

    @Override
    public List<RentalsResponseDto> getAll() {
        return rentalsRepository
                .findAll()
                .stream()
                .map(this::toRentalsResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public RentalsResponseDto canceled(String id) {

        Rentals rental = rentalsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        rental.setStatus("CANCELED");
        rental.setReturnDate(LocalDateTime.now());

        RentalDetail detail = detailRepository.findByRentals(rental).orElseThrow(() -> new RuntimeException("Rental Not Found"));
        detail.setStatus("CANCELED");
        detailRepository.save(detail);

        var product = detail.getProducts();
        product.setStock(product.getStock() + detail.getQuantity());
        productRepository.save(product);

        StockMovements sm = new StockMovements();
        sm.setMovementType("IN");
        sm.setRefType("RETURN");
        sm.setRefId(rental.getInvoiceNumber());
        sm.setQuantity(detail.getQuantity());
        sm.setNote("Rental returned");
        sm.setCreateAt(LocalDateTime.now());
        sm.setUsers(rental.getUsers());

        stockMovementRepository.save(sm);

        History history = historyRepository.findByInvoiceNumber(rental.getInvoiceNumber());
        history.setPriceOfProduct(product.getPrice());
        history.setProductName(product.getName());
        history.setProductCategory(product.getCategory().getName());
        history.setStatusPayment("CANCELED");
        history.setStatusProduct("CANCELED");

        historyRepository.save(history);

        return toRentalsResponseDto(rentalsRepository.save(rental));
    }

    @Override
    public void delete(String id) {
        Rentals rental = rentalsRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));
        RentalDetail detail = detailRepository.findByRentals(rental).orElseThrow(() -> new RuntimeException("Rental not found"));
        rentalsRepository.delete(rental);
    }

    @Override
    public RentalsResponseDto returned(long invoiceId) {
        Rentals rentals = rentalsRepository.findByInvoiceNumber(invoiceId).orElseThrow(() -> new RuntimeException("Rental not found"));
        rentals.setReturnDate(LocalDateTime.now());
        rentals.setStatus("RETURN");
        rentalsRepository.save(rentals);

        RentalDetail detail = detailRepository.findByRentals(rentals)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        var product = detail.getProducts();
        product.setStock(product.getStock() + detail.getQuantity());
        productRepository.save(product);

        StockMovements sm = new StockMovements();
        sm.setMovementType("IN");
        sm.setRefType("RETURN");
        sm.setRefId(invoiceId);
        sm.setQuantity(detail.getQuantity());
        sm.setNote("Rental returned");
        sm.setCreateAt(LocalDateTime.now());
        sm.setUsers(rentals.getUsers());

        stockMovementRepository.save(sm);

        History history = historyRepository.findByInvoiceNumber(invoiceId);
        history.setStatusPayment("RETURN");
        history.setStatusProduct("RETURN");

        historyRepository.save(history);

        return toRentalsResponseDto(rentals);
    }

    @Override
    public RentalsResponseDto payment(String id) {
        Rentals rentals = rentalsRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));
        rentals.setStatus("ONRENT");
        rentalsRepository.save(rentals);

        RentalDetail detail = detailRepository.findByRentals(rentals).orElseThrow(() -> new RuntimeException("Rental not found"));
        detail.setStatus("PAID");
        detailRepository.save(detail);

        History history = historyRepository.findByInvoiceNumber(rentals.getInvoiceNumber());
        history.setStatusPayment("PAID");
        history.setStatusProduct("ONRENT");
        historyRepository.save(history);

        return toRentalsResponseDto(rentals);
    }

    @Override
    public RentalsResponseDto getById(String id) {
        return toRentalsResponseDto(rentalsRepository.findById(id).orElseThrow(() -> {throw new RuntimeException("Rental not found");}));
    }

    @Override
    public List<RentalsResponseDto> getByStatus() {
        return detailRepository.findByRentalAndDetailStatus("ONGOING", "UNPAID").stream().map(this::toRentalsResponseDto).toList();
    }

    private RentalsResponseDto toRentalsResponseDto(Rentals rentals) {
        return RentalsResponseDto.builder()
                .id(rentals.getId())
                .invoice(rentals.getInvoiceNumber())
                .startDate(dateToString(rentals.getRentalDate()))
                .endDate(dateToString(rentals.getEndDate()))
                .totalAmount(rentals.getTotalAmount())
                .status(rentals.getStatus())
                .renterName(rentals.getRenterName())
                .renterPhone(rentals.getRenterPhone())
                .build();
    }

    private RentalsResponseDto toRentalsResponseDto(RentalDetail detail){
        return RentalsResponseDto.builder()
                .id(detail.getRentals().getId())
                .invoice(detail.getRentals().getInvoiceNumber())
                .startDate(dateToString(detail.getRentals().getRentalDate()))
                .endDate(dateToString(detail.getRentals().getEndDate()))
                .totalAmount(detail.getRentals().getTotalAmount())
                .status(detail.getRentals().getStatus())
                .renterName(detail.getRentals().getRenterName())
                .renterPhone(detail.getRentals().getRenterPhone())
                .build();
    }

    private String dateToString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private static final DateTimeFormatter FLEXIBLE_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd")
                    .optionalStart()
                    .appendPattern("['T'][' ']HH:mm:ss")
                    .optionalEnd()
                    .toFormatter();

    public static LocalDateTime toLocalDateTime(String dateTime) {
        if (dateTime == null || dateTime.isBlank()) {
            return null;
        }

        if (dateTime.length() == 10) {
            return LocalDate.parse(dateTime)
                    .atStartOfDay();
        }

        return LocalDateTime.parse(dateTime, FLEXIBLE_FORMATTER);
    }

    public long calculateDays(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        return ChronoUnit.DAYS.between(
                startDate.toLocalDate(),
                endDate.toLocalDate()
        ) + 1;
    }
}
