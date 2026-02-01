package com.rental.Inventory.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.rental.Inventory.repository.ProcurementDetailsRepository;
import com.rental.Inventory.repository.ProductRepository;
import com.rental.Inventory.service.ProcurementDetailsService;
import org.springframework.stereotype.Service;

import com.rental.Inventory.entity.Procurements;
import com.rental.Inventory.entity.ProcurementsDetail;
import com.rental.Inventory.entity.Products;
import com.rental.Inventory.repository.ProcurementsRepository;

@Service
public class ProcurementDetailsServiceImpl implements ProcurementDetailsService {

    private final ProcurementDetailsRepository detailRepository;
    private final ProcurementsRepository procurementsRepository;
    private final ProductRepository productsRepository;

    public ProcurementDetailsServiceImpl(
            ProcurementDetailsRepository detailRepository,
            ProcurementsRepository procurementsRepository,
            ProductRepository productsRepository) {

        this.detailRepository = detailRepository;
        this.procurementsRepository = procurementsRepository;
        this.productsRepository = productsRepository;
    }

    @Override
    public ProcurementsDetail create(ProcurementsDetail detail) {

        Procurements procurement = procurementsRepository.findById(
                        detail.getProcurements().getId())
                .orElseThrow(() -> new RuntimeException("Procurement not found"));

        Products product = productsRepository.findById(
                        detail.getProducts().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        detail.setProcurements(procurement);
        detail.setProducts(product);

        // subtotal = quantity × purchase price
        BigDecimal subtotal = detail.getPurchasePrice()
                .multiply(BigDecimal.valueOf(detail.getQuantity()));
        detail.setSubtotal(subtotal);

        return detailRepository.save(detail);
    }

    @Override
    public ProcurementsDetail update(String id, ProcurementsDetail detail) {

        ProcurementsDetail existing = detailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procurement detail not found"));

        Procurements procurement = procurementsRepository.findById(
                        detail.getProcurements().getId())
                .orElseThrow(() -> new RuntimeException("Procurement not found"));

        Products product = productsRepository.findById(
                        detail.getProducts().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setProcurements(procurement);
        existing.setProducts(product);
        existing.setQuantity(detail.getQuantity());
        existing.setPurchasePrice(detail.getPurchasePrice());

        BigDecimal subtotal = detail.getPurchasePrice()
                .multiply(BigDecimal.valueOf(detail.getQuantity()));
        existing.setSubtotal(subtotal);

        return detailRepository.save(existing);
    }

    @Override
    public ProcurementsDetail getById(String id) {
        return detailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procurement detail not found"));
    }

    @Override
    public List<ProcurementsDetail> getByProcurementId(String procurementId) {
        return detailRepository.findByProcurementsId(procurementId);
    }

    @Override
    public List<ProcurementsDetail> getAll() {
        return detailRepository.findAll();
    }

    @Override
    public void delete(String id) {
        ProcurementsDetail detail = detailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Procurement detail not found"));
        detailRepository.delete(detail);
    }
}
