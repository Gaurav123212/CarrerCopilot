package com.careercopilot.user.mapper;

import com.careercopilot.user.dto.PreferredLocationRequestDto;
import com.careercopilot.user.dto.PreferredLocationResponseDto;
import com.careercopilot.user.entity.preferredLocation;
import org.springframework.stereotype.Component;

@Component
public class PreferredLocationMapper {

    public preferredLocation requestDtoToPreferredLocationEntity(PreferredLocationRequestDto dto) {
        if (dto == null) {
            return null;
        }

        preferredLocation location = new preferredLocation();
        location.setLocationName(dto.getLocationName());
        location.setCountry(dto.getCountry());
        return location;
    }

    public PreferredLocationResponseDto preferredLocationEntityToPreferredLocationResponseDto(preferredLocation preferredLocation) {
        if (preferredLocation == null) {
            return null;
        }

        return PreferredLocationResponseDto.builder()
                .id(preferredLocation.getId())
                .locationName(preferredLocation.getLocationName())
                .country(preferredLocation.getCountry())
                .build();
    }
}
