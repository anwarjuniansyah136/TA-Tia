package com.rental.Inventory.service;

import com.rental.Inventory.entity.Procurements;

import java.util.List;

public interface ProcurementsService {
    Procurements create(Procurements procurement);

    Procurements update(String id, Procurements procurement);

    Procurements getById(String id);

    List<Procurements> getAll();

    void delete(String id);
}
