package com.rental.Inventory.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.rental.Inventory.repository.ProductRepository;
import com.rental.Inventory.repository.RentalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.Inventory.entity.Products;
import com.rental.Inventory.entity.RentalDetail;
import com.rental.Inventory.entity.Rentals;
import com.rental.Inventory.repository.RentalDetailRepository;
import com.rental.Inventory.service.RentalDetailService;

@Service
public class RentalDetailServiceImpl implements RentalDetailService {

    private final RentalDetailRepository rentalDetailRepository;
    private final RentalRepository rentalsRepository;
    private final ProductRepository productsRepository;

    public RentalDetailServiceImpl(
            RentalDetailRepository rentalDetailRepository,
            RentalRepository rentalsRepository,
            ProductRepository productsRepository) {

        this.rentalDetailRepository = rentalDetailRepository;
        this.rentalsRepository = rentalsRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    @Transactional
    public RentalDetail create(RentalDetail detail) {

        Rentals rental = rentalsRepository.findById(
                        detail.getRentals().getId())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        Products product = productsRepository.findById(
                        detail.getProducts().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < detail.getQuantity()) {
            throw new RuntimeException("Stock not sufficient");
        }

        detail.setRentals(rental);
        detail.setProducts(product);

        BigDecimal subtotal = detail.getPrice()
                .multiply(BigDecimal.valueOf(detail.getQuantity()));
        detail.setSubtotal(subtotal);

        // ⬇️ Kurangi stok
        product.setStock(product.getStock() - detail.getQuantity());
        productsRepository.save(product);

        return rentalDetailRepository.save(detail);
    }

    @Override
    @Transactional
    public RentalDetail update(String id, RentalDetail detail) {

        RentalDetail existing = rentalDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental detail not found"));

        Products product = productsRepository.findById(
                        detail.getProducts().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        long stockAdjustment =
                existing.getQuantity() - detail.getQuantity();

        if (product.getStock() + stockAdjustment < 0) {
            throw new RuntimeException("Stock not sufficient");
        }

        product.setStock(product.getStock() + stockAdjustment);
        productsRepository.save(product);

        existing.setQuantity(detail.getQuantity());
        existing.setPrice(detail.getPrice());
        existing.setProducts(product);

        BigDecimal subtotal = detail.getPrice()
                .multiply(BigDecimal.valueOf(detail.getQuantity()));
        existing.setSubtotal(subtotal);

        return rentalDetailRepository.save(existing);
    }

    @Override
    public RentalDetail getById(String id) {
        return rentalDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental detail not found"));
    }

    @Override
    public List<RentalDetail> getByRentalId(String rentalId) {
        return rentalDetailRepository.findByRentalsId(rentalId);
    }

    @Override
    public List<RentalDetail> getAll() {
        return rentalDetailRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(String id) {

        RentalDetail detail = rentalDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental detail not found"));

        Products product = detail.getProducts();

        // ⬆️ Kembalikan stok
        product.setStock(product.getStock() + detail.getQuantity());
        productsRepository.save(product);

        rentalDetailRepository.delete(detail);
    }
}
