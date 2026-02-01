package com.rental.Inventory.entity;

import java.math.BigDecimal;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "daily_reports")
public class DailyReports {
    @Id
    @UuidGenerator
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "total_transactions")
    private BigDecimal totalTransactions;

    @Column(name = "total_income")
    private BigDecimal totalIncome;

    @ManyToOne
    @JoinColumn(name = "generated_by", referencedColumnName = "id")
    private Users users;

    @Column(name = "generated_at")
    private LocalDateTime generatedAt;
}
