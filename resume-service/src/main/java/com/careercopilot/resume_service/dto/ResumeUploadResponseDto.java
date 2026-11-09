package com.careercopilot.resume_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeUploadResponseDto {
    private String message;
    private ResumeResponseDto resume;
}
