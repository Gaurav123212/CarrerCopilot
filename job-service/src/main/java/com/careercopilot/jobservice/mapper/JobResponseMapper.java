
package com.careercopilot.jobservice.mapper;

import com.careercopilot.jobservice.dto.JobResponseDTO;
import com.careercopilot.jobservice.entity.JobEntity;
import org.springframework.stereotype.Component;

@Component
public class JobResponseMapper {

    public JobResponseDTO toDTO(JobEntity job) {

        return new JobResponseDTO(
                job.getId(),
                job.getTitle(),
                job.getCompanyName(),
                job.getLocation(),
                job.getJobType(),
                job.getCategory(),
                job.getSalary(),
                job.getDescription(),
                job.getOfficialJobUrl(),
                job.getPostedDate()
        );
    }
}