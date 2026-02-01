package com.rental.Inventory.repository;

import com.rental.Inventory.entity.DailyReports;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyReportsRepository extends JpaRepository<DailyReports, String> {
}
