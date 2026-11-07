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
public class CareerPreferenceResponseDto {

    private Long id;
    private String preferredWorkMode;
    private Double expectedSalary;
    private String currency;
    private Boolean willingToRelocate;
    private List<PreferredRoleResponseDto> preferredRoles;

    private List<PreferredLocationResponseDto> preferredLocations;
}
