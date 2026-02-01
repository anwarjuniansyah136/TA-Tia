package com.rental.Inventory.service;

import java.util.List;

import com.rental.Inventory.entity.RentalDetail;

public interface RentalDetailService {

    RentalDetail create(RentalDetail detail);

    RentalDetail update(String id, RentalDetail detail);

    RentalDetail getById(String id);

    List<RentalDetail> getByRentalId(String rentalId);

    List<RentalDetail> getAll();

    void delete(String id);
}
