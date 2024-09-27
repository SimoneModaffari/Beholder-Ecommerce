package com.example.progetto.entities;

import com.example.progetto.support.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "carrello")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(View.Summary.class)
    private Long cartID;

    @Basic
    @Column(name = "data_creazione")
    @JsonView(View.Detail.class)
    private Timestamp creationDate;

    @Basic
    @Column(name = "totale", nullable = false)
    @JsonView(View.Detail.class)
    private double total = 0;

    @OneToOne
    @JoinColumn(name = "id_utente", referencedColumnName = "id_utente",nullable = false)
    @JsonView(View.Summary.class)
    private User userID;


    @OneToOne
    @JoinColumn(name = "id_ordine", referencedColumnName = "id_ordine")
    @JsonView(View.Summary.class)
    private Order orderID;

    public Cart(User id){
        this.userID=id;
    }

    public Cart() {

    }
}
