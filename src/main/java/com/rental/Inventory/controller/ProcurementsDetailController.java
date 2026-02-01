package com.rental.Inventory.controller;

import java.util.List;

import com.rental.Inventory.service.ProcurementDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rental.Inventory.entity.ProcurementsDetail;

@RestController
@RequestMapping("/api/procurements-details")
public class ProcurementsDetailController {

    private final ProcurementDetailsService detailService;

    public ProcurementsDetailController(ProcurementDetailsService detailService) {
        this.detailService = detailService;
    }

    // ➕ CREATE
    @PostMapping
    public ResponseEntity<ProcurementsDetail> create(
            @RequestBody ProcurementsDetail detail) {

        ProcurementsDetail saved = detailService.create(detail);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ✏️ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ProcurementsDetail> update(
            @PathVariable String id,
            @RequestBody ProcurementsDetail detail) {

        return ResponseEntity.ok(detailService.update(id, detail));
    }

    // 🔍 GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ProcurementsDetail> getById(@PathVariable String id) {
        return ResponseEntity.ok(detailService.getById(id));
    }

    // 📄 GET BY PROCUREMENT
    @GetMapping("/procurement/{procurementId}")
    public ResponseEntity<List<ProcurementsDetail>> getByProcurement(
            @PathVariable String procurementId) {

        return ResponseEntity.ok(
                detailService.getByProcurementId(procurementId));
    }

    // 📄 GET ALL
    @GetMapping
    public ResponseEntity<List<ProcurementsDetail>> getAll() {
        return ResponseEntity.ok(detailService.getAll());
    }

    // ❌ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        detailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
