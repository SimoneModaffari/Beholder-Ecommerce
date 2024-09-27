package com.example.progetto.repository;

import com.example.progetto.entities.Order;
import com.example.progetto.entities.Product;
import com.example.progetto.entities.User;
import com.example.progetto.utility.ORDER_STATUS;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {


    Page<Order> findByOrderByOrderDataAsc(Pageable pageable);

    Page<Order> findByOrderByOrderDataDesc(Pageable pageable);

    Page<Order> findByOrderByOrderStatusAsc(Pageable pageable);

    Page<Order> findByOrderByOrderStatusDesc(Pageable pageable);

    Page<Order> findByOrderStatus(ORDER_STATUS stato,Pageable pageable);

    @Query ("SELECT o FROM Order o WHERE o.orderID = :id")
    Order findByOrderID(long id);

    List<Order> findOrderByUserOrderByOrderDataDesc(User u);
    List<Order> findOrderByUser(User u);



}


