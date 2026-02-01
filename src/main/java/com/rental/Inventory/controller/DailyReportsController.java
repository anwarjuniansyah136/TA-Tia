package com.rental.Inventory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rental.Inventory.entity.DailyReports;
import com.rental.Inventory.service.DailyReportsService;

@RestController
@RequestMapping("/api/daily-reports")
public class DailyReportsController {

    private final DailyReportsService dailyReportsService;

    public DailyReportsController(DailyReportsService dailyReportsService) {
        this.dailyReportsService = dailyReportsService;
    }

    // ➕ CREATE
    @PostMapping
    public ResponseEntity<DailyReports> create(@RequestBody DailyReports report) {
        DailyReports saved = dailyReportsService.create(report);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ✏️ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<DailyReports> update(
            @PathVariable String id,
            @RequestBody DailyReports report) {

        DailyReports updated = dailyReportsService.update(id, report);
        return ResponseEntity.ok(updated);
    }

    // 🔍 GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DailyReports> getById(@PathVariable String id) {
        return ResponseEntity.ok(dailyReportsService.getById(id));
    }

    // 📄 GET ALL
    @GetMapping
    public ResponseEntity<List<DailyReports>> getAll() {
        return ResponseEntity.ok(dailyReportsService.getAll());
    }

    // ❌ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        dailyReportsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
