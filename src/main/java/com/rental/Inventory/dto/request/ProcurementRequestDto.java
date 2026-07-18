package com.rental.Inventory.dto.request;

import java.math.BigDecimal;

public record ProcurementRequestDto(
        String invoiceNumber,
        String procurementDate,
        String supplierName,
        String productId,
        long quantity,
        BigDecimal unitCost
) {}
