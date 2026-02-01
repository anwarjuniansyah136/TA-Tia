package com.rental.Inventory.service;

import com.rental.Inventory.dto.request.CategoryRequestDto;
import com.rental.Inventory.entity.Category;

import java.util.List;

public interface CategoryService {
    Category create(CategoryRequestDto category);

    Category update(String id, CategoryRequestDto category);

    Category getById(String id);

    List<Category> getAll();

    void delete(String id);}
