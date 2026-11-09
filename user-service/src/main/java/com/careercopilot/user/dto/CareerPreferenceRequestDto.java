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
public class CareerPreferenceRequestDto {
    private String preferredWorkMode;
    private Double expectedSalary;
    private String currency;
    private Boolean willingToRelocate;
    private List<PreferredRoleRequestDto> preferredRoles;
    private List<PreferredLocationRequestDto> preferredLocations;
}
