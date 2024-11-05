package com.example.demo.repository;

import com.example.demo.model.Bag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BagRepository extends MongoRepository<Bag, Long> {
}
