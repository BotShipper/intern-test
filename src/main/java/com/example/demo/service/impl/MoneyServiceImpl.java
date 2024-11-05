package com.example.demo.service.impl;

import com.example.demo.model.Money;
import com.example.demo.repository.MoneyRepository;
import com.example.demo.service.MoneyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class MoneyServiceImpl implements MoneyService {

    MoneyRepository moneyRepository;

    @Override
    public List<Money> getAllMoneys() {
        log.info("Get All Moneys.....");

        return moneyRepository.findAll();
    }
}
