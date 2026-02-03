package com.rental.Inventory.service;

import java.util.List;

import com.rental.Inventory.dto.request.RentalsRequestDto;
import com.rental.Inventory.dto.response.RentalsResponseDto;
import com.rental.Inventory.entity.RentalDetail;

public interface RentalsService {

    RentalsResponseDto create(RentalsRequestDto rental);

    List<RentalsResponseDto> getAll();

    RentalsResponseDto canceled(String id);

    void delete(String id);

    RentalsResponseDto returned(long invoiceId);

    RentalsResponseDto payment(String invoiceId);

    RentalsResponseDto getById(String id);

    List<RentalsResponseDto> getByStatus();

    List<RentalDetail> getTodayRentalDetails();

    List<RentalDetail> findForWarehouse();

}
