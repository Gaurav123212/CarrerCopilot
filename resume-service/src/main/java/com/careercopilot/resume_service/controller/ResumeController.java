package com.careercopilot.resume_service.controller;

import com.careercopilot.resume_service.dto.ResumeResponseDto;
import com.careercopilot.resume_service.dto.ResumeUploadResponseDto;
import com.careercopilot.resume_service.service.Interface.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resumes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeUploadResponseDto> uploadResume(
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file) {

        ResumeResponseDto resumeResponse = resumeService.uploadResume(userId, file);
        ResumeUploadResponseDto response = ResumeUploadResponseDto.builder()
                .message("Resume uploaded and parsed successfully.")
                .resume(resumeResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResumeResponseDto>> getResumesByUserId(@PathVariable("userId") Long userId) {
        List<ResumeResponseDto> resumes = resumeService.getResumesByUserId(userId);
        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeResponseDto> getResumeById(@PathVariable("id") Long id) {
        ResumeResponseDto resume = resumeService.getResumeById(id);
        return ResponseEntity.ok(resume);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable("id") Long id) {
        resumeService.deleteResume(id);
        return ResponseEntity.noContent().build();
    }
}
