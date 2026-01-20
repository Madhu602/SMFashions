package com.example.SMFashions.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SMFashions.Dto.AddToCartDTO;
import com.example.SMFashions.Dto.CartResponseDTO;
import com.example.SMFashions.Service.CartService;



@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // ADD TO CART
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(
            @RequestBody AddToCartDTO dto
    ) {
        return ResponseEntity.ok(cartService.addToCart(dto));
    }

    // VIEW CART
    @GetMapping("/view/{accountId}")
    public ResponseEntity<CartResponseDTO> viewCart(
            @PathVariable Long accountId
    ) {
        return ResponseEntity.ok(cartService.viewCart(accountId));
    }

    // REMOVE ITEM
    @DeleteMapping("/remove/{accountId}/{productId}")
    public ResponseEntity<String> removeItem(
            @PathVariable Long accountId,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(
                cartService.removeItem(accountId, productId)
        );
    }
}
