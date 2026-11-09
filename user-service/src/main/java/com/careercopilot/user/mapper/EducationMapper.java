package com.careercopilot.user.mapper;

import com.careercopilot.user.dto.EducationRequestDto;
import com.careercopilot.user.dto.EducationResponseDto;
import com.careercopilot.user.entity.Education;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {

    public Education requestDtoToEducationEntity(EducationRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Education education = new Education();
        education.setInstitutionName(dto.getInstitutionName());
        education.setDegree(dto.getDegree());
        education.setFieldOfStudy(dto.getFieldOfStudy());
        education.setStartDate(dto.getStartDate());
        education.setEndDate(dto.getEndDate());
        education.setGrade(dto.getGrade());
        if (dto.getIsCurrent() != null) {
            education.setCurrent(dto.getIsCurrent());
        }
        return education;
    }

    public EducationResponseDto educationEntityToEducationResponseDto(Education education) {
        if (education == null) {
            return null;
        }

        return EducationResponseDto.builder()
                .id(education.getId())
                .institutionName(education.getInstitutionName())
                .degree(education.getDegree())
                .fieldOfStudy(education.getFieldOfStudy())
                .startDate(education.getStartDate())
                .endDate(education.getEndDate())
                .grade(education.getGrade())
                .build();
    }
}
