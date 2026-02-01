package com.rental.Inventory.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "procurements_detail")
public class ProcurementsDetail {

    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "procurement_id", referencedColumnName = "id")
    private Procurements procurements;

    @ManyToOne
    @JoinColumn(name = "products", referencedColumnName = "id")
    private Products products;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "purchase_price")
    private BigDecimal purchasePrice;

    @Column(name = "subtotal")
    private BigDecimal subtotal;
    
}
