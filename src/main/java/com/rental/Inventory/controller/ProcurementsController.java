package com.rental.Inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rental.Inventory.entity.Procurements;
import com.rental.Inventory.service.ProcurementsService;

@RestController
@RequestMapping("/api/procurements")
public class ProcurementsController {

    private final ProcurementsService procurementsService;

    public ProcurementsController(ProcurementsService procurementsService) {
        this.procurementsService = procurementsService;
    }

    // ➕ CREATE
    @PostMapping
    public ResponseEntity<Procurements> create(@RequestBody Procurements procurement) {
        Procurements saved = procurementsService.create(procurement);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ✏️ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Procurements> update(
            @PathVariable String id,
            @RequestBody Procurements procurement) {

        Procurements updated = procurementsService.update(id, procurement);
        return ResponseEntity.ok(updated);
    }

    // 🔍 GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Procurements> getById(@PathVariable String id) {
        return ResponseEntity.ok(procurementsService.getById(id));
    }

    // 📄 GET ALL
    @GetMapping
    public ResponseEntity<List<Procurements>> getAll() {
        return ResponseEntity.ok(procurementsService.getAll());
    }

    // ❌ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        procurementsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
