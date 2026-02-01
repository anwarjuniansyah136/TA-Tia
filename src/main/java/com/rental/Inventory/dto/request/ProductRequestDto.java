package com.rental.Inventory.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProductRequestDto(
        String code,

        String name,

        String description,

        String categoryId,

        BigDecimal price,

        BigDecimal purchasePrice,

        long stock,

        String unit
) {
}
