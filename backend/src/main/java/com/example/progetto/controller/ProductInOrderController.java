package com.example.progetto.controller;

import com.example.progetto.entities.ProductInOrder;
import com.example.progetto.services.OrderService;
import com.example.progetto.services.ProductInOrderService;
import com.example.progetto.support.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prodottiOrdinati")
public class ProductInOrderController {

    @Autowired
    private ProductInOrderService prodottoOrdinatoService;


   @Autowired
   private  OrderService orderService;



    @PreAuthorize("hasRole('admin')")
    @GetMapping("/")
    public List<ProductInOrder> getAllProdottiOrdinati(){
        return this.prodottoOrdinatoService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin') or hasRole('customer')")
    @JsonView(View.Summary.class)
    public ProductInOrder getById(@PathVariable("id") Long id){
        return this.prodottoOrdinatoService.getById(id);
    }
    @PreAuthorize("hasRole('customer')")
    @PutMapping("/{id}/editQuantita")
    @JsonView(View.Summary.class)
    public ResponseEntity<ProductInOrder> editQuantita(@PathVariable("id") Long id, @RequestParam int q){
        ProductInOrder po = this.prodottoOrdinatoService.getById(id);
        this.prodottoOrdinatoService.changeProductInOrderQuantity(po, q);
        orderService.updateOrderTotalPrice(po.getOrder().getOrderID());
        return ResponseEntity.ok(po);
    }

    @GetMapping("/pAsc")
    @PreAuthorize("hasRole('admin')")
    public List<ProductInOrder> sortByPrezzoAsc(){
        return this.prodottoOrdinatoService.getProductInOrderByPriceAsc();
    }

    @GetMapping("/pDesc")
    @PreAuthorize("hasRole('admin')")
    public List<ProductInOrder> sortByPrezzoDesc(){
        return this.prodottoOrdinatoService.getProductInOrderByPriceDesc();
    }

    @DeleteMapping("{id}/delete")
    @PreAuthorize("hasRole('admin') or hasRole('customer')")
    public ResponseEntity<String> deleteProdottoOrdinato(@PathVariable("id") ProductInOrder po){
        Long idTemp = po.getProductInOrderID();
        this.prodottoOrdinatoService.deleteProductInOrder(po);
        return ResponseEntity.ok("Eliminato prodotto ordinato con id:"+idTemp);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('customer')")
    public ResponseEntity<String> addProdottoOrdinato(@RequestBody ProductInOrder po){
        ProductInOrder prodottoAggiunto = prodottoOrdinatoService.createProductInOrder(po);
        return ResponseEntity.ok("Prodotto aggiunto con successo con ID: " + prodottoAggiunto.getProductInOrderID());
    }
}
