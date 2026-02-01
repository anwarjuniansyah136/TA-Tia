package com.rental.Inventory.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.rental.Inventory.repository.UserRepository;
import org.springframework.stereotype.Service;

import com.rental.Inventory.entity.Procurements;
import com.rental.Inventory.entity.Users;
import com.rental.Inventory.repository.ProcurementsRepository;
import com.rental.Inventory.service.ProcurementsService;

@Service
public class ProcurementsServiceImpl implements ProcurementsService {

    private final ProcurementsRepository procurementsRepository;
    private final UserRepository usersRepository;

    public ProcurementsServiceImpl(
            ProcurementsRepository procurementsRepository,
            UserRepository usersRepository) {

        this.procurementsRepository = procurementsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public Procurements create(Procurements procurement) {

        Users user = usersRepository.findById(
                        procurement.getUsers().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        procurement.setUsers(user);

        if (procurement.getDate() == null) {
            procurement.setDate(LocalDateTime.now());
        }

        return procurementsRepository.save(procurement);
    }

    @Override
    public Procurements update(String id, Procurements procurement) {

        Procurements existing = procurementsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procurement not found"));

        Users user = usersRepository.findById(
                        procurement.getUsers().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setProcurement_number(procurement.getProcurement_number());
        existing.setDate(procurement.getDate());
        existing.setTotalCost(procurement.getTotalCost());
        existing.setUsers(user);

        return procurementsRepository.save(existing);
    }

    @Override
    public Procurements getById(String id) {
        return procurementsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procurement not found"));
    }

    @Override
    public List<Procurements> getAll() {
        return procurementsRepository.findAll();
    }

    @Override
    public void delete(String id) {
        Procurements procurement = procurementsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procurement not found"));
        procurementsRepository.delete(procurement);
    }
}
