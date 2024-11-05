package com.example.demo.service.impl;

import com.example.demo.Util.JsonService;
import com.example.demo.dto.AttributeDto;
import com.example.demo.model.Attribute;
import com.example.demo.service.AttributeService;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class AttributeServiceImpl implements AttributeService {

    JsonService jsonService;

    @NonFinal
    List<Attribute> attributes;

    @SneakyThrows
    @PostConstruct
    public void init() {
        log.info("Attributes load JSON file.....");
        attributes = jsonService.readJsonFile("attribute", Attribute.class);
    }

    @Override
    public Map<Long, AttributeDto> getAllAttributes() {
        log.info("Get All Attributes.....");

        return attributes.stream().collect(
                Collectors.toMap(
                        Attribute::getId,
                        temp -> AttributeDto.builder()
                                .name(temp.getName())
                                .build()
                ));
    }


    @Override
    public AttributeDto getAttributeById(long id) {
        log.info("Get Attributes By Id {}.....", id);

        AttributeDto attribute = getAllAttributes().getOrDefault(id, null);
        if (attribute == null) {
            throw new IllegalArgumentException("Attribute not found with id: " + id);
        }
        return attribute;
    }
}
