package com.example.demo.service;

import com.example.demo.dto.UserDto;

public interface UserService {

    void createUser(UserDto userDto);

    UserDto getUserById(long id);

}
