package com.example.demo.Util;

import com.example.demo.model.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class JsonService {

    public <T> List<T> readJsonFile(String file, Class<T> clazz) throws IOException {
        log.info("Reading JSON file.....");
        ObjectMapper objectMapper = new ObjectMapper();

        // Đọc file từ resources
        InputStream inputStream = new ClassPathResource("json/" + file + ".json").getInputStream();

        // Sử dụng TypeReference với kiểu cụ thể của lớp T
        return objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }
}
