package com.careercopilot.user.mapper;

import com.careercopilot.user.dto.UserRequestDto;
import com.careercopilot.user.dto.UserResponseDto;
import com.careercopilot.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User requestDtoToUserEntity(UserRequestDto dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setProfilePictureUrl(dto.getProfilePictureUrl());
        return user;
    }

    public UserResponseDto userEntityToUserResponseDto(User user) {
        if (user == null) {
            return null;
        }

        return UserResponseDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profilePictureUrl(user.getProfilePictureUrl())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
