package com.careercopilot.user.service.impl;

import com.careercopilot.user.dto.UserProfileResponseDto;
import com.careercopilot.user.dto.UserRequestDto;
import com.careercopilot.user.dto.UserResponseDto;
import com.careercopilot.user.entity.User;
import com.careercopilot.user.mapper.UserMapper;
import com.careercopilot.user.repository.UserRepository;
import com.careercopilot.user.service.Interface.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("User already exists with email: " + requestDto.getEmail());
        }

        User userEntity = userMapper.requestDtoToUserEntity(requestDto);
        User savedUser = userRepository.save(userEntity);
        return userMapper.userEntityToUserResponseDto(savedUser);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return null;
    }

    @Override
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public UserProfileResponseDto getUserProfile(Long userId) {
        return null;
    }
}