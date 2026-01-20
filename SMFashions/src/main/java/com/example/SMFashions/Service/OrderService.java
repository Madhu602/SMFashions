package com.example.SMFashions.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SMFashions.Dto.OrderResponseDTO;
import com.example.SMFashions.Entity.Account;
import com.example.SMFashions.Entity.Cart;
import com.example.SMFashions.Entity.CartItem;
import com.example.SMFashions.Entity.Order;
import com.example.SMFashions.Entity.OrderItem;
import com.example.SMFashions.Entity.Product;
import com.example.SMFashions.Enum.OrderStatus;
import com.example.SMFashions.Repository.AccountRepository;
import com.example.SMFashions.Repository.CartItemRepository;
import com.example.SMFashions.Repository.CartRepository;
import com.example.SMFashions.Repository.OrderItemRepository;
import com.example.SMFashions.Repository.OrderRepository;
import com.example.SMFashions.Repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;

    // PLACE ORDER
    public OrderResponseDTO placeOrder(Long accountId) {

        Cart cart = cartRepository.findByAccount_IdAndActiveTrue(accountId)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        List<CartItem> cartItems = cartItemRepository.findByCart_Id(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("No items in cart");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Calculate total
        double totalAmount = 0;
        for (CartItem item : cartItems) {

            Product product = item.getProduct();

            // STOCK CHECK
            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for product: " + product.getName()
                );
            }

            totalAmount += item.getPrice() * item.getQuantity();
        }

        // CREATE ORDER
        Order order = new Order();
        order.setOrderNumber(generateOrderNumber());
        order.setAccount(account);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PLACED);

        Order savedOrder = orderRepository.save(order);

        // CREATE ORDER ITEMS + REDUCE STOCK
        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());

            orderItemRepository.save(orderItem);

            // REDUCE STOCK
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        // CLEAR CART
        cartItemRepository.deleteAll(cartItems);
        cart.setActive(false);
        cartRepository.save(cart);

        return new OrderResponseDTO(
                savedOrder.getOrderNumber(),
                totalAmount,
                "Order placed successfully"
        );
    }

    // SIMPLE ORDER NUMBER GENERATOR
    private String generateOrderNumber() {
        return "ORD" + System.currentTimeMillis();
    }
}
