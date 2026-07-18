package com.rental.Inventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.Inventory.dto.request.ProcurementRequestDto;
import com.rental.Inventory.service.ProcurementService;

@RestController
@RequestMapping("/api/v1/procurements")
public class ProcurementController {
    private final ProcurementService procurementService;

    public ProcurementController(ProcurementService procurementService) {
        this.procurementService = procurementService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody ProcurementRequestDto request) {
        return new ResponseEntity<>(procurementService.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(procurementService.getAll());
    }
}
