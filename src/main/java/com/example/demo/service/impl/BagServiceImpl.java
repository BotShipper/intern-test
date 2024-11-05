package com.example.demo.service.impl;

import com.example.demo.dto.BagDto;
import com.example.demo.model.Bag;
import com.example.demo.model.Money;
import com.example.demo.repository.BagRepository;
import com.example.demo.service.BagService;
import com.example.demo.service.MoneyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
public class BagServiceImpl implements BagService {

    BagRepository bagRepository;

    MoneyService moneyService;

    @Override
    public Boolean createBag(long userId) {

        if (userId <= 0) return false;


        List<Money> moneys = moneyService.getAllMoneys();
        Map<Long, Long> setMoneys = moneys.stream().collect(
                Collectors.toMap(Money::getId, money -> 20000L)
        );

        Bag bag = new Bag(userId, null, setMoneys);
        bagRepository.save(bag);
        log.info("Create {}", bag);

        return true;
    }

    @Override
    public BagDto getBagById(long id) {
        log.info("Get Bag By Id {}.....", id);

        Bag bag = bagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bag not found with id: " + id));

        return BagDto.builder()
                .items(bag.getItems())
                .moneys(bag.getMoneys())
                .build();
    }

    @Override
    public Boolean updateBag(long id, BagDto bag) {

        Bag bag1 = new Bag();
        bag1.setId(id);
        bag1.setItems(bag.getItems());
        bag1.setMoneys(bag.getMoneys());
        log.info("Update {}", bag1);
        bagRepository.save(bag1);

        return true;
    }
}
