package com.rental.Inventory.service.impl;

import java.util.List;

import com.rental.Inventory.dto.request.ProductRequestDto;
import com.rental.Inventory.dto.response.ProductResponseDto;
import org.springframework.stereotype.Service;

import com.rental.Inventory.entity.Category;
import com.rental.Inventory.entity.Products;
import com.rental.Inventory.repository.CategoryRepository;
import com.rental.Inventory.repository.ProductRepository;
import com.rental.Inventory.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(
            ProductRepository productRepository,
            CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductResponseDto create(ProductRequestDto product) {

        Category category = categoryRepository.findById(
                        product.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));


        var products = new Products();
        products.setCode(product.code());
        products.setName(product.name());
        products.setDescription(product.description());
        products.setCategory(category);
        products.setPrice(product.price());
        products.setPurchasePrice(product.purchasePrice());
        products.setStock(product.stock());
        products.setUnit(product.unit());
        products.setStatus(true);
        products.setImageUrl(null);

        productRepository.save(products);

        return new ProductResponseDto(
                products.getId(),
                products.getCode(),
                products.getName(),
                products.getDescription(),
                products.getCategory().getName(),
                products.getCategory().getId(),
                products.getPrice(),
                products.getStock(),
                products.getUnit(),
                products.isStatus(),
                products.getImageUrl()
        );
    }

    @Override
    public ProductResponseDto update(String id, ProductRequestDto product, boolean status) {

        Products existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(product.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setCode(product.code());
        existing.setName(product.name());
        existing.setDescription(product.description());
        existing.setCategory(category);
        existing.setPrice(product.price());
        existing.setPurchasePrice(product.purchasePrice());
        existing.setStock(product.stock());
        existing.setUnit(product.unit());
        existing.setStatus(status);

        productRepository.save(existing);

        return new ProductResponseDto(
                existing.getId(),
                existing.getCode(),
                existing.getName(),
                existing.getDescription(),
                existing.getCategory().getName(),
                existing.getCategory().getId(),
                existing.getPrice(),
                existing.getStock(),
                existing.getUnit(),
                existing.isStatus(),
                existing.getImageUrl()
        );
    }

    @Override
    public ProductResponseDto getById(String id) {
        var products = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return new ProductResponseDto(
                products.getId(),
                products.getCode(),
                products.getName(),
                products.getDescription(),
                products.getCategory().getName(),
                products.getCategory().getId(),
                products.getPrice(),
                products.getStock(),
                products.getUnit(),
                products.isStatus(),
                products.getImageUrl()
        );
    }

    @Override
    public List<ProductResponseDto> getAll() {
        return productRepository.findAll().stream().map(this::toProductResponseDto).toList();
    }

    @Override
    public void delete(String id) {
        Products product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    private ProductResponseDto toProductResponseDto(Products products){
        return ProductResponseDto.builder()
                .id(products.getId())
                .code(products.getCode())
                .name(products.getName())
                .description(products.getDescription())
                .categoryName(products.getCategory().getName())
                .categoryId(products.getCategory().getId())
                .price(products.getPrice())
                .stock(products.getStock())
                .unit(products.getUnit())
                .status(products.isStatus())
                .imageUrl(products.getImageUrl())
                .build();
    }
}
