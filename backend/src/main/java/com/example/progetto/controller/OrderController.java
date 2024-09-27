package com.example.progetto.controller;

import com.example.progetto.configurations.JwtDecoder;
import com.example.progetto.dto.OrderDTO;
import com.example.progetto.dto.OrderRecord;
import com.example.progetto.entities.Order;
import com.example.progetto.entities.Product;
import com.example.progetto.entities.User;
import com.example.progetto.services.OrderService;
import com.example.progetto.services.ProductService;
import com.example.progetto.services.UserService;
import com.example.progetto.support.View;
import com.example.progetto.support.exception.OrderAlreadyExistException;
import com.example.progetto.utility.ORDER_STATUS;
import com.fasterxml.jackson.annotation.JsonView;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordine")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    private final ProductService productService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    public OrderController(OrderService o, ProductService p, UserService u){
        this.orderService =o;
        this.productService = p;
        this.userService = u;

    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<Page<OrderDTO>> getAllOrders(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> ordersPage = orderService.getOrders(pageable);

        // Converti la Page<Order> in Page<OrderDTO>
        Page<OrderDTO> orderDTOPage = ordersPage.map(order -> {
            OrderDTO dto = new OrderDTO();
            dto.setOrderID(order.getOrderID());
            dto.setTotal(order.getTotal());
            dto.setOrderStatus(order.getOrderStatus());
            dto.setAddress(order.getAddress());
            dto.setOrderData(order.getOrderData());
            dto.setUserID(order.getUser() != null ? order.getUser().getUserID() : null);
            return dto;
        });

        return ResponseEntity.ok(orderDTOPage);
    }


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/dataAsc")
    public ResponseEntity<Page<Order>> getByDataAsc(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.orderService.sortByOrderDataAsc(pageable));
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/dataDesc")
    public ResponseEntity<Page<Order>> getByDataDesc(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.orderService.sortByOrderDataDesc(pageable));
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/statoAsc")
    public ResponseEntity<Page<Order>> getByStatoAsc(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.orderService.sortByOrderStatusAsc(pageable));
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/statoDesc")
    public ResponseEntity<Page<Order>> getByStatoDesc(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.orderService.sortByOrderStatusDesc(pageable));
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/stato/{stato}")
    public ResponseEntity<Page<Order>> getByStatus(@PathVariable ORDER_STATUS stato,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(this.orderService.findByOrderStatus(stato,pageable));
    }



    @GetMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    @JsonView(View.Summary.class)
    public Order getByID(@PathVariable Long id){
        return this.orderService.findById(id);
    }

    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("{id}/delete")
    public ResponseEntity<String> deleteOrdine(@PathVariable Long id){
        Order o = orderService.findById(id);
        orderService.deleteOrder(o);
        return ResponseEntity.ok("Ordine con id: "+id+" eliminato con successo.");
    }

    @PreAuthorize("hasRole('customer')")
    @PostMapping("/userOrders")
    @JsonView(View.Summary.class)
    public List<Order> getOrderByUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        token = token.replace("Bearer ", "");
        String userEmail = JwtDecoder.getUserEmail(token);
        if (userEmail == null) {
            return null;
        }
        User u = this.userService.findByEmail(userEmail);
        System.out.println(u.getUserID());
        List<Order> orders = orderService.getOrderByUserAndData(u);
        return orders;
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/adminUserOrder/{email}")
    @JsonView(View.Summary.class)
    public ResponseEntity<List<Order>> getOrderByUserEmail(@PathVariable String email){
        User u = this.userService.findByEmail(email);
        List<Order> ordini = this.orderService.getOrderByUser(u);
        return ResponseEntity.ok(ordini);
    }
    @PreAuthorize("hasRole('customer')")
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,
                                            @RequestParam long id,
                                            @RequestParam int quantity) throws OrderAlreadyExistException {


        // Rimuovi il prefisso "Bearer " dal token
        token = token.replace("Bearer ", "");

        // Decodifica il token per ottenere i claims


        String userEmail = JwtDecoder.getUserEmail(token);
        if (userEmail == null) {
            return ResponseEntity.badRequest().body("Token non valido");
        }

        Product product = productService.getProdottoByID(id);
        if (product == null) {
            return ResponseEntity.badRequest().body("Prodotto non trovato");
        }
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.badRequest().body("Utente non trovato");
        }
        this.orderService.addToCart(product, user, quantity);
        return ResponseEntity.ok("Prodotto aggiunto al carrello con successo");
    }


}
