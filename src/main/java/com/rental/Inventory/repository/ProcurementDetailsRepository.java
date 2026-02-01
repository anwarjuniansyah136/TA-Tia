package com.rental.Inventory.repository;

import com.rental.Inventory.entity.ProcurementsDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcurementDetailsRepository extends JpaRepository<ProcurementsDetail, String> {
    List<ProcurementsDetail>  findByProcurementsId(String procurementId);
}
