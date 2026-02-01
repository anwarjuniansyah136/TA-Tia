package com.rental.Inventory.service;

import com.rental.Inventory.entity.ProcurementsDetail;

import java.util.List;

public interface ProcurementDetailsService {
    ProcurementsDetail create(ProcurementsDetail detail);

    ProcurementsDetail update(String id, ProcurementsDetail detail);

    ProcurementsDetail getById(String id);

    List<ProcurementsDetail> getByProcurementId(String procurementId);

    List<ProcurementsDetail> getAll();

    void delete(String id);
}
