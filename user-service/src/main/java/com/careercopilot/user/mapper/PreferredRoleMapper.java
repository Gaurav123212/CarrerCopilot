package com.careercopilot.user.mapper;

import com.careercopilot.user.dto.PreferredRoleRequestDto;
import com.careercopilot.user.dto.PreferredRoleResponseDto;
import com.careercopilot.user.entity.preferredRole;
import org.springframework.stereotype.Component;

@Component
public class PreferredRoleMapper {

    public preferredRole requestDtoToPreferredRoleEntity(PreferredRoleRequestDto dto) {
        if (dto == null) {
            return null;
        }

        preferredRole role = new preferredRole();
        role.setRoleName(dto.getRoleName());
        return role;
    }

    public PreferredRoleResponseDto preferredRoleEntityToPreferredRoleResponseDto(preferredRole preferredRole) {
        if (preferredRole == null) {
            return null;
        }

        return PreferredRoleResponseDto.builder()
                .id(preferredRole.getId())
                .roleName(preferredRole.getRoleName())
                .build();
    }
}
