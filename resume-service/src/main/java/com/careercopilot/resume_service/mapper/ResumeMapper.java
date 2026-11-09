package com.careercopilot.resume_service.mapper;

import com.careercopilot.resume_service.dto.ResumeResponseDto;
import com.careercopilot.resume_service.entity.Resume;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResumeMapper {

    public ResumeResponseDto toResponseDto(Resume resume) {
        if (resume == null) {
            return null;
        }

        return ResumeResponseDto.builder()
                .id(resume.getId())
                .userId(resume.getUserId())
                .fileName(resume.getFileName())
                .fileType(resume.getFileType())
                .fileSize(resume.getFileSize())
                .parsedContent(resume.getParsedContent())
                .createdAt(resume.getCreatedAt())
                .updatedAt(resume.getUpdatedAt())
                .build();
    }

    public List<ResumeResponseDto> toResponseDtoList(List<Resume> resumes) {
        if (resumes == null) {
            return Collections.emptyList();
        }

        return resumes.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
