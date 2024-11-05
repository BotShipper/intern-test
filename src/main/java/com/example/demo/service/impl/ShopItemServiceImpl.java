package com.example.demo.service.impl;

import com.example.demo.Util.JsonService;
import com.example.demo.dto.AttributeDto;
import com.example.demo.dto.BagDto;
import com.example.demo.dto.ItemDto;
import com.example.demo.dto.ShopItemDto;
import com.example.demo.model.ShopItem;
import com.example.demo.service.*;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ShopItemServiceImpl implements ShopItemService {

    JsonService jsonService;
    BagService bagService;
    ItemService itemService;
    AttributeService attributeService;

    @NonFinal
    List<ShopItem> shopItems;

    @SneakyThrows
    @PostConstruct
    public void init() {
        log.info("Shop Items load JSON file.....");
        shopItems = jsonService.readJsonFile("shopItem", ShopItem.class);
    }

    @Override
    public Map<Long, ShopItemDto> getAllShopItems() {
        log.info("Get All Shop Items.....");

        return shopItems.stream().collect(
                Collectors.toMap(
                        ShopItem::getId,
                        temp -> ShopItemDto.builder()
                                .price(temp.getPrice())
                                .itemId(temp.getItemId())
                                .priceType(temp.getPriceType())
                                .maxBuy(temp.getMaxBuy())
                                .build()
                )
        );
    }

    @Override
    public ShopItemDto getShopItemById(long id) {
        log.info("Get Shop Item By Id {}.....", id);

        ShopItemDto shopItemDto = getAllShopItems().getOrDefault(id, null);
        if (shopItemDto == null) {
            throw new IllegalArgumentException("Shop item not found with id: " + id);
        }

        return shopItemDto;
    }

    @Override
    public ShopItemDto getShopItemByItemId(long itemId) {
        log.info("Get Shop Item By Item Id {}.....", itemId);

        return null;
    }

    @Override
    public void updateShop(long id, ShopItemDto shopItemDto) {

        ShopItem shopItem = shopItems.get((int) id - 1);

       if (shopItem != null) {
           shopItem.setItemId(shopItemDto.getItemId());
           shopItem.setMaxBuy(shopItemDto.getMaxBuy());
           shopItem.setPrice(shopItemDto.getPrice());
           shopItem.setPriceType(shopItemDto.getPriceType());
       } else {
           throw new IllegalArgumentException("Shop Item not found with id: " + id);
       }
       log.info("Update {}", shopItem);
    }

    @Override
    public String UserBuyItem(long userId, Map<Long, Long> items) { // id của shopItem và số lượng mua mỗi item
        log.info("User {} Buy Item {}.....", userId, items);

        if (items.size() > 1 ) return "Can only buy 1 item";
        if (items.isEmpty()) return "Buy item fail";

        // Kiểm tra số lượng mua
        for (long shopId : items.keySet()) {
            ShopItemDto shopItem = getShopItemById(shopId);
            ItemDto item = itemService.getItemDtoById(shopItem.getItemId());
            AttributeDto attribute = attributeService.getAttributeById(item.getAttributeId());
            Long maxBuy = shopItem.getMaxBuy();
            Long quantity = items.get(shopId);
            if (maxBuy == null) continue;
            if (quantity != null && maxBuy < quantity) {
                return "Quantity items (" + item.getName() + ") cannot be greater than quantity items buy in day";
            }
        }

        // Kiểm tra tiền người dùng
        BagDto bag = bagService.getBagById(userId);
        Map<Long, Long> itemsInBag = bag.getItems();
        Map<Long, Long> moneys = bag.getMoneys();

        for (long shopId : items.keySet()) {
            ShopItemDto shopItem = getShopItemById(shopId);
            long totalMoneyPay = shopItem.getPrice();
            long wallet = moneys.get(shopItem.getPriceType());
            if (wallet < totalMoneyPay) {
                return "Not enough money";
            } else {

                // Trừ tiền
                wallet -= totalMoneyPay;
                moneys.put(shopItem.getPriceType(), wallet);
                bag.setMoneys(moneys);

                // Thêm item vào túi
                if (itemsInBag.containsKey(shopItem.getItemId())) {
                    itemsInBag.put(shopItem.getItemId(), items.get(shopId) + itemsInBag.get(shopItem.getItemId()));
                } else {
                    itemsInBag.put(shopItem.getItemId(), items.get(shopId));
                }
                bag.setItems(itemsInBag);

                // Trừ item trong shop
                shopItem.setMaxBuy(shopItem.getMaxBuy() - items.get(shopId));
                updateShop(shopId, shopItem);
            }
        }

        bagService.updateBag(userId ,bag);

        return "Buy item success";
    }

}
