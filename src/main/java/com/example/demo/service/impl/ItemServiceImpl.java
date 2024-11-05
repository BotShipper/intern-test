package com.example.demo.service.impl;

import com.example.demo.Util.JsonService;
import com.example.demo.dto.ItemDto;
import com.example.demo.enums.CategoryEnum;
import com.example.demo.model.Item;
import com.example.demo.service.AttributeService;
import com.example.demo.service.ItemService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ItemServiceImpl implements ItemService {

    JsonService jsonService;
    AttributeService attributeService;

    @NonFinal
    List<Item> items;

    @SneakyThrows
    @PostConstruct
    public void init() {
        log.info("Items load JSON file.....");
        items = jsonService.readJsonFile("item", Item.class);
    }

    @Override
    public Map<Long, ItemDto> getAllItems() {
        log.info("Get All Item.....");

        return items.stream()
                .collect(Collectors.toMap(
                        Item::getId,
                        item -> ItemDto.builder()
                                .name(item.getName())
                                .attributeId(item.getAttributeId())
                                .categoryId(item.getCategoryId())
                                .build()
                ));
    }

    @Override
    public ItemDto getItemDtoById(long id) {
        log.info("Get Item DTO By Id {}.....", id);

        ItemDto itemDto = getAllItems().getOrDefault(id, null);
        if (itemDto == null) {
            throw new IllegalArgumentException("Item not found with id: " + id);
        }

        return itemDto;
    }

    @Override
    public Item getItemById(long id) {
        log.info("Get Item By Id {}.....", id);
//        ItemDto itemDto = getItemDtoById(id);
//
//        Item item = new Item();
//        item.setId(id);
//        item.setMoneys(itemDto.getMoneys());
//        item.setName(item.getName());
//        item.setCategoryId(CategoryEnum.getIdByName(itemDto.getName()));

        return null;
    }

}