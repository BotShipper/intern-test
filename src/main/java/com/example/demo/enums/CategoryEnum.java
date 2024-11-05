package com.example.demo.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public enum CategoryEnum {
    FOOD(1L, "Đồ ăn"),
    BEVERAGE(2L, "Đồ uống"),
    ACCESSORY(3L, "Phụ kiện");

    Long id;
    String name;

    // Khởi tạo Map một lần duy nhất
    private static final Map<Long, String> ID_TO_NAME_MAP = Stream.of(values())
            .collect(Collectors.toMap(CategoryEnum::getId, CategoryEnum::getName));

    private static final Map<String, Long> NAME_TO_ID_MAP = Stream.of(values())
            .collect(Collectors.toMap(CategoryEnum::getName, CategoryEnum::getId));

    public static String getNameById(long id) {
        // Sử dụng Map để lấy tên và kiểm tra null
        return ID_TO_NAME_MAP.getOrDefault(id, "Chưa đặt tên");
    }

    public static Long getIdByName(String name) {
        // Sử dụng Map để lấy id và kiểm tra null
        return NAME_TO_ID_MAP.getOrDefault(name, 0L);
    }
}
