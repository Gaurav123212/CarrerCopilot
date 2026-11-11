package com.careercopilot.resume_service.service;

import com.careercopilot.resume_service.dto.ResumeResponseDto;
import com.careercopilot.resume_service.entity.Resume;
import com.careercopilot.resume_service.exceptions.ResourceNotFoundException;
import com.careercopilot.resume_service.mapper.ResumeMapper;
import com.careercopilot.resume_service.repository.ResumeRepository;
import com.careercopilot.resume_service.service.impl.ResumeServiceImpl;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResumeServiceTest {

    @Mock
    private ResumeRepository resumeRepository;

    @Mock
    private com.careercopilot.resume_service.client.UserClient userClient;

    @Spy
    private ResumeMapper resumeMapper = new ResumeMapper();

    @InjectMocks
    private ResumeServiceImpl resumeService;

    @TempDir
    Path tempUploadDir;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(resumeService, "uploadDir", tempUploadDir.toString());
    }

    private byte[] createSamplePdfBytes(String text) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText(text);
                contentStream.endText();
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);
            return out.toByteArray();
        }
    }

    @Test
    @DisplayName("Should upload resume and extract text successfully")
    void testUploadResume_Success() throws IOException {
        byte[] pdfBytes = createSamplePdfBytes("Java Developer Resume");
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "resume.pdf",
                "application/pdf",
                pdfBytes
        );

        Resume savedResume = Resume.builder()
                .id(1L)
                .userId(100L)
                .fileName("resume.pdf")
                .fileType("application/pdf")
                .filePath(tempUploadDir.resolve("test.pdf").toString())
                .fileSize((long) pdfBytes.length)
                .parsedContent("Java Developer Resume")
                .build();

        when(userClient.getUserById(100L)).thenReturn(new Object());
        when(resumeRepository.save(any(Resume.class))).thenReturn(savedResume);

        ResumeResponseDto result = resumeService.uploadResume(100L, file);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100L, result.getUserId());
        assertEquals("resume.pdf", result.getFileName());
        verify(resumeRepository, times(1)).save(any(Resume.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when file is empty")
    void testUploadResume_EmptyFile() {
        MockMultipartFile emptyFile = new MockMultipartFile("file", "empty.pdf", "application/pdf", new byte[0]);

        assertThrows(IllegalArgumentException.class, () -> resumeService.uploadResume(100L, emptyFile));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when userId is null")
    void testUploadResume_NullUserId() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "resume.pdf", "application/pdf", createSamplePdfBytes("Test"));

        assertThrows(IllegalArgumentException.class, () -> resumeService.uploadResume(null, file));
    }

    @Test
    @DisplayName("Should get resumes by userId")
    void testGetResumesByUserId() {
        when(userClient.getUserById(100L)).thenReturn(new Object());
        Resume resume = Resume.builder().id(1L).userId(100L).fileName("r.pdf").build();
        when(resumeRepository.findByUserId(100L)).thenReturn(List.of(resume));

        List<ResumeResponseDto> result = resumeService.getResumesByUserId(100L);

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getUserId());
    }

    @Test
    @DisplayName("Should get resume by id successfully")
    void testGetResumeById_Success() {
        Resume resume = Resume.builder().id(1L).userId(100L).fileName("r.pdf").build();
        when(resumeRepository.findById(1L)).thenReturn(Optional.of(resume));

        ResumeResponseDto result = resumeService.getResumeById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when resume not found by id")
    void testGetResumeById_NotFound() {
        when(resumeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> resumeService.getResumeById(999L));
    }

    @Test
    @DisplayName("Should delete resume successfully")
    void testDeleteResume_Success() {
        Resume resume = Resume.builder().id(1L).userId(100L).filePath(tempUploadDir.resolve("dummy.pdf").toString()).build();
        when(resumeRepository.findById(1L)).thenReturn(Optional.of(resume));

        assertDoesNotThrow(() -> resumeService.deleteResume(1L));
        verify(resumeRepository, times(1)).delete(resume);
    }
}
