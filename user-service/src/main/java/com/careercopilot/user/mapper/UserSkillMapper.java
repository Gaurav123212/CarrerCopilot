package com.careercopilot.user.mapper;

import com.careercopilot.user.dto.UserSkillRequestDto;
import com.careercopilot.user.dto.UserSkillResponseDto;
import com.careercopilot.user.entity.UserSkill;
import org.springframework.stereotype.Component;

@Component
public class UserSkillMapper {

    public UserSkill requestDtoToUserSkillEntity(UserSkillRequestDto dto) {
        if (dto == null) {
            return null;
        }

        UserSkill userSkill = new UserSkill();
        userSkill.setSkillName(dto.getSkillName());
        userSkill.setProficiencyLevel(dto.getProficiencyLevel());
        if (dto.getYearsOfExperience() != null) {
            userSkill.setYearsOfExperience(dto.getYearsOfExperience().doubleValue());
        }
        return userSkill;
    }

    public UserSkillResponseDto userSkillEntityToUserSkillResponseDto(UserSkill userSkill) {
        if (userSkill == null) {
            return null;
        }

        return UserSkillResponseDto.builder()
                .id(userSkill.getId())
                .skillName(userSkill.getSkillName())
                .proficiencyLevel(userSkill.getProficiencyLevel())
                .yearsOfExperience(userSkill.getYearsOfExperience() != null ? userSkill.getYearsOfExperience().intValue() : null)
                .build();
    }
}
