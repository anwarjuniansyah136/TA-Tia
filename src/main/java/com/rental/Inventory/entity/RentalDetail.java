package com.rental.Inventory.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "rental_detail")
@NoArgsConstructor
@AllArgsConstructor
public class RentalDetail {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable = false)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "rental", referencedColumnName = "id")
    private Rentals rentals;

    @ManyToOne
    @JoinColumn(name = "product", referencedColumnName = "id")
    private Products products;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "status")
    private String status;
}
