package com.rental.Inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rental.Inventory.entity.RentalDetail;
import com.rental.Inventory.service.RentalDetailService;

@RestController
@RequestMapping("/api/v1/rental-details")
public class RentalDetailController {

    private final RentalDetailService rentalDetailService;

    public RentalDetailController(RentalDetailService rentalDetailService) {
        this.rentalDetailService = rentalDetailService;
    }

    // ➕ CREATE
    @PostMapping
    public ResponseEntity<RentalDetail> create(
            @RequestBody RentalDetail detail) {

        RentalDetail saved = rentalDetailService.create(detail);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ✏️ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<RentalDetail> update(
            @PathVariable String id,
            @RequestBody RentalDetail detail) {

        return ResponseEntity.ok(
                rentalDetailService.update(id, detail));
    }

    // 🔍 GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<RentalDetail> getById(@PathVariable String id) {
        return ResponseEntity.ok(rentalDetailService.getById(id));
    }

    // 📄 GET BY RENTAL
    @GetMapping("/rental/{rentalId}")
    public ResponseEntity<List<RentalDetail>> getByRental(
            @PathVariable String rentalId) {

        return ResponseEntity.ok(
                rentalDetailService.getByRentalId(rentalId));
    }

    // 📄 GET ALL
    @GetMapping
    public ResponseEntity<List<RentalDetail>> getAll() {
        return ResponseEntity.ok(rentalDetailService.getAll());
    }

    // ❌ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        rentalDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
