package com.careercopilot.resume_service.mapper;

import com.careercopilot.resume_service.dto.ResumeResponseDto;
import com.careercopilot.resume_service.entity.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResumeMapperTest {

    private ResumeMapper resumeMapper;

    @BeforeEach
    void setUp() {
        resumeMapper = new ResumeMapper();
    }

    @Test
    @DisplayName("Should convert Resume entity to ResumeResponseDto")
    void testToResponseDto() {
        LocalDateTime now = LocalDateTime.now();
        Resume resume = Resume.builder()
                .id(1L)
                .userId(101L)
                .fileName("john_doe_resume.pdf")
                .fileType("application/pdf")
                .filePath("uploads/resumes/12345_101.pdf")
                .fileSize(2048L)
                .parsedContent("Extracted Resume Content")
                .createdAt(now)
                .updatedAt(now)
                .build();

        ResumeResponseDto dto = resumeMapper.toResponseDto(resume);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals(101L, dto.getUserId());
        assertEquals("john_doe_resume.pdf", dto.getFileName());
        assertEquals("application/pdf", dto.getFileType());
        assertEquals(2048L, dto.getFileSize());
        assertEquals("Extracted Resume Content", dto.getParsedContent());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    @DisplayName("Should return null when mapping null Resume entity")
    void testToResponseDto_Null() {
        assertNull(resumeMapper.toResponseDto(null));
    }

    @Test
    @DisplayName("Should convert list of Resume entities to ResumeResponseDto list")
    void testToResponseDtoList() {
        Resume resume1 = Resume.builder().id(1L).userId(101L).fileName("r1.pdf").build();
        Resume resume2 = Resume.builder().id(2L).userId(101L).fileName("r2.pdf").build();

        List<ResumeResponseDto> dtoList = resumeMapper.toResponseDtoList(List.of(resume1, resume2));

        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals("r1.pdf", dtoList.get(0).getFileName());
        assertEquals("r2.pdf", dtoList.get(1).getFileName());
    }
}
