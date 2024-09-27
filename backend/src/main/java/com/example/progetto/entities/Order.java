package com.example.progetto.entities;

import com.example.progetto.support.View;
import com.example.progetto.utility.ORDER_STATUS;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "ordini")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ordine")
    @JsonView(View.Summary.class)
    private Long orderID;

    @Basic
    @Column(name = "totale")
    @JsonView(View.Summary.class)
    private double total;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato_ordine", nullable = false)
    @JsonView(View.Summary.class)
    private ORDER_STATUS orderStatus;

    @Basic
    @Column(name = "address")
    @JsonView(View.Summary.class)
    private String address;

    @Basic
    @Column(name = "data_ordine")
    @CreationTimestamp
    @JsonView(View.Summary.class)
    private Timestamp orderData;

    @ManyToOne
    @JoinColumn(name = "id_utente", referencedColumnName = "id_utente")
    @JsonView(View.Summary.class)
    private User user;

    @OneToOne(mappedBy = "orderID", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(View.Detail.class)
    private Cart cart;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonView(View.Summary.class)
    private List<ProductInOrder> productsInOrder;

    @PreRemove
    public void preRemove() {
        if (cart != null) {
            cart.setOrderID(null);
        }
    }
}