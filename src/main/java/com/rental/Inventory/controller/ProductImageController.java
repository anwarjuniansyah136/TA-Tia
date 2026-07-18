package com.rental.Inventory.controller;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rental.Inventory.service.ProductImageStorageService;

@RestController
@RequestMapping("/images/products")
public class ProductImageController {
    private final ProductImageStorageService storageService;

    public ProductImageController(ProductImageStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) throws IOException {
        Resource resource = new UrlResource(storageService.resolve(fileName).toUri());
        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }
        String detectedType = java.nio.file.Files.probeContentType(resource.getFile().toPath());
        MediaType mediaType = detectedType == null
                ? MediaType.APPLICATION_OCTET_STREAM : MediaType.parseMediaType(detectedType);
        return ResponseEntity.ok().contentType(mediaType).body(resource);
    }
}
