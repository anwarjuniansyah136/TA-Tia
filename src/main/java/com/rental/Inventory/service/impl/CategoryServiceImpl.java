package com.rental.Inventory.service.impl;

import com.rental.Inventory.dto.request.CategoryRequestDto;
import com.rental.Inventory.entity.Category;
import com.rental.Inventory.repository.CategoryRepository;
import com.rental.Inventory.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(CategoryRequestDto category) {
        Category category1 = new Category();
        category1.setName(category.name());
        category1.setDescription(category.description());
        return categoryRepository.save(category1);
    }

    @Override
    public Category update(String id, CategoryRequestDto category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setName(category.name());
        existing.setDescription(category.description());

        return categoryRepository.save(existing);
    }

    @Override
    public Category getById(String id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(String id) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(existing);
    }
}
