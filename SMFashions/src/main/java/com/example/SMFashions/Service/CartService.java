package com.example.SMFashions.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SMFashions.Dto.AddToCartDTO;
import com.example.SMFashions.Dto.CartItemResponseDTO;
import com.example.SMFashions.Dto.CartResponseDTO;
import com.example.SMFashions.Entity.Account;
import com.example.SMFashions.Entity.Cart;
import com.example.SMFashions.Entity.CartItem;
import com.example.SMFashions.Entity.Product;
import com.example.SMFashions.Repository.AccountRepository;
import com.example.SMFashions.Repository.CartItemRepository;
import com.example.SMFashions.Repository.CartRepository;
import com.example.SMFashions.Repository.ProductRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProductRepository productRepository;

    
    //getorcreate
    private Cart getOrCreateCart(Long accountId) {

        if (accountId == null) {
            throw new RuntimeException("Account ID must not be null");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return cartRepository.findByAccount_IdAndActiveTrue(accountId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setAccount(account);
                    cart.setActive(true);
                    return cartRepository.save(cart);
                });
    }


    // ADD TO CART
    public String addToCart(AddToCartDTO dto) {

        if (dto.getAccountId() == null) {
            throw new RuntimeException("Account ID is required");
        }

        if (dto.getProductId() == null) {
            throw new RuntimeException("Product ID is required");
        }

        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        Cart cart = getOrCreateCart(dto.getAccountId());

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = cartItemRepository
                .findByCart_IdAndProduct_Id(cart.getId(), product.getId())
                .orElse(null);

        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());
            item.setPrice(product.getPrice());
            item.setAddedAt(LocalDateTime.now()); // ✅ FIX
        } else {
            item.setQuantity(item.getQuantity() + dto.getQuantity());
        }

        cartItemRepository.save(item);
        return "Product added to cart";
    }


    // VIEW CART
    public CartResponseDTO viewCart(Long accountId) {

        Cart cart = cartRepository.findByAccount_IdAndActiveTrue(accountId)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        List<CartItem> items = cartItemRepository.findByCart_Id(cart.getId());

        List<CartItemResponseDTO> responseItems = items.stream()
                .map(i -> new CartItemResponseDTO(
                        i.getProduct().getId(),
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getPrice(),
                        i.getPrice() * i.getQuantity()
                ))
                .toList();

        double total = responseItems.stream()
                .mapToDouble(CartItemResponseDTO::getTotal)
                .sum();

        return new CartResponseDTO(cart.getId(), responseItems, total);
    }

    // REMOVE ITEM
    public String removeItem(Long accountId, Long productId) {

        Cart cart = cartRepository.findByAccount_IdAndActiveTrue(accountId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cartItemRepository
                .findByCart_IdAndProduct_Id(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        cartItemRepository.delete(item);
        return "Item removed from cart";
    }
}
