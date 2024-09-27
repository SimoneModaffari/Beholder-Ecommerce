package com.example.progetto.controller;

import com.example.progetto.entities.Cart;
import com.example.progetto.entities.Order;
import com.example.progetto.entities.Product;
import com.example.progetto.entities.User;
import com.example.progetto.repository.CartRepository;
import com.example.progetto.repository.UserRepository;
import com.example.progetto.services.CartService;
import com.example.progetto.services.OrderService;
import com.example.progetto.services.ProductService;
import com.example.progetto.services.UserService;
import com.example.progetto.support.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired OrderService orderService;
    @Autowired UserService userService;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/{email}/carrello")
    @PreAuthorize("hasRole('customer')")
    @JsonView({View.Summary.class})
    public Cart getCartByEmail(@PathVariable String email){
        return  userService.getCartByEmail(email);
    }
    @PreAuthorize("hasRole('customer')")
    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseOrder(@RequestParam Long cartId,
                                                @RequestParam String indirizzo,
                                                @RequestParam String cap) {
        Cart c = cartService.findCartById(cartId);
        boolean isPurchaseSuccessful = cartService.checkStockAvailability(c);
        if (isPurchaseSuccessful) {

            boolean isUpdateSuccessful = orderService.updateOrderStatusToPending(c.getOrderID(), indirizzo,cap);
            if (isUpdateSuccessful) {
                return ResponseEntity.ok("Acquisto effettuato con successo e stato dell'ordine aggiornato");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nell'aggiornamento dello stato dell'ordine");
            }
        } else {
            return ResponseEntity.badRequest().body("Acquisto non riuscito a causa di quantità non disponibili");
        }
    }
}