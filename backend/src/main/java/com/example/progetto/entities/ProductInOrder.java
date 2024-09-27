package com.example.progetto.entities;

import com.example.progetto.support.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "prodottoordinato")
public class ProductInOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prodotto_ordinato")
    @JsonView(View.Summary.class)
    private Long productInOrderID;

    @ManyToOne
    @JoinColumn(name = "id_prodotto", referencedColumnName = "id_prodotto")
    @JsonView(View.Summary.class)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_ordine")
    @JsonView(View.Detail.class)
    private Order order;

    @Basic
    @Column(name = "quantita")
    @JsonView(View.Summary.class)
    private int quantity;


}
