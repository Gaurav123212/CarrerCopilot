package com.careercopilot.jobservice.mapper;

import com.careercopilot.jobservice.entity.JobEntity;
import com.careercopilot.jobservice.remotiveResponse.RemotiveResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class RemotiveJobMapper {

    public JobEntity toEntity(RemotiveResponse.Job job) {

        return JobEntity.builder()
                .externalId(job.getId())
                .title(job.getTitle())
                .companyName(job.getCompanyName())
                .location(job.getCandidateRequiredLocation())
                .jobType(job.getJobType())
                .category(job.getCategory())
                .salary(job.getSalary())
                .description(job.getDescription())
                .officialJobUrl(job.getUrl())
                .postedDate(LocalDateTime.parse(job.getPublicationDate())
                        .atOffset(ZoneOffset.UTC))
                .source("REMOTIVE")
                .build();
    }


    public void updateEntity(JobEntity entity, RemotiveResponse.Job job) {

        entity.setTitle(job.getTitle());
        entity.setCompanyName(job.getCompanyName());
        entity.setLocation(job.getCandidateRequiredLocation());
        entity.setJobType(job.getJobType());
        entity.setCategory(job.getCategory());
        entity.setSalary(job.getSalary());
        entity.setDescription(job.getDescription());
        entity.setOfficialJobUrl(job.getUrl());

        entity.setPostedDate(
                LocalDateTime.parse(job.getPublicationDate())
                        .atOffset(ZoneOffset.UTC)
        );
    }
}