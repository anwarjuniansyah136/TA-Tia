package com.rental.Inventory.repository;

import com.rental.Inventory.entity.StockMovements;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovements, String> {
    List<StockMovements> findByRefTypeAndRefId(String refType, long refId);
}
