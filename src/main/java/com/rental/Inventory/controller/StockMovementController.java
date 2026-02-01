package com.rental.Inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rental.Inventory.entity.StockMovements;
import com.rental.Inventory.service.StockMovementService;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {

    private final StockMovementService stockMovementsService;

    public StockMovementController(StockMovementService stockMovementsService) {
        this.stockMovementsService = stockMovementsService;
    }

    @PostMapping
    public ResponseEntity<StockMovements> create(
            @RequestBody StockMovements movement) {

        StockMovements saved = stockMovementsService.create(movement);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovements> getById(@PathVariable String id) {
        return ResponseEntity.ok(stockMovementsService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<StockMovements>> getAll() {
        return ResponseEntity.ok(stockMovementsService.getAll());
    }

    @GetMapping("/reference")
    public ResponseEntity<List<StockMovements>> getByReference(
            @RequestParam String refType,
            @RequestParam long refId) {

        return ResponseEntity.ok(
                stockMovementsService.getByReference(refType, refId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        stockMovementsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
