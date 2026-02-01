package com.rental.Inventory.dto.request;

import java.math.BigDecimal;

public record RentalsRequestDto(

        String rentalDate,

        String endDate,

        BigDecimal totalAmount,

        String renterName,

        String renterPhone,

        String productId,

        long quantity
) {
}
