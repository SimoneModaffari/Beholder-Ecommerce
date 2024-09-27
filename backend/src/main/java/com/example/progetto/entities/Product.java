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
@Table(name="prodotti")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prodotto")
    @JsonView(View.Summary.class)
    private Long productID;

    @Basic
    @Column(name = "nome", nullable = false)
    @JsonView(View.Summary.class)
    private String name;

    @Basic
    @Column(name="descrizione")
    @JsonView(View.Detail.class)
    private String description;

    @Basic
    @Column (name = "prezzo", nullable = false)
    @JsonView(View.Summary.class)
    private double price;

    @Basic
    @Column(name = "quantita_in_magazzino", nullable = false)
    @JsonView(View.Summary.class)
    private int quantity;

    @Basic
    @Column(name = "immagine")
    @JsonView(View.Summary.class)
    private String picture;

    @Basic
    @Column(name = "categoria")
    @JsonView(View.Detail.class)
    private String category;


    @Version
    @Column(name = "version")
    @JsonView(View.Summary.class)
    private Long version;

    public Product(){
        this.version= 0L;
    }
}
