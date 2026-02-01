package com.rental.Inventory.repository;

import com.rental.Inventory.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, String> {
    History findByInvoiceNumber(long id);
}
