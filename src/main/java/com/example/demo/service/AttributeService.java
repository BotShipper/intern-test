package com.example.demo.service;

import com.example.demo.dto.AttributeDto;

import java.util.Map;

public interface AttributeService {

    Map<Long, AttributeDto> getAllAttributes();

    AttributeDto getAttributeById(long id);
}
