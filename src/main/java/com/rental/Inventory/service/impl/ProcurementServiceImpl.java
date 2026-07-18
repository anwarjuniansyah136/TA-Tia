package com.rental.Inventory.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rental.Inventory.dto.request.ProcurementRequestDto;
import com.rental.Inventory.entity.Procurement;
import com.rental.Inventory.repository.ProcurementRepository;
import com.rental.Inventory.repository.ProductRepository;
import com.rental.Inventory.service.ProcurementService;

@Service
public class ProcurementServiceImpl implements ProcurementService {
    private final ProcurementRepository procurementRepository;
    private final ProductRepository productRepository;

    public ProcurementServiceImpl(ProcurementRepository procurementRepository,
                                  ProductRepository productRepository) {
        this.procurementRepository = procurementRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Procurement create(ProcurementRequestDto request) {
        if (request.quantity() <= 0 || request.unitCost() == null
                || request.unitCost().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Quantity and unit cost must be valid");
        }
        if (procurementRepository.existsByInvoiceNumber(request.invoiceNumber())) {
            throw new IllegalArgumentException("Procurement invoice already exists");
        }

        var product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Procurement procurement = new Procurement();
        procurement.setInvoiceNumber(request.invoiceNumber());
        procurement.setProcurementDate(request.procurementDate() == null
                ? LocalDateTime.now() : LocalDateTime.parse(request.procurementDate()));
        procurement.setSupplierName(request.supplierName());
        procurement.setProduct(product);
        procurement.setQuantity(request.quantity());
        procurement.setUnitCost(request.unitCost());
        procurement.setTotalCost(request.unitCost().multiply(BigDecimal.valueOf(request.quantity())));
        procurement.setStatus("RECEIVED");

        product.setStock(product.getStock() + request.quantity());
        product.setPurchasePrice(request.unitCost());
        productRepository.save(product);
        return procurementRepository.save(procurement);
    }

    @Override
    public List<Procurement> getAll() {
        return procurementRepository.findAll();
    }
}
