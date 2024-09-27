package com.example.progetto.dto;

import com.example.progetto.utility.ORDER_STATUS;

import java.sql.Timestamp;

public class OrderDTO {
    private Long orderID;
    private double total;
    private ORDER_STATUS orderStatus;
    private String address;
    private Timestamp orderData;
    private Long userID;  // Solo l'ID dell'utente

    // Costruttori, getter e setter
    public OrderDTO() {}

    public OrderDTO(Long orderID, double total, ORDER_STATUS orderStatus, String address, Timestamp orderData, Long userID) {
        this.orderID = orderID;
        this.total = total;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderData = orderData;
        this.userID = userID;
    }

    public Long getOrderID() {
        return orderID;
    }

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ORDER_STATUS getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(ORDER_STATUS orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getOrderData() {
        return orderData;
    }

    public void setOrderData(Timestamp orderData) {
        this.orderData = orderData;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }


}
