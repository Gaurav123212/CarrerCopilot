package com.careercopilot.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String profilePictureUrl;
}