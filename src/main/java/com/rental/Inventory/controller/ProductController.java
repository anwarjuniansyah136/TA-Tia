package com.rental.Inventory.controller;

import java.util.List;

import com.rental.Inventory.dto.request.ProductRequestDto;
import com.rental.Inventory.dto.response.ProductResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rental.Inventory.entity.Products;
import com.rental.Inventory.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(
            @RequestBody ProductRequestDto product
    ) {
        ProductResponseDto saved = productService.create(product);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable String id,
            @RequestBody ProductRequestDto product
    ) {
        ProductResponseDto updated = productService.update(id, product, true);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
