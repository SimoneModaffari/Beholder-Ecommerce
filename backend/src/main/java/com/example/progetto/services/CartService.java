package com.example.progetto.services;

import com.example.progetto.entities.Order;
import com.example.progetto.entities.Product;
import com.example.progetto.entities.ProductInOrder;
import com.example.progetto.repository.CartRepository;
import com.example.progetto.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.progetto.entities.Cart;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private CartRepository cartRepository;

    public Cart findCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElse(null);
    }
    @Transactional
    public boolean checkStockAvailability(Cart cart) {
        boolean isAvailable = true;
        Order o = (cart.getOrderID());
        List<ProductInOrder> list = o.getProductsInOrder();
        for (ProductInOrder item : list) {
            Product product = productRepository.findById(item.getProduct().getProductID()).orElse(null);
            if (product == null || product.getQuantity() < item.getQuantity()) {
                isAvailable = false;
                break;
            }
            product.setQuantity(product.getQuantity() - item.getQuantity());
            try {
                productRepository.save(product);
            } catch (OptimisticLockingFailureException e) {
                isAvailable = false;
                break;
            }
        }
        return isAvailable;
    }


}