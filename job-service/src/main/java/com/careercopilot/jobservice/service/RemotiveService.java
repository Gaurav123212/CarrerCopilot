package com.careercopilot.jobservice.service;

import com.careercopilot.jobservice.entity.JobEntity;
import com.careercopilot.jobservice.mapper.RemotiveJobMapper;
import com.careercopilot.jobservice.remotiveResponse.RemotiveResponse;
import com.careercopilot.jobservice.repository.JobRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RemotiveService {
    private static final String API_URL = "https://remotive.com/api/remote-jobs?category=software-dev&limit=50";

    private final RestTemplate restTemplate;
    private final JobRepository jobRepository;
    private final RemotiveJobMapper remotiveJobMapper;

    public RemotiveResponse fetchJobs() {
        ResponseEntity<RemotiveResponse> response = restTemplate.exchange(API_URL, HttpMethod.GET, null, RemotiveResponse.class);
        return response.getBody();
    }


    @Transactional
    public void fetchAndSaveJobs() {

        // Step 1: Fetch jobs from Remotive API
        RemotiveResponse response = fetchJobs();

        // Step 2: Iterate through every job
        for (RemotiveResponse.Job job : response.getJobs()) {

            // Step 3: Check whether job already exists
            Optional<JobEntity> existingJob =
                    jobRepository.findByExternalId(job.getId());

            JobEntity entity;

            // Step 4: Update existing job or create new job
            if (existingJob.isPresent()) {

                entity = existingJob.get();

                remotiveJobMapper.updateEntity(entity, job);

            } else {

                entity = remotiveJobMapper.toEntity(job);

                entity.setCreatedAt(OffsetDateTime.now());
            }

            // Step 5: Update synchronization timestamps
            entity.setLastSeenAt(OffsetDateTime.now());
            entity.setModifiedAt(OffsetDateTime.now());

            // Step 6: Save to database
            jobRepository.save(entity);
            deactivateExpiredJobs();
        }
    }

    public void deactivateExpiredJobs() {

        OffsetDateTime cutoffDate =
                OffsetDateTime.now().minusDays(7);

        int updatedCount =
                jobRepository.deactivateStaleJobs(cutoffDate);

        System.out.println(
                "Deactivated jobs: " + updatedCount
        );
    }
}