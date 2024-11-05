package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    Boolean createUser(UserDto userDto);

    List<UserDto> getAllUserDto();

    UserDto getUserDtoById(long id);

    User getUserById(long id);

    Boolean updateUser(User user);
}
