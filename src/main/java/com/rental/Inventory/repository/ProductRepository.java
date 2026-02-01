package com.rental.Inventory.repository;

import com.rental.Inventory.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Products, String> {

    @Query("SELECT COALESCE(SUM(p.stock), 0) FROM Products p")
    Long sumTotalStock();

}
