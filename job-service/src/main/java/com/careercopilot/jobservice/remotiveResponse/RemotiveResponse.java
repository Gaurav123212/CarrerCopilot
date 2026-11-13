package com.careercopilot.jobservice.remotiveResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class RemotiveResponse {

    @JsonProperty("00-warning")
    private String warning;

    @JsonProperty("0-legal-notice")
    private String legalNotice;

    @JsonProperty("job-count")
    private int jobCount;

    @JsonProperty("total-job-count")
    private int totalJobCount;

    private List<Job> jobs;

    @Data
    @Getter
    @Setter
    public static class Job {

        private long id;
        private String url;
        private String title;

        @JsonProperty("company_name")
        private String companyName;

        @JsonProperty("company_logo")
        private String companyLogo;

        private String category;
        private List<String> tags;

        @JsonProperty("job_type")
        private String jobType;

        @JsonProperty("publication_date")
        private String publicationDate;

        @JsonProperty("candidate_required_location")
        private String candidateRequiredLocation;

        private String salary;
        private String description;

        @JsonProperty("company_logo_url")
        private String companyLogoUrl;
    }
}