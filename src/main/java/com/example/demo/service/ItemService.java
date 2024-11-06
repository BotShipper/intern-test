package com.example.demo.service;

import com.example.demo.dto.ItemDto;

import java.util.Map;

public interface ItemService {

    Map<Long, ItemDto> getAllItems();

    ItemDto getItemById(long id);

}
