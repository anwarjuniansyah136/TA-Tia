package com.rental.Inventory.repository;

import com.rental.Inventory.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, String> {
    Optional<History> findByInvoiceNumber(long id);
    List<History> findByRentalDateGreaterThanEqualAndRentalDateLessThanOrderByRentalDateDesc(
            LocalDateTime start, LocalDateTime end);
}
