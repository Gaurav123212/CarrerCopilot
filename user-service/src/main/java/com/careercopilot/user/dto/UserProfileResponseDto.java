package com.careercopilot.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponseDto {

    private UserResponseDto user;
    private List<EducationResponseDto> educations;
    private List<UserSkillResponseDto> skills;
    private List<ExperienceResponseDto> experiences;
    private CareerPreferenceResponseDto careerPreference;
    private List<ResumeResponseDto> resumes;
}
