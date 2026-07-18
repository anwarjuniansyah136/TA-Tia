package com.rental.Inventory.service;

import com.rental.Inventory.dto.response.DashboarResponse;
import com.rental.Inventory.entity.History;

import java.util.List;

public interface HistoryService {
    DashboarResponse getForDashboard();
    List<History> findAllForCurrentMonth();
}
