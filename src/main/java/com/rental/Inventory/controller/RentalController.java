package com.rental.Inventory.controller;

import com.rental.Inventory.dto.GenericResponse;
import com.rental.Inventory.dto.request.RentalsRequestDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rental.Inventory.service.RentalsService;

@RestController
@RequestMapping("/api/v1/rentals")
public class RentalController {

    private final RentalsService rentalsService;

    public RentalController(RentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> create(@RequestBody RentalsRequestDto rental) {
        return ResponseEntity.ok().body(GenericResponse.success(rentalsService.create(rental), "success"));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(rentalsService.getAll());
    }

    @PutMapping("/{id}/close")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> closeRental(@PathVariable String id) {
        return ResponseEntity.ok(rentalsService.canceled(id));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        rentalsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/return/{invoiceId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> updateDateReturn(@PathVariable long invoiceId){
        return ResponseEntity.ok(rentalsService.returned(invoiceId));
    }

    @PutMapping("/payment/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> payment(@PathVariable String id){
        return ResponseEntity.ok(rentalsService.payment(id));
    }

    @GetMapping("/get-by-id/{id}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> getById(@PathVariable String id){
        return ResponseEntity.ok(rentalsService.getById(id));
    }

    @GetMapping("/find-by-status")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> findByStatus(){
        return ResponseEntity.ok(rentalsService.getByStatus());
    }

    @GetMapping("/report")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> report(){
        return ResponseEntity.ok(rentalsService.getTodayRentalDetails());
    }

    @GetMapping("/find-for-warehouse")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Object> findForWarehouse(){
        return ResponseEntity.ok(rentalsService.findForWarehouse());
    }
}
