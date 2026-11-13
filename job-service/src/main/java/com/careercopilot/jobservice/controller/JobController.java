
package com.careercopilot.jobservice.controller;

import com.careercopilot.jobservice.dto.JobResponseDTO;
import com.careercopilot.jobservice.service.JobService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    // Get all active jobs
    @GetMapping
    public Page<JobResponseDTO> getAllJobs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "postedDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {

        Pageable pageable = createPageable(
                page, size, sortBy, direction
        );

        return jobService.getAllActiveJobs(pageable);
    }

    // Get job by UUID
    @GetMapping("/{id}")
    public JobResponseDTO getJobById(@PathVariable UUID id) {

        return jobService.getJobById(id);
    }

    // Search jobs
    @GetMapping("/search")
    public Page<JobResponseDTO> searchJobs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "postedDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String direction
    ) {

        Pageable pageable = createPageable(
                page, size, sortBy, direction
        );

        return jobService.searchJobs(search, location, pageable);
    }

    // Get jobs by company
    @GetMapping("/company/{companyName}")
    public Page<JobResponseDTO> getJobsByCompany(
            @PathVariable String companyName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        Pageable pageable = createPageable(
                page, size, "postedDate", "DESC"
        );

        return jobService.getJobsByCompany(companyName, pageable);
    }

    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<Page<JobResponseDTO>> getRecommendations(
            @PathVariable Long userId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<JobResponseDTO> recommendedJobs = jobService.getRecommendedJobsForUser(userId, pageable);
        return ResponseEntity.ok(recommendedJobs);
    }

    // Pagination and sorting helper
    private Pageable createPageable(
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Sort sort = Sort.by(
                Sort.Direction.fromString(direction),
                sortBy
        );

        return PageRequest.of(page, size, sort);
    }
}