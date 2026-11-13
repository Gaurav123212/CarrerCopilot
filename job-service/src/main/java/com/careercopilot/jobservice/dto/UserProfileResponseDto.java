package com.careercopilot.jobservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserProfileResponseDto {
    private CareerPreferenceDto careerPreference;
    private List<UserSkillDto> skills;

    @Data
    public static class CareerPreferenceDto {
        private String preferredWorkMode;              // e.g. "REMOTE", "HYBRID"
        private List<PreferredRoleDto> preferredRoles;        // e.g. ["Java Developer", "Backend Engineer"]
        private List<PreferredLocationDto> preferredLocations;// e.g. ["Bangalore", "Remote"]
    }

    @Data
    public static class PreferredRoleDto {
        private String roleName;                       // e.g. "Software Engineer"
    }

    @Data
    public static class PreferredLocationDto {
        private String locationName;                   // e.g. "Remote" or "Bangalore"
    }

    @Data
    public static class UserSkillDto {
        private String skillName;                      // e.g. "Java", "Docker", "React"
    }
}