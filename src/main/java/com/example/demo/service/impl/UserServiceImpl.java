package com.example.demo.service.impl;

import com.example.demo.Util.SequenceGeneratorService;
import com.example.demo.dto.UserDto;
import com.example.demo.model.Bag;
import com.example.demo.model.Item;
import com.example.demo.model.Money;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BagService;
import com.example.demo.service.MoneyService;
import com.example.demo.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    SequenceGeneratorService sequenceGeneratorService;
    BagService bagService;

    @Override
    public Boolean createUser(UserDto userDto) {

        if (userDto == null) return false;

        User user = new User();
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        log.info("Create {}", user);
        userRepository.save(user);

        bagService.createBag(user.getId());

        return true;
    }

    @Override
    public List<UserDto> getAllUserDto() {
        return null;
    }

    @Override
    public UserDto getUserDtoById(long id) {
        log.info("Get User DTO By Id {}.....", id);

        User user = getUserById(id);

        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    @Override
    public User getUserById(long id) {
        log.info("Get User By Id {}.....", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
    }

    @Override
    public Boolean updateUser(User user) {
        log.info("Update {}", user);

        userRepository.save(user);

        return true;
    }
}
