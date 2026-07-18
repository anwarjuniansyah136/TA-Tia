package com.rental.Inventory.service;

import java.util.List;

import com.rental.Inventory.dto.request.ProcurementRequestDto;
import com.rental.Inventory.entity.Procurement;

public interface ProcurementService {
    Procurement create(ProcurementRequestDto request);
    List<Procurement> getAll();
}
