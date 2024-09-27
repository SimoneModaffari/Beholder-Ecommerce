package com.example.progetto.services;

import com.example.progetto.entities.Product;
import com.example.progetto.repository.ProductRepository;
import com.example.progetto.support.exception.ProductAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository prodottoRepository) {
        this.productRepository = prodottoRepository;
    }



    public List<Product> getProdottiPerCategoria(String nomeCategoria) {
        return productRepository.findProductsByCategory(nomeCategoria);
    }

    public Product getProdottoByID(Long ID){
        return productRepository.findByProductID(ID);
    }

    public List<Product> getProductsByName(String nome){
        return productRepository.findAllByNameContaining(nome);
    }

    public List<Product> getProductsByPriceAsc(){
        return productRepository.findAllByOrderByPriceAsc();
    }

    public List<Product> getProductsByPriceDesc(){
        return productRepository.findAllByOrderByPriceDesc();
    }

    public List<Product> getProductsByPriceBetween(double p1, double p2){
        return productRepository.findByPriceBetween(p1,p2);
    }

    public Product createProducts(Product p)throws ProductAlreadyExistException {
        if(productRepository.existsById(p.getProductID())){
            throw new ProductAlreadyExistException();
        }
        else{
            p.setProductID(null);
            return productRepository.save(p);
        }
    }

    public void deleteProduct(Product p){
        productRepository.delete(p);
    }

    public Product editProductPrice(Long id, double nuovoPrezzo) {
        Optional<Product> optionalProdotto = productRepository.findById(id);
        if (optionalProdotto.isPresent()) {
            Product prodotto = optionalProdotto.get();
            prodotto.setPrice(nuovoPrezzo);
            return productRepository.save(prodotto);
        } else {
            throw new IllegalArgumentException("Prodotto non trovato con ID: " + id);
        }
    }
    public Product editProductQuantity(Long id, int nuovaQuantita) {
        Optional<Product> optionalProdotto = productRepository.findById(id);
        if (optionalProdotto.isPresent()) {
            Product prodotto = optionalProdotto.get();
            prodotto.setQuantity(nuovaQuantita);
            return productRepository.save(prodotto);
        } else {
            throw new IllegalArgumentException("Prodotto non trovato con ID: " + id);
        }
    }

    public Product editProductName(Long id, String nuovoNome) {
        Optional<Product> optionalProdotto = productRepository.findById(id);
        if (optionalProdotto.isPresent()) {
            Product prodotto = optionalProdotto.get();
            prodotto.setName(nuovoNome);
            return productRepository.save(prodotto);
        } else {
            throw new IllegalArgumentException("Prodotto non trovato con ID: " + id);
        }
    }

    public Product editProductDescr(Long id, String nuovaDescr) {
        Optional<Product> optionalProdotto = productRepository.findById(id);
        if (optionalProdotto.isPresent()) {
            Product prodotto = optionalProdotto.get();
            prodotto.setDescription(nuovaDescr);
            return productRepository.save(prodotto);
        } else {
            throw new IllegalArgumentException("Prodotto non trovato con ID: " + id);
        }
    }
    public Product editProductCategory(Long id, String nuovaCat) {
        Optional<Product> optionalProdotto = productRepository.findById(id);
        if (optionalProdotto.isPresent()) {
            Product prodotto = optionalProdotto.get();
            prodotto.setCategory(nuovaCat);
            return productRepository.save(prodotto);
        } else {
            throw new IllegalArgumentException("Prodotto non trovato con ID: " + id);
        }
    }

    public Product editPicture(Long id, String immagine) {
        Optional<Product> optionalProdotto = productRepository.findById(id);
        if (optionalProdotto.isPresent()) {
            Product prodotto = optionalProdotto.get();
            prodotto.setPicture(immagine);
            return productRepository.save(prodotto);
        } else {
            throw new IllegalArgumentException("Prodotto non trovato con ID: " + id);
        }
    }

    public List<Product> getByCategoryAndPriceAsc(String c){
        return productRepository.findAllByCategoryOrderByPriceAsc(c);
    }

    public List<Product> getByCategoryAndPriceDesc(String c){
        return productRepository.findAllByCategoryOrderByPriceDesc(c);
    }

    public List<Product> getByCategoryAndPriceBetween(String c, double p1, double p2){
        return productRepository.findAllByCategoryAndPriceBetween(c,p1,p2);
    }

    /*Pageable*/
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public Page<Product> findByPriceBetween(double prezzo1, double prezzo2, Pageable pageable) {
        return productRepository.findByPriceBetween(prezzo1, prezzo2, pageable);
    }

    public Page<Product> findProductsByCategoryName(String nomeCategoria, Pageable pageable) {
        return productRepository.findProductsByCategory(nomeCategoria, pageable);
    }

    public Page<Product> findAllByOrderByPriceAsc(Pageable pageable) {
        return productRepository.findAllByOrderByPriceAsc(pageable);
    }

    public Page<Product> findAllByOrderByPriceDesc(Pageable pageable) {
        return productRepository.findAllByOrderByPriceDesc(pageable);
    }

    public Page<Product> findAllByCategoryOrderByPriceAsc(String categoria, Pageable pageable) {
        return productRepository.findAllByCategoryOrderByPriceAsc(categoria, pageable);
    }

    public Page<Product> findAllByCategoryOrderByPriceDesc(String categoria, Pageable pageable) {
        return productRepository.findAllByCategoryOrderByPriceDesc(categoria, pageable);
    }

    public Page<Product> findAllByCategoryAndPriceBetween(String c, double p1, double p2, Pageable pageable) {
        return productRepository.findAllByCategoryAndPriceBetween(c, p1, p2, pageable);
    }

    public Page<Product> findAllByNameContaining(String nome, int page, int size) {
        Sort sort = Sort.by("name").ascending(); // Ordinamento alfabetico per il campo "name"
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllByNameContaining(nome, pageable);
    }


}
