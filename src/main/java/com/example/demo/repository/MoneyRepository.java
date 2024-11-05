package com.example.demo.repository;

import com.example.demo.model.Money;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoneyRepository extends MongoRepository<Money, Long> {
}
