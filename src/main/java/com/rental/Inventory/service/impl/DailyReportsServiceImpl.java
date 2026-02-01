package com.rental.Inventory.service.impl;

import com.rental.Inventory.entity.DailyReports;
import com.rental.Inventory.entity.Users;
import com.rental.Inventory.repository.DailyReportsRepository;
import com.rental.Inventory.repository.UserRepository;
import com.rental.Inventory.service.DailyReportsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailyReportsServiceImpl implements DailyReportsService {

    private final DailyReportsRepository dailyReportsRepository;
    private final UserRepository usersRepository;

    public DailyReportsServiceImpl(
            DailyReportsRepository dailyReportsRepository,
            UserRepository usersRepository) {

        this.dailyReportsRepository = dailyReportsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public DailyReports create(DailyReports report) {

        Users user = usersRepository.findById(
                        report.getUsers().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        report.setUsers(user);
        report.setGeneratedAt(LocalDateTime.now());

        return dailyReportsRepository.save(report);
    }

    @Override
    public DailyReports update(String id, DailyReports report) {

        DailyReports existing = dailyReportsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Daily report not found"));

        Users user = usersRepository.findById(
                        report.getUsers().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setDate(report.getDate());
        existing.setTotalTransactions(report.getTotalTransactions());
        existing.setTotalIncome(report.getTotalIncome());
        existing.setUsers(user);

        return dailyReportsRepository.save(existing);
    }

    @Override
    public DailyReports getById(String id) {
        return dailyReportsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Daily report not found"));
    }

    @Override
    public List<DailyReports> getAll() {
        return dailyReportsRepository.findAll();
    }

    @Override
    public void delete(String id) {
        DailyReports report = dailyReportsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Daily report not found"));
        dailyReportsRepository.delete(report);
    }
}
