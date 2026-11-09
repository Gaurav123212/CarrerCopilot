package com.careercopilot.user.service.impl;

import com.careercopilot.user.dto.UserProfileResponseDto;
import com.careercopilot.user.dto.UserRequestDto;
import com.careercopilot.user.dto.UserResponseDto;
import com.careercopilot.user.entity.*;
import com.careercopilot.user.exceptions.DuplicateResourceException;
import com.careercopilot.user.exceptions.ResourceNotFoundException;
import com.careercopilot.user.mapper.EducationMapper;
import com.careercopilot.user.mapper.UserMapper;
import com.careercopilot.user.mapper.UserProfileMapper;
import com.careercopilot.user.repository.*;
import com.careercopilot.user.service.Interface.UserService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EducationRepository educationRepository;
    private final EducationMapper educationMapper;
    private final UserProfileMapper userProfileMapper;
    private final UserSkillRepository userSkillRepository;
    private final ExperienceRepository experienceRepository;
    private final CareerPreferenceRepository careerPreferenceRepository;
    private final ResumeRepository resumeRepository;


    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateResourceException("User already exists with email: " + requestDto.getEmail());
        }

        User userEntity = userMapper.requestDtoToUserEntity(requestDto);
        User savedUser = userRepository.save(userEntity);
        return userMapper.userEntityToUserResponseDto(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.userEntityToUserResponseDto(userEntity);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        existingUser.setEmail(requestDto.getEmail());
        existingUser.setFullName(requestDto.getFullName());
        existingUser.setPhoneNumber(requestDto.getPhoneNumber());
        existingUser.setProfilePictureUrl(requestDto.getProfilePictureUrl());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.userEntityToUserResponseDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserProfileResponseDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        List<Education> educations = educationRepository.findByUserId(userId);
        List<UserSkill> skills = userSkillRepository.findByUserId(userId);
        List<Experience> experiences = experienceRepository.findByUserId(userId);
        careerPreference careerPreference = careerPreferenceRepository.findByUserId(userId).orElse(null);
        List<resume> resumes = resumeRepository.findByUserId(userId);

        return userProfileMapper.toUserProfileResponseDto(
                user, educations, skills, experiences, careerPreference, resumes
        );    }
}