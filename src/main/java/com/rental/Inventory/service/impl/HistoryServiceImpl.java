package com.rental.Inventory.service.impl;

import com.rental.Inventory.dto.response.DashboarResponse;
import com.rental.Inventory.repository.ProductRepository;
import com.rental.Inventory.repository.RentalDetailRepository;
import com.rental.Inventory.service.HistoryService;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final ProductRepository productRepository;
    private final RentalDetailRepository rentalDetailRepository;

    public HistoryServiceImpl(
            ProductRepository productRepository,
            RentalDetailRepository rentalDetailRepository
    ){
        this.productRepository = productRepository;
        this.rentalDetailRepository = rentalDetailRepository;
    }
    @Override
    public DashboarResponse getForDashboard() {
        return new DashboarResponse(
                productRepository.sumTotalStock() + rentalDetailRepository.sumQuantityExcludeReturnAndCanceled(),
                rentalDetailRepository.sumQuantityExcludeReturnAndCanceled(),
                rentalDetailRepository.getTotalRevenue()
        );
    }
}
