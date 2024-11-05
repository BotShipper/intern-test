package com.example.demo.service;

import com.example.demo.dto.BagDto;

public interface BagService {

    Boolean createBag(long userId);

    BagDto getBagById(long id);

    Boolean updateBag(long id,BagDto bag);
}
