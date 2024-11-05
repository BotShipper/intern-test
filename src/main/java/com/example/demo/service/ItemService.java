package com.example.demo.service;

import com.example.demo.dto.ItemDto;
import com.example.demo.model.Item;

import java.util.Map;

public interface ItemService {

    Map<Long, ItemDto> getAllItems();

    ItemDto getItemDtoById(long id);

    Item getItemById(long id);
}
