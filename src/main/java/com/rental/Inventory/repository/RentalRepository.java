package com.rental.Inventory.repository;

import com.rental.Inventory.entity.Rentals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rentals, String> {
    boolean existsByInvoiceNumber(long invoiceNumber);

    Optional<Rentals> findByInvoiceNumber(long invoiceId);

    List<Rentals> findByStatusNotIn(List<String> statuses);
}
