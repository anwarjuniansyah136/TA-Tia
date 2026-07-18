package com.rental.Inventory.service;

import com.rental.Inventory.dto.request.ProductRequestDto;
import com.rental.Inventory.dto.response.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto create(ProductRequestDto product);

    ProductResponseDto update(String id, ProductRequestDto product, boolean status);

    ProductResponseDto getById(String id);

    List<ProductResponseDto> getAll();

    void delete(String id);

    ProductResponseDto updateImage(String id, String fileName);

}
