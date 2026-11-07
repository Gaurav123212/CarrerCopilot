package com.careercopilot.user.mapper;

import com.careercopilot.user.dto.ResumeRequestDto;
import com.careercopilot.user.dto.ResumeResponseDto;
import com.careercopilot.user.entity.resume;
import org.springframework.stereotype.Component;

@Component
public class ResumeMapper {

    public resume requestDtoToResumeEntity(ResumeRequestDto dto) {
        if (dto == null) {
            return null;
        }

        resume resumeEntity = new resume();
        resumeEntity.setResumeName(dto.getResumeName());
        resumeEntity.setFileName(dto.getFileName());
        resumeEntity.setFileUrl(dto.getFileUrl());
        resumeEntity.setFileType(dto.getFileType());
        resumeEntity.setFileSize(dto.getFileSize());
        return resumeEntity;
    }

    public ResumeResponseDto resumeEntityToResumeResponseDto(resume resume) {
        if (resume == null) {
            return null;
        }

        return ResumeResponseDto.builder()
                .id(resume.getId())
                .resumeName(resume.getResumeName())
                .fileName(resume.getFileName())
                .fileUrl(resume.getFileUrl())
                .fileType(resume.getFileType())
                .fileSize(resume.getFileSize())
                .build();
    }
}
