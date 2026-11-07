package com.careercopilot.user.mapper;

import com.careercopilot.user.dto.CareerPreferenceRequestDto;
import com.careercopilot.user.dto.CareerPreferenceResponseDto;
import com.careercopilot.user.entity.careerPreference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CareerPreferenceMapper {

    private final PreferredRoleMapper preferredRoleMapper;
    private final PreferredLocationMapper preferredLocationMapper;

    public careerPreference requestDtoToCareerPreferenceEntity(CareerPreferenceRequestDto dto) {
        if (dto == null) {
            return null;
        }

        careerPreference preference = new careerPreference();
        preference.setPreferredWorkMode(dto.getPreferredWorkMode());
        preference.setExpectedSalary(dto.getExpectedSalary());
        preference.setCurrency(dto.getCurrency());
        preference.setWillingToRelocate(dto.getWillingToRelocate());

        if (dto.getPreferredRoles() != null) {
            preference.setPreferredRoles(dto.getPreferredRoles().stream()
                    .map(preferredRoleMapper::requestDtoToPreferredRoleEntity)
                    .collect(Collectors.toList()));
        }

        if (dto.getPreferredLocations() != null) {
            preference.setPreferredLocations(dto.getPreferredLocations().stream()
                    .map(preferredLocationMapper::requestDtoToPreferredLocationEntity)
                    .collect(Collectors.toList()));
        }

        return preference;
    }

    public CareerPreferenceResponseDto careerPreferenceEntityToCareerPreferenceResponseDto(careerPreference careerPreference) {
        if (careerPreference == null) {
            return null;
        }

        return CareerPreferenceResponseDto.builder()
                .id(careerPreference.getId())
                .preferredWorkMode(careerPreference.getPreferredWorkMode())
                .expectedSalary(careerPreference.getExpectedSalary())
                .currency(careerPreference.getCurrency())
                .willingToRelocate(careerPreference.getWillingToRelocate())
                .preferredRoles(careerPreference.getPreferredRoles() != null
                        ? careerPreference.getPreferredRoles().stream()
                                .map(preferredRoleMapper::preferredRoleEntityToPreferredRoleResponseDto)
                                .collect(Collectors.toList())
                        : null)
                .preferredLocations(careerPreference.getPreferredLocations() != null
                        ? careerPreference.getPreferredLocations().stream()
                                .map(preferredLocationMapper::preferredLocationEntityToPreferredLocationResponseDto)
                                .collect(Collectors.toList())
                        : null)
                .build();
    }
}
