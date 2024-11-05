package com.example.demo.service;

import com.example.demo.dto.ShopItemDto;

import java.util.Map;

public interface ShopItemService {

    Map<Long, ShopItemDto> getAllShopItems();

    ShopItemDto getShopItemById(long id);

    ShopItemDto getShopItemByItemId(long itemId);

    void updateShop(long id, ShopItemDto shopItemDto);

    String UserBuyItem(long userId, Map<Long, Long> items);
}
