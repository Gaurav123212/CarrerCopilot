package com.careercopilot.resume_service.service.impl;

import com.careercopilot.resume_service.dto.ResumeResponseDto;
import com.careercopilot.resume_service.entity.Resume;
import com.careercopilot.resume_service.exceptions.ResourceNotFoundException;
import com.careercopilot.resume_service.mapper.ResumeMapper;
import com.careercopilot.resume_service.repository.ResumeRepository;
import com.careercopilot.resume_service.service.Interface.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final ResumeMapper resumeMapper;

    @Value("${file.upload-dir:uploads/resumes}")
    private String uploadDir;

    @Override
    @Transactional
    public ResumeResponseDto uploadResume(Long userId, MultipartFile file) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File must not be empty");
        }

        try {
            // Ensure target upload directory exists
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate timestamped unique filename: e.g. 1712345678_1.pdf
            String originalFilename = file.getOriginalFilename();
            String extension = ".pdf";
            if (originalFilename != null && originalFilename.lastIndexOf(".") != -1) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String storedFileName = System.currentTimeMillis() + "_" + userId + extension;
            Path targetLocation = uploadPath.resolve(storedFileName);

            // Save file to disk
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("Resume file stored successfully at: {}", targetLocation);

            // Extract raw text using Apache PDFBox 3.0.1
            String parsedContent = extractTextFromPdf(targetLocation.toFile());

            // Build entity
            Resume resume = Resume.builder()
                    .userId(userId)
                    .fileName(originalFilename != null ? originalFilename : storedFileName)
                    .fileType(file.getContentType() != null ? file.getContentType() : "application/pdf")
                    .filePath(targetLocation.toString())
                    .fileSize(file.getSize())
                    .parsedContent(parsedContent)
                    .build();

            // Save to MySQL DB
            Resume savedResume = resumeRepository.save(resume);
            log.info("Resume record persisted to database with id: {}", savedResume.getId());

            return resumeMapper.toResponseDto(savedResume);

        } catch (IOException e) {
            log.error("Failed to store or process resume file for user: {}", userId, e);
            throw new RuntimeException("Could not store or process resume file: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResumeResponseDto> getResumesByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        List<Resume> resumes = resumeRepository.findByUserId(userId);
        return resumeMapper.toResponseDtoList(resumes);
    }

    @Override
    @Transactional(readOnly = true)
    public ResumeResponseDto getResumeById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Resume ID must not be null");
        }
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));
        return resumeMapper.toResponseDto(resume);
    }

    @Override
    @Transactional
    public void deleteResume(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Resume ID must not be null");
        }
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));

        // Delete physical file from disk
        try {
            Path filePath = Paths.get(resume.getFilePath());
            Files.deleteIfExists(filePath);
            log.info("Deleted physical file at: {}", filePath);
        } catch (IOException e) {
            log.warn("Failed to delete physical file from disk at: {}. Proceeding with DB record deletion.", resume.getFilePath(), e);
        }

        // Delete DB record
        resumeRepository.delete(resume);
        log.info("Deleted resume record from database with id: {}", id);
    }

    private String extractTextFromPdf(File pdfFile) {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        } catch (IOException e) {
            log.warn("PDFBox text extraction failed or file is not a valid PDF: {}", e.getMessage());
            return "";
        }
    }
}
