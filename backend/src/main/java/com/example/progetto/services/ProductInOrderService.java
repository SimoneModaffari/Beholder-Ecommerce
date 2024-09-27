package com.example.progetto.services;

import com.example.progetto.entities.ProductInOrder;
import com.example.progetto.repository.OrderRepository;
import com.example.progetto.repository.ProductInOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductInOrderService {
    private final ProductInOrderRepository productInOrderRepository;

    private final OrderService orderService;



    @Autowired
    public ProductInOrderService(ProductInOrderRepository por, OrderRepository ordineRepository, OrderService orderService){
        this.productInOrderRepository =por;

        this.orderService = orderService;
    }
    public List<ProductInOrder> getAll(){
        return productInOrderRepository.findAll();
    }

    public ProductInOrder getById(Long id){
        return productInOrderRepository.findByProductInOrderID(id);
    }
    public List<ProductInOrder> getProductInOrderByPriceAsc(){
        return productInOrderRepository.findAllByOrderByProductPriceAsc();
    }

    public List<ProductInOrder> getProductInOrderByPriceDesc(){
        return productInOrderRepository.findAllByOrderByProductPriceDesc();
    }

    @Transactional
    public void deleteProductInOrder(ProductInOrder p){
        if(!productInOrderRepository.existsById(p.getProductInOrderID())){
            throw new IllegalArgumentException("Prodotto con id:"+p.getProductInOrderID()+" inesistente!");
        }
        productInOrderRepository.delete(p);
    }

    @Transactional
    public void changeProductInOrderQuantity(ProductInOrder p, int x){
        if(!productInOrderRepository.existsById(p.getProductInOrderID())){
            throw new IllegalArgumentException("Prodotto con id:"+p.getProductInOrderID()+" inesistente!");
        }
        p.setQuantity(x);
        productInOrderRepository.save(p);
        orderService.updateOrderTotalPrice(p.getOrder().getOrderID());
    }

    @Transactional
    public ProductInOrder createProductInOrder(ProductInOrder po){
        if(productInOrderRepository.existsById(po.getProductInOrderID())){
            throw new IllegalArgumentException("Prodotto con id:"+po.getProductInOrderID()+" già esistente!");
        }
        po.setProductInOrderID(null);
        return this.productInOrderRepository.save(po);
    }
}
