package com.rental.Inventory.service.impl;

import com.rental.Inventory.dto.response.DashboarResponse;
import com.rental.Inventory.repository.ProductRepository;
import com.rental.Inventory.repository.RentalDetailRepository;
import com.rental.Inventory.repository.HistoryRepository;
import com.rental.Inventory.entity.History;
import com.rental.Inventory.service.HistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final ProductRepository productRepository;
    private final RentalDetailRepository rentalDetailRepository;
    private final HistoryRepository historyRepository;

    public HistoryServiceImpl(
            ProductRepository productRepository,
            RentalDetailRepository rentalDetailRepository,
            HistoryRepository historyRepository
    ){
        this.productRepository = productRepository;
        this.rentalDetailRepository = rentalDetailRepository;
        this.historyRepository = historyRepository;
    }
    @Override
    public DashboarResponse getForDashboard() {
        return new DashboarResponse(
                productRepository.sumTotalStock() + rentalDetailRepository.sumQuantityExcludeReturnAndCanceled(),
                rentalDetailRepository.sumQuantityExcludeReturnAndCanceled(),
                rentalDetailRepository.getTotalRevenue()
        );
    }

    @Override
    public List<History> findAllForCurrentMonth() {
        LocalDate firstDay = LocalDate.now().withDayOfMonth(1);
        LocalDateTime start = firstDay.atStartOfDay();
        LocalDateTime end = firstDay.plusMonths(1).atStartOfDay();
        return historyRepository.findByRentalDateGreaterThanEqualAndRentalDateLessThanOrderByRentalDateDesc(start, end);
    }
}
