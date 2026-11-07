package com.careercopilot.user.mapper;

import com.careercopilot.user.dto.ExperienceRequestDto;
import com.careercopilot.user.dto.ExperienceResponseDto;
import com.careercopilot.user.entity.Experience;
import org.springframework.stereotype.Component;

@Component
public class ExperienceMapper {

    public Experience requestDtoToExperienceEntity(ExperienceRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Experience experience = new Experience();
        experience.setTitle(dto.getTitle());
        experience.setExperienceType(dto.getExperienceType());
        experience.setOrganizationName(dto.getOrganizationName());
        experience.setDescription(dto.getDescription());
        experience.setStartDate(dto.getStartDate());
        experience.setEndDate(dto.getEndDate());
        if (dto.getIsCurrent() != null) {
            experience.setCurrent(dto.getIsCurrent());
        }
        experience.setLocation(dto.getLocation());
        experience.setReferenceUrl(dto.getReferenceUrl());
        experience.setTechnologies(dto.getTechnologies());
        return experience;
    }

    public ExperienceResponseDto experienceEntityToExperienceResponseDto(Experience experience) {
        if (experience == null) {
            return null;
        }

        return ExperienceResponseDto.builder()
                .id(experience.getId())
                .title(experience.getTitle())
                .experienceType(experience.getExperienceType())
                .organizationName(experience.getOrganizationName())
                .description(experience.getDescription())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .isCurrent(experience.isCurrent())
                .location(experience.getLocation())
                .referenceUrl(experience.getReferenceUrl())
                .technologies(experience.getTechnologies())
                .build();
    }
}
