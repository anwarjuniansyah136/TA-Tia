package com.rental.Inventory.controller;

import com.rental.Inventory.dto.request.ProductRequestDto;
import com.rental.Inventory.dto.response.ProductResponseDto;
import com.rental.Inventory.service.ProductImageStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rental.Inventory.service.ProductService;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductImageStorageService productImageStorageService;

    public ProductController(ProductService productService,
                             ProductImageStorageService productImageStorageService) {
        this.productService = productService;
        this.productImageStorageService = productImageStorageService;
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

    @PostMapping(value = "/upload-photo-product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@PathVariable String id,
                                              @RequestParam("file") MultipartFile file) {
        String fileName = productImageStorageService.store(file);
        return ResponseEntity.ok(productService.updateImage(id, fileName));
    }
}
