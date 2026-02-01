package com.rental.Inventory.service;

import java.util.List;

import com.rental.Inventory.entity.StockMovements;

public interface StockMovementService {

    StockMovements create(StockMovements movement);

    StockMovements getById(String id);

    List<StockMovements> getAll();

    List<StockMovements> getByReference(String refType, long refId);

    void delete(String id);
}
