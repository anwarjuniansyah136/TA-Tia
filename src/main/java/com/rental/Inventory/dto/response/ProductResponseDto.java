package com.rental.Inventory.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponseDto(
        String id,

        String code,

        String name,

        String description,

        @JsonProperty("category_name")
        String categoryName,

        @JsonProperty("category_id")
        String categoryId,

        BigDecimal price,

        long stock,

        String unit,

        boolean status,

        @JsonProperty("image_url")
        String imageUrl
) {
}
