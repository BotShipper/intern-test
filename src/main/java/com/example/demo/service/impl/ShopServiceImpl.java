package com.example.demo.service.impl;

import com.example.demo.dto.BagDto;
import com.example.demo.dto.ItemDto;
import com.example.demo.dto.ShopItemDto;
import com.example.demo.model.Bag;
import com.example.demo.model.Item;
import com.example.demo.service.BagService;
import com.example.demo.service.ItemService;
import com.example.demo.service.ShopService;
import com.example.demo.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ShopServiceImpl implements ShopService {

    ItemService itemService;
    UserService userService;
    BagService bagService;

    Random random = new Random();

    @NonFinal
    Map<Long, Long> cachedShop;

    @NonFinal
    boolean needRefresh = true;

    @Override
    public Map<Long, Long> getShop() {
        log.info("Get Shop.....");

        if (needRefresh || cachedShop == null) {  // Random lại khi cần
            Map<Long, ItemDto> items = itemService.getAllItems();
            cachedShop = items.entrySet().stream()
                    .sorted((a, b) -> random.nextInt(2) - 1)
                    .limit(3)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            quantity ->random.nextLong(10) + 1)
                    );
            needRefresh = false;
        }
        return cachedShop;
//        return null;
    }

    @Override
    public Map<Long, BagDto> getShopRandom() {
        log.info("Get Shop Random.....");

//        if (needRefresh || cachedShop == null) {  // Random lại khi cần
//            Map<Long, ItemDto> items = itemService.getAllItems();
//            cachedShop = items.entrySet().stream()
//                    .sorted((a, b) -> random.nextInt(2) - 1)
//                    .limit(3)
//                    .collect(Collectors.toMap(
//                            Map.Entry::getKey,
//                            entry -> BagDto.builder()
//                                    .items(Map.of(entry.getKey(), random.nextLong(10) + 1))
//                                    .build()
//                    ));
//            needRefresh = false;
//        }
//        return cachedShop;
        return null;
    }

    @Override
    public void refreshShop() {
        log.info("Refresh Shop.....");
        needRefresh = true;
    }

    @Override
    public Boolean UserBuyItem(long userId, long moneyId, Map<Long, Long> items) {
        log.info("User Buy Item.....");

//        if (cachedShop == null || items == null) {
//            return false;
//        }
//
//        // So sánh số lượng mua với shop
//        if (!compareBagWithShop(items, cachedShop)) {
//            return false;
//        }
//
//        // So sánh tiền mua với tiền hiện tại
//        if (!compareMoney(userId, moneyId, items)) {
//            return false;
//        }
//
//        Bag bag = bagService.getBagById(userId);
//
//        if (bag.getItems() == null) {
//            bag.setItems(new HashMap<>());
//        }
//
//        items.forEach((itemId, quantity) -> {
//            long quantityItemInBag = bag.getItems().getOrDefault(itemId, 0L);
//            bag.getItems().put(itemId, quantityItemInBag + quantity);
//        });
//
//        // Cập nhật số lượng trong shop
//        for (Map.Entry<Long, Long> entry : items.entrySet()) {
//            long itemId = entry.getKey();
//            long itemInShop = cachedShop.getOrDefault(itemId, 0L);
//
//            long newQuantity = itemInShop - entry.getValue();
//            if (newQuantity < 0) {
//                return false;
//            }
//
//            cachedShop.put(itemId, newQuantity);
//        }
//
//        bagService.updateBag(, bag);
        return true;
    }

    @Override
    public void updateShop(long shopId, ShopItemDto shopItems) {

    }

    @NonFinal
    boolean compareBagWithShop(Map<Long, Long> items, Map<Long, Long> shop) {
        for (Map.Entry<Long, Long> bagEntry : items.entrySet()) {
            long itemId = bagEntry.getKey();
            long quantityItem = bagEntry.getValue();

            if (shop.containsKey(itemId)) {
                long quantityShop = shop.get(itemId);
                if (quantityItem > quantityShop) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    @NonFinal
    boolean compareMoney(long userId, long moneyId, Map<Long, Long> items) {

//        Bag bag = bagService.getBagById(userId);
//        long userMoneyBalance = bag.getMoneys().getOrDefault(moneyId, 0L);
//        long totalCost = 0L;
//
//        for (Map.Entry<Long, Long> entry : items.entrySet()) {
//            long itemId = entry.getKey();
//            long quantity = entry.getValue();
//
//            Item item = itemService.getItemById(itemId);
//            long itemPrice = item.getMoneys().getOrDefault(moneyId, 0L);
//
//            totalCost += itemPrice * quantity;
//        }
//
//        if (userMoneyBalance < totalCost) {
//            log.info("Không đủ tiền");
//            return false;
//        }
//
//        bag.getMoneys().put(moneyId, userMoneyBalance - totalCost);
//        bagService.updateBag(, bag);

        return true;
    }
}
