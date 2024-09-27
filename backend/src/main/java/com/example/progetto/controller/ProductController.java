package com.example.progetto.controller;

import com.example.progetto.entities.Product;
import com.example.progetto.services.ProductService;
import com.example.progetto.support.exception.ProductAlreadyExistException;
import com.example.progetto.utility.ImmagineRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


// Esempio di controller REST
@RestController
@RequestMapping("/api/prodotti")
public class ProductController {
    private final ProductService prodottoService;

    @Autowired
    public ProductController(ProductService prodottoService) {
        this.prodottoService = prodottoService;
    }

    /*
     GETTER
     */


    @GetMapping("/get/{id_prodotto}" )
    public Product getProdottoByID(@PathVariable("id_prodotto") Long ID){
        return prodottoService.getProdottoByID(ID);
    }


    /*Pageable*/
    @GetMapping("/get/")
    public ResponseEntity<Page<Product>> getProdotti(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10")int size
        ){
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(prodottoService.findAllProducts(pageable));
    }

    @GetMapping("/get/pBet")
    public ResponseEntity<Page<Product>> findByPrezzoBetween(
            @RequestParam double prezzo1,
            @RequestParam double prezzo2,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(prodottoService.findByPriceBetween(prezzo1, prezzo2, pageable));
    }

    @GetMapping("/get/categoria")
    public ResponseEntity<Page<Product>> findProdottiByCategoriaNome(
            @RequestParam String nomeCategoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(prodottoService.findProductsByCategoryName(nomeCategoria, pageable));
    }

    @GetMapping("/get/pAsc")
    public ResponseEntity<Page<Product>> findAllByOrderByPrezzoAsc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(prodottoService.findAllByOrderByPriceAsc(pageable));
    }

    @GetMapping("/get/pDesc")
    public ResponseEntity<Page<Product>> findAllByOrderByPrezzoDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(prodottoService.findAllByOrderByPriceDesc(pageable));
    }

    @GetMapping("/get/categoria/{categoria}/prezzoAsc")
    public ResponseEntity<Page<Product>> findAllByCategoriaOrderByPrezzoAsc(
            @PathVariable String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(prodottoService.findAllByCategoryOrderByPriceAsc(categoria, pageable));
    }

    @GetMapping("/get/categoria/{categoria}/prezzoDesc")
    public ResponseEntity<Page<Product>> findAllByCategoriaOrderByPrezzoDesc(
            @PathVariable String categoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(prodottoService.findAllByCategoryOrderByPriceDesc(categoria, pageable));
    }

    @GetMapping("/get/categoria/{categoria}/fasciaPrezzo")
    public ResponseEntity<Page<Product>> findAllByCategoriaAndPrezzoBetween(
            @PathVariable String categoria,
            @RequestParam double prezzo1,
            @RequestParam double prezzo2,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(prodottoService.findAllByCategoryAndPriceBetween(categoria, prezzo1, prezzo2, pageable));
    }

    @GetMapping("/get/nome")
    public ResponseEntity<Page<Product>> findAllByNomeContaining(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(prodottoService.findAllByNameContaining(nome, page, size));
    }

    /*
        POST
     */

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/add")
    public ResponseEntity<String> nuovoProdotto(@RequestBody Product nuovoProdotto) {
        try{
            Product prodottoAggiunto = prodottoService.createProducts(nuovoProdotto);
            return ResponseEntity.ok("Prodotto aggiunto con successo con ID: " + prodottoAggiunto.getProductID());
        }catch (ProductAlreadyExistException p){
            return ResponseEntity.badRequest().body("Prodotto già esistente!");
        }
    }

    /*
        PUT
     */

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{id}/editPrezzo")
    public ResponseEntity<Product> editPrezzoProdotto(@PathVariable("id") Long id,
                                                      @RequestBody double nuovoPrezzo) {
        Product prodotto = prodottoService.editProductPrice(id, nuovoPrezzo);
        return ResponseEntity.ok(prodotto);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{id}/editQuantita")
    public ResponseEntity<Product> editQuantitaProdotto(@PathVariable("id") Long id,
                                                        @RequestBody int nuovaQuantita) {
        Product prodotto = prodottoService.editProductQuantity(id, nuovaQuantita);
        return ResponseEntity.ok(prodotto);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{id}/editNome")
    public ResponseEntity<Product> editNomeProdotto(@PathVariable("id") Long id,
                                                        @RequestBody String nuovoNome) {
        Product prodotto = prodottoService.editProductName(id, nuovoNome);
        return ResponseEntity.ok(prodotto);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{id}/editPicture")
    public ResponseEntity<Product> editImmagine(@PathVariable("id") Long id, @RequestBody String immagineRequest) {
        Product prodotto = prodottoService.editPicture(id, immagineRequest);
        return ResponseEntity.ok(prodotto);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{id}/editDescr")
    public ResponseEntity<Product> editDescrProdotto(@PathVariable("id") Long id,
                                                    @RequestBody String nuovaDescr) {
        Product prodotto = prodottoService.editProductDescr(id, nuovaDescr);
        return ResponseEntity.ok(prodotto);
    }

    @PreAuthorize("hasRole('admin')")
    @PutMapping("/{id}/editCategory")
    public ResponseEntity<Product> editCategoriaProdotto(@PathVariable("id") Long id,
                                                    @RequestBody String nuovaCat) {
        Product prodotto = prodottoService.editProductName(id, nuovaCat);
        return ResponseEntity.ok(prodotto);
    }
    /*
        DELETE
     */
    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteProdotto(@PathVariable ("id") Product p){
        Long tempId = p.getProductID();
        prodottoService.deleteProduct(p);
        return ResponseEntity.ok("Prodotto con eliminato con successo.");
    }
}
