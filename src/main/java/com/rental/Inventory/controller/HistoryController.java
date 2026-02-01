package com.rental.Inventory.controller;

import com.rental.Inventory.dto.GenericResponse;
import com.rental.Inventory.service.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/history")
public class HistoryController {

    private final HistoryService historyService;

    public HistoryController(
            HistoryService historyService
    ){
        this.historyService = historyService;
    }
    @GetMapping
    public ResponseEntity<Object> getForDashboard(){
        return ResponseEntity.ok().body(GenericResponse.success(historyService.getForDashboard(), "success"));
    }

    @GetMapping("/find-all-for-this-month")
    public ResponseEntity<Object> findAllForThisMonth(){
        return null;
    }
}
