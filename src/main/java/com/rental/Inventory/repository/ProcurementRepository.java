package com.rental.Inventory.repository;

import com.rental.Inventory.entity.Procurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcurementRepository extends JpaRepository<Procurement, String> {
    boolean existsByInvoiceNumber(String invoiceNumber);
}
