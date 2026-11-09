package com.careercopilot.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeResponseDto {

    private Long id;
    private String resumeName;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private Long fileSize;
}
