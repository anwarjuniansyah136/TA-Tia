package com.rental.Inventory.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "history")
public class History {

    @Id
    @UuidGenerator
    @Column(name = "id", length = 36,nullable = false)
    private String id;

    @Column(name = "invoice_number")
    private long invoiceNumber;

    @Column(name = "productName")
    private String productName;

    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "qty")
    private long qty;

    @Column(name = "price_of_product")
    private BigDecimal priceOfProduct;

    @Column(name = "subtotal_price")
    private BigDecimal subtotalPrice;

    @Column(name = "status")
    private String statusProduct;

    @Column(name = "status_payment")
    private String statusPayment;

    @Column(name = "date")
    private LocalDateTime rentalDate;
}
