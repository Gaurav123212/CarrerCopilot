package com.careercopilot.jobservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobResponseDTO {

    private UUID id;

    private String title;

    private String companyName;

    private String location;

    private String jobType;

    private String category;

    private String salary;

    private String description;

    private String officialJobUrl;

    private OffsetDateTime postedDate;
}