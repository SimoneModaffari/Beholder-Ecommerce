package com.example.progetto.repository;

import com.example.progetto.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByNameContaining(String nome); //cerca per nome specificato

    @Query("SELECT p FROM Product p WHERE p.productID = :id_prodotto")
    Product findByProductID(Long id_prodotto);

    List<Product> findByPriceBetween(double prezzo1, double prezzo2); // per trovare prodotti per prezzo minore di un certo valore

    @Query("SELECT p FROM Product p WHERE p.category = :nomeCategoria")
    List<Product> findProductsByCategory(String nomeCategoria);

    List<Product> findAllByOrderByPriceAsc();//per prezzo crescente

    List<Product> findAllByOrderByPriceDesc(); // per prezzo decrescendente

    List<Product> findAllByCategoryOrderByPriceAsc(String categoria);


    List<Product> findAllByCategoryOrderByPriceDesc(String categoria);

    List<Product> findAllByCategoryAndPriceBetween(String c, double p1, double p2);

    /*Pageable*/
    @Query("SELECT p FROM Product p WHERE p.category = :nomeCategoria")
    Page<Product> findProductsByCategory(String nomeCategoria, Pageable pageable);

    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);

    Page<Product> findAllByOrderByPriceDesc(Pageable pageable);

    Page<Product> findAllByCategoryOrderByPriceAsc(String categoria, Pageable pageable);

    Page<Product> findAllByCategoryOrderByPriceDesc(String categoria, Pageable pageable);

    Page<Product> findAllByCategoryAndPriceBetween(String c, double p1, double p2, Pageable pageable);

    Page<Product> findAllByNameContaining(String nome, Pageable pageable);

    Page<Product> findByPriceBetween(double prezzo1, double prezzo2, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

}
