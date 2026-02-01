package com.rental.Inventory.dto.response;

import java.math.BigDecimal;

public record DashboarResponse(
        long totalProduct,
        long activeProduct,
        BigDecimal revenue
) {
}
