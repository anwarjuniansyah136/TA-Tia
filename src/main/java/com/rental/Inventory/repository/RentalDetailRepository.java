package com.rental.Inventory.repository;

import com.rental.Inventory.entity.RentalDetail;
import com.rental.Inventory.entity.Rentals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RentalDetailRepository extends JpaRepository<RentalDetail, String> {
    List<RentalDetail> findByRentalsId(String rentalId);

    Optional<RentalDetail> findByRentals(Rentals rental);

    @Query("""
        SELECT COALESCE(SUM(rd.quantity), 0)
        FROM RentalDetail rd
        WHERE rd.rentals.status NOT IN ('RETURN', 'CANCELED')
    """)
    Long sumQuantityExcludeReturnAndCanceled();

    @Query("""
        SELECT COALESCE(SUM(rd.subtotal), 0)
        FROM RentalDetail rd
        WHERE rd.rentals.status NOT IN ('RETURN', 'CANCELED', 'ONGOING')
    """)
    BigDecimal getTotalRevenue();

    @Query("""
        SELECT rd
        FROM RentalDetail rd
        JOIN rd.rentals r
        WHERE r.status = :rentalStatus
        AND rd.status = :detailStatus
    """)
    List<RentalDetail> findByRentalAndDetailStatus(
            @Param("rentalStatus") String rentalStatus,
            @Param("detailStatus") String detailStatus
    );

    @Query("""
        SELECT rd
        FROM RentalDetail rd
        JOIN rd.rentals r
        WHERE r.rentalDate >= :start
        AND r.rentalDate < :end
    """)
    List<RentalDetail> findTodayRentalDetails(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
