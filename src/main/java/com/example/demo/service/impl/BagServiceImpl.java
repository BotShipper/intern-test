package com.example.demo.service.impl;

import com.example.demo.dto.BagDto;
import com.example.demo.model.Bag;
import com.example.demo.repository.BagRepository;
import com.example.demo.service.BagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class BagServiceImpl implements BagService {

    BagRepository bagRepository;

    @Override
    public void createBag(long userId) {

        Map<Long, Long> moneys = new HashMap<>();
        moneys.put(1L, 10000L); // Xu
        moneys.put(2L, 10000L);  // Kim cương

        Bag bag = new Bag(userId, moneys);
        bagRepository.save(bag);
        log.info("Create {}", bag);
    }

    @Override
    public BagDto getBagById(long id) {
        log.info("Get Bag By Id {}.....", id);

        Bag bag = bagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bag not found with id: " + id));

        return BagDto.builder()
                .items(bag.getItems())
                .build();
    }

    @Override
    public void updateBag(long id, BagDto bagDto) {

        Bag bag = bagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bag not found with id: " + id));
        bag.setItems(bagDto.getItems());

        log.info("Update {}", bag);
        bagRepository.save(bag);

    }
}
