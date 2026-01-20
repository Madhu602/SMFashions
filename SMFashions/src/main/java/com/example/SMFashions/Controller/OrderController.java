package com.example.SMFashions.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SMFashions.Dto.OrderResponseDTO;
import com.example.SMFashions.Dto.PlaceOrderDTO;
import com.example.SMFashions.Service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponseDTO> placeOrder(
            @RequestBody PlaceOrderDTO dto
    ) {
        return ResponseEntity.ok(
                orderService.placeOrder(dto.getAccountId())
        );
    }
}
