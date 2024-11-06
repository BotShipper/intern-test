package com.example.demo.service.impl;

import com.example.demo.Util.SequenceGeneratorService;
import com.example.demo.dto.UserDto;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BagService;
import com.example.demo.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void createUser(UserDto userDto) {

        User user = new User();
        user.setId(sequenceGeneratorService.generateSequence(User.SEQUENCE_NAME));
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        log.info("Create {}", user);
        userRepository.save(user);

        bagService.createBag(user.getId());
    }

    @Override
    public UserDto getUserById(long id) {
        log.info("Get User By Id {}.....", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));

        return UserDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
