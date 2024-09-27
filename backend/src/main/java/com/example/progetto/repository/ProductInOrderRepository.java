package com.example.progetto.repository;

import com.example.progetto.entities.ProductInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder,Long> {

    List<ProductInOrder> findAllByOrderByProductPriceAsc();
    List<ProductInOrder> findAllByOrderByProductPriceDesc();

    @Query("Select po From ProductInOrder po where po.productInOrderID= :ID")
    ProductInOrder findByProductInOrderID(Long ID);
}
