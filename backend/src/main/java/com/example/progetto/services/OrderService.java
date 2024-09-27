package com.example.progetto.services;

import com.example.progetto.entities.*;
import com.example.progetto.repository.OrderRepository;
import com.example.progetto.repository.ProductInOrderRepository;
import com.example.progetto.repository.UserRepository;
import com.example.progetto.utility.ORDER_STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository ordineRepository;
    private final ProductInOrderRepository pior;

    private final UserRepository userRepository;
    @Autowired
    public OrderService(OrderRepository o, ProductInOrderRepository pior, UserRepository userRepository){this.ordineRepository=o;
        this.pior = pior;
        this.userRepository = userRepository;
    }

    @Transactional
    public List<Order> getOrderByUser(User u){
        return this.ordineRepository.findOrderByUser(u);
    }
    @Transactional
    public List<Order> getOrderByUserAndData(User u){
        return this.ordineRepository.findOrderByUserOrderByOrderDataDesc(u);
    }
    @Transactional(readOnly = true)
    public Page<Order> sortByOrderDataAsc(Pageable pageable){
        return this.ordineRepository.findByOrderByOrderDataAsc(pageable);
    }
    @Transactional(readOnly = true)
    public Page<Order> sortByOrderDataDesc(Pageable pageable){
        return this.ordineRepository.findByOrderByOrderDataDesc(pageable);
    }
    @Transactional(readOnly = true)
    public Page<Order> sortByOrderStatusAsc(Pageable pageable){
        return this.ordineRepository.findByOrderByOrderStatusAsc(pageable);
    }
    @Transactional(readOnly = true)
    public Page<Order> sortByOrderStatusDesc(Pageable pageable){
        return this.ordineRepository.findByOrderByOrderStatusDesc(pageable);
    }
    @Transactional(readOnly = true)
    public Page<Order> findByOrderStatus(ORDER_STATUS stato, Pageable pageable){
        return this.ordineRepository.findByOrderStatus(stato,pageable);
    }
    @Transactional(readOnly = true)
    public Order findById(Long id){
        return this.ordineRepository.findByOrderID(id);
    }
    @Transactional(readOnly = true)
    public Page<Order> getOrders(Pageable pageable) {
        Page<Order> ordini =  ordineRepository.findAll(pageable);
        return ordini;
    }

    @Transactional
    public void saveOrder(Order o){
        ordineRepository.save(o);
    }




    @Transactional
    public void updateOrderTotalPrice(Long idOrdine) {
        Order ordine = ordineRepository.findById(idOrdine).orElse(null);
        if (ordine == null || ordine.getOrderStatus()!=ORDER_STATUS.ACTIVE) {
            return;
        }
        double prezzoTotale = computeTotalPrice(ordine);
        ordine.setTotal(prezzoTotale);
        ordineRepository.save(ordine);
    }

    private double computeTotalPrice(Order ordine) {
        double prezzoTotale = 0;
        List<ProductInOrder> prodotti = ordine.getProductsInOrder();
        for (ProductInOrder pio : prodotti) {
            prezzoTotale += pio.getQuantity() * pio.getProduct().getPrice();
        }
        BigDecimal bd = new BigDecimal(prezzoTotale).setScale(2, RoundingMode.HALF_UP);
        prezzoTotale = bd.doubleValue();
        return prezzoTotale;
    }

    @Transactional
    public void deleteOrder(Order o){
        if(!ordineRepository.existsById(o.getOrderID())){
            throw new IllegalArgumentException("Ordine con id: "+o.getOrderID()+" inesistente!");
        }
        ordineRepository.delete(o);
    }

    @Transactional(readOnly = false)
    public void addToCart(Product product, User user, int quantity){
        ProductInOrder productInOrder = new ProductInOrder();
        productInOrder.setProduct(product);
        productInOrder.setQuantity(quantity);
        Order order = user.getOrdineAttivo();
        if (order == null) {
            order = new Order();
            order.setUser(user);
            order.setProductsInOrder(new LinkedList<>());
            order.setOrderStatus(ORDER_STATUS.ACTIVE);
            ordineRepository.save(order);
        }
        productInOrder.setOrder(order);
        double totale = order.getTotal()+(product.getPrice()*quantity);
        BigDecimal bd = new BigDecimal(totale).setScale(2, RoundingMode.HALF_UP);
        totale = bd.doubleValue();
        System.out.println(totale);
        order.setTotal(totale);
        pior.save(productInOrder);
        order.getProductsInOrder().add(productInOrder);
        Cart c = user.getCart();
        c.setOrderID(order);
        order.setTotal(computeTotalPrice(order));
        ordineRepository.save(order);
        List<Order> l = user.getOrders();
        l.add(order);
        user.setOrders(l);
        userRepository.save(user);
    }

    public boolean updateOrderStatusToPending(Order o, String indirizzo, String cap) {
        o.setOrderStatus(ORDER_STATUS.PROCESSING);
        o.setAddress(indirizzo+","+cap);
        ordineRepository.save(o);
        return true;
    }
}
