
package com.careercopilot.jobservice.service;

import com.careercopilot.jobservice.client.UserClient;
import com.careercopilot.jobservice.dto.JobResponseDTO;
import com.careercopilot.jobservice.dto.UserProfileResponseDto;
import com.careercopilot.jobservice.entity.JobEntity;
import com.careercopilot.jobservice.exception.ResourceNotFoundException;
import com.careercopilot.jobservice.mapper.JobResponseMapper;
import com.careercopilot.jobservice.repository.JobRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final JobResponseMapper jobResponseMapper;
    private final UserClient userClient;

    public Page<JobResponseDTO> getRecommendedJobsForUser(Long userId, Pageable pageable) {
        // 1. Call user-service through OpenFeign + Eureka
        UserProfileResponseDto profile = userClient.getUserProfile(userId);
        // 2. Extract preferences (e.g. target role and location)
        String targetRole = null;
        if (profile.getCareerPreference() != null &&
                !profile.getCareerPreference().getPreferredRoles().isEmpty()) {
            targetRole = profile.getCareerPreference().getPreferredRoles().get(0).getRoleName();
        }
        String targetLocation = null;
        if (profile.getCareerPreference() != null &&
                !profile.getCareerPreference().getPreferredLocations().isEmpty()) {
            targetLocation = profile.getCareerPreference().getPreferredLocations().get(0).getLocationName();
        }
        // 3. Query your job database using user's real preferences!
        return jobRepository.searchActiveJobs(targetRole, targetLocation, pageable)
                .map(jobResponseMapper::toDTO);
    }

    // Get all active jobs
    public Page<JobResponseDTO> getAllActiveJobs(Pageable pageable) {

        return jobRepository.findByActiveTrue(pageable)
                .map(jobResponseMapper::toDTO);
    }

    // Get job by UUID

    public JobResponseDTO getJobById(UUID id) {

        JobEntity job = jobRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job not found with id: " + id
                        )
                );

        return jobResponseMapper.toDTO(job);
    }


    // Search jobs
    public Page<JobResponseDTO> searchJobs(
            String search,
            String location,
            Pageable pageable
    ) {

        return jobRepository.searchActiveJobs(
                        search,
                        location,
                        pageable
                )
                .map(jobResponseMapper::toDTO);
    }

    // Get jobs by company
    public Page<JobResponseDTO> getJobsByCompany(
            String companyName,
            Pageable pageable
    ) {

        return jobRepository
                .findByActiveTrueAndCompanyNameContainingIgnoreCase(
                        companyName,
                        pageable
                )
                .map(jobResponseMapper::toDTO);
    }
}