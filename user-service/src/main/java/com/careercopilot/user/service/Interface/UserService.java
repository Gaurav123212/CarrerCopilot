package com.careercopilot.user.service.Interface;

import com.careercopilot.user.dto.UserProfileResponseDto;
import com.careercopilot.user.dto.UserRequestDto;
import com.careercopilot.user.dto.UserResponseDto;

public interface UserService {
    UserResponseDto createUser(UserRequestDto requestDto);
    UserResponseDto getUserById(Long id);
    UserResponseDto updateUser(Long id, UserRequestDto requestDto);
    void deleteUser(Long id);
    UserProfileResponseDto getUserProfile(Long userId);
}
