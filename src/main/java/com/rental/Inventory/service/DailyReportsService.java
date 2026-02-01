package com.rental.Inventory.service;

import com.rental.Inventory.entity.DailyReports;

import java.util.List;

public interface DailyReportsService {
    DailyReports create(DailyReports report);

    DailyReports update(String id, DailyReports report);

    DailyReports getById(String id);

    List<DailyReports> getAll();

    void delete(String id);
}
