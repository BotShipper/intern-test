package com.example.demo.service;

import com.example.demo.dto.BagDto;
import com.example.demo.dto.ShopDto;
import com.example.demo.dto.ShopItemDto;
import com.example.demo.model.ShopItem;

import java.util.List;
import java.util.Map;

public interface ShopService {

    Map<Long, Long> getShop();

    Map<Long, BagDto> getShopRandom();

    void refreshShop();

    Boolean UserBuyItem(long userId, long moneyId, Map<Long, Long> bags);

    void updateShop(long shopId, ShopItemDto shopItems);
}
