package com.careercopilot.user.mapper;

import com.careercopilot.user.dto.UserProfileResponseDto;
import com.careercopilot.user.entity.Education;
import com.careercopilot.user.entity.Experience;
import com.careercopilot.user.entity.User;
import com.careercopilot.user.entity.UserSkill;
import com.careercopilot.user.entity.careerPreference;
import com.careercopilot.user.entity.resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserProfileMapper {

    private final UserMapper userMapper;
    private final EducationMapper educationMapper;
    private final UserSkillMapper userSkillMapper;
    private final ExperienceMapper experienceMapper;
    private final CareerPreferenceMapper careerPreferenceMapper;
    private final ResumeMapper resumeMapper;

    public UserProfileResponseDto toUserProfileResponseDto(
            User user,
            List<Education> educations,
            List<UserSkill> skills,
            List<Experience> experiences,
            careerPreference careerPreference,
            List<resume> resumes) {

        if (user == null && educations == null && skills == null && experiences == null && careerPreference == null && resumes == null) {
            return null;
        }

        return UserProfileResponseDto.builder()
                .user(user != null ? userMapper.userEntityToUserResponseDto(user) : null)
                .educations(educations != null
                        ? educations.stream()
                                .map(educationMapper::educationEntityToEducationResponseDto)
                                .collect(Collectors.toList())
                        : null)
                .skills(skills != null
                        ? skills.stream()
                                .map(userSkillMapper::userSkillEntityToUserSkillResponseDto)
                                .collect(Collectors.toList())
                        : null)
                .experiences(experiences != null
                        ? experiences.stream()
                                .map(experienceMapper::experienceEntityToExperienceResponseDto)
                                .collect(Collectors.toList())
                        : null)
                .careerPreference(careerPreference != null
                        ? careerPreferenceMapper.careerPreferenceEntityToCareerPreferenceResponseDto(careerPreference)
                        : null)
                .resumes(resumes != null
                        ? resumes.stream()
                                .map(resumeMapper::resumeEntityToResumeResponseDto)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }
}
