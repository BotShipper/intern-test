package com.example.demo.service;

import com.example.demo.dto.BagDto;

public interface BagService {

    void createBag(long userId);

    BagDto getBagById(long id);

    void updateBag(long id, BagDto bag);
}
