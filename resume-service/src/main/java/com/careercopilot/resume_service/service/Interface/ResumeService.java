package com.careercopilot.resume_service.service.Interface;

import com.careercopilot.resume_service.dto.ResumeResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResumeService {

    ResumeResponseDto uploadResume(Long userId, MultipartFile file);

    List<ResumeResponseDto> getResumesByUserId(Long userId);

    ResumeResponseDto getResumeById(Long id);

    void deleteResume(Long id);
}
