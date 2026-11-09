package com.careercopilot.resume_service.controller;

import com.careercopilot.resume_service.dto.ResumeResponseDto;
import com.careercopilot.resume_service.exceptions.GlobalExceptionHandler;
import com.careercopilot.resume_service.exceptions.ResourceNotFoundException;
import com.careercopilot.resume_service.service.Interface.ResumeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ResumeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ResumeService resumeService;

    @InjectMocks
    private ResumeController resumeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(resumeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("POST /api/v1/resumes/upload - should return 201 Created")
    void testUploadResume() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "sample_resume.pdf",
                "application/pdf",
                "Sample PDF Content".getBytes()
        );

        ResumeResponseDto responseDto = ResumeResponseDto.builder()
                .id(1L)
                .userId(10L)
                .fileName("sample_resume.pdf")
                .fileType("application/pdf")
                .fileSize(1024L)
                .parsedContent("Extracted Content")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(resumeService.uploadResume(eq(10L), any())).thenReturn(responseDto);

        mockMvc.perform(multipart("/api/v1/resumes/upload")
                        .file(file)
                        .param("userId", "10")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Resume uploaded and parsed successfully."))
                .andExpect(jsonPath("$.resume.id").value(1L))
                .andExpect(jsonPath("$.resume.userId").value(10L))
                .andExpect(jsonPath("$.resume.fileName").value("sample_resume.pdf"));
    }

    @Test
    @DisplayName("GET /api/v1/resumes/user/{userId} - should return 200 OK")
    void testGetResumesByUserId() throws Exception {
        ResumeResponseDto dto = ResumeResponseDto.builder()
                .id(1L)
                .userId(10L)
                .fileName("resume.pdf")
                .build();

        when(resumeService.getResumesByUserId(10L)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/v1/resumes/user/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(10L));
    }

    @Test
    @DisplayName("GET /api/v1/resumes/{id} - should return 200 OK when found")
    void testGetResumeById_Found() throws Exception {
        ResumeResponseDto dto = ResumeResponseDto.builder()
                .id(1L)
                .userId(10L)
                .fileName("resume.pdf")
                .build();

        when(resumeService.getResumeById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/resumes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.fileName").value("resume.pdf"));
    }

    @Test
    @DisplayName("GET /api/v1/resumes/{id} - should return 404 NOT FOUND when missing")
    void testGetResumeById_NotFound() throws Exception {
        when(resumeService.getResumeById(99L)).thenThrow(new ResourceNotFoundException("Resume not found with id: 99"));

        mockMvc.perform(get("/api/v1/resumes/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Resume not found with id: 99"));
    }

    @Test
    @DisplayName("DELETE /api/v1/resumes/{id} - should return 204 No Content")
    void testDeleteResume() throws Exception {
        doNothing().when(resumeService).deleteResume(1L);

        mockMvc.perform(delete("/api/v1/resumes/1"))
                .andExpect(status().isNoContent());

        verify(resumeService, times(1)).deleteResume(1L);
    }
}
