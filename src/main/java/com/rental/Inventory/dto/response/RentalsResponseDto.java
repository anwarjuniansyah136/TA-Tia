package com.rental.Inventory.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RentalsResponseDto(
        String id,

        long invoice,

        String startDate,

        String endDate,

        BigDecimal totalAmount,

        String status,

        String renterName,

        String renterPhone
) {
}
