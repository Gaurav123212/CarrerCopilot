package com.careercopilot.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSkillResponseDto {

    private Long id;
    private String skillName;
    private String proficiencyLevel;
    private Integer yearsOfExperience;
}