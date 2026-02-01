package com.rental.Inventory.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.rental.Inventory.repository.StockMovementRepository;
import com.rental.Inventory.repository.UserRepository;
import com.rental.Inventory.service.StockMovementService;
import org.springframework.stereotype.Service;

import com.rental.Inventory.entity.StockMovements;
import com.rental.Inventory.entity.Users;

@Service
public class StockMovementServiceImpl implements StockMovementService {

    private final StockMovementRepository stockMovementsRepository;
    private final UserRepository usersRepository;

    public StockMovementServiceImpl(
            StockMovementRepository stockMovementsRepository,
            UserRepository usersRepository) {

        this.stockMovementsRepository = stockMovementsRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    public StockMovements create(StockMovements movement) {

        Users user = usersRepository.findById(
                        movement.getUsers().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        movement.setUsers(user);
        movement.setCreateAt(LocalDateTime.now());

        return stockMovementsRepository.save(movement);
    }

    @Override
    public StockMovements getById(String id) {
        return stockMovementsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock movement not found"));
    }

    @Override
    public List<StockMovements> getAll() {
        return stockMovementsRepository.findAll();
    }

    @Override
    public List<StockMovements> getByReference(String refType, long refId) {
        return stockMovementsRepository.findByRefTypeAndRefId(refType, refId);
    }

    @Override
    public void delete(String id) {
        StockMovements movement = stockMovementsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock movement not found"));
        stockMovementsRepository.delete(movement);
    }
}
