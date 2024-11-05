package com.example.demo.controller;

import com.example.demo.service.ShopItemService;
import com.example.demo.service.ShopService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/shops")
public class ShopController {

    ShopService shopService;
    ShopItemService shopItemService;

    @PostMapping("/buy/{userId}")
    public ResponseEntity<?> userBuyItems(@PathVariable long userId, @RequestBody Map<Long, Long> items) {
        try {
            return ResponseEntity.ok().body(shopItemService.UserBuyItem(userId, items));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllItems() {
        try {
            return ResponseEntity.ok().body(shopItemService.getAllShopItems());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshShop() {
        try {
            shopService.refreshShop();
            return ResponseEntity.ok().body("Refresh Shop Success");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
