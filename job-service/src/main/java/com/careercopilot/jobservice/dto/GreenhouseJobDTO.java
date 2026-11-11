package com.careercopilot.jobservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GreenhouseJobDTO {

    private Long id;

    @JsonProperty("internal_job_id")
    private Long internalJobId;

    private String title;

    @JsonProperty("company_name")
    private String companyName;

    private String location;

    private String department;

    @JsonProperty("requisition_id")
    private String requisitionId;

    private String education;

    private String language;

    private String content;

    @JsonProperty("absolute_url")
    private String officialJobUrl;

    @JsonProperty("first_published")
    private OffsetDateTime postedDate;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;

    @JsonProperty("application_deadline")
    private OffsetDateTime applicationDeadline;
}