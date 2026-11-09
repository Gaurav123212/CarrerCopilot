package com.careercopilot.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceResponseDto {

    private Long id;
    private String title;
    private String experienceType;
    private String organizationName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isCurrent;
    private String location;
    private String referenceUrl;
    private List<String> technologies;
}