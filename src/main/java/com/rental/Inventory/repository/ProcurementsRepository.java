package com.rental.Inventory.repository;

import com.rental.Inventory.entity.Procurements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcurementsRepository extends JpaRepository<Procurements, String> {
}
