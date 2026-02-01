package com.rental.Inventory.entity;

import java.time.LocalDateTime;

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
@Table(name = "stock_movements")
@AllArgsConstructor
@NoArgsConstructor
public class StockMovements {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable =  false)
    private String id;

    @Column(name = "movement_type")
    private String movementType;

    @Column(name = "ref_type")
    private String refType;

    @Column(name = "ref_id")
    private long refId;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "note")
    private String note;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Users users;
}
