package com.careercopilot.jobservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "jobs", uniqueConstraints = {@UniqueConstraint(columnNames = {"source", "external_id"})},
        indexes = {
                @Index(name = "idx_job_status", columnList = "active"),
                @Index(name = "idx_job_posted_date", columnList = "posted_date"),
                @Index(name = "idx_job_company", columnList = "company_name")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class job {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "external_id", nullable = false)
    private Long externalId;

    private Long internalJobId;

    @Column(nullable = false)
    private String source;

    // Basic job information
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String companyName;

    private String location;

    private String department;

    private String requisitionId;

    private String education;

    private String language;

    // Job description
    @Column(columnDefinition = "TEXT")
    private String description;

    // Application information
    @Column(columnDefinition = "TEXT")
    private String officialJobUrl;

    private OffsetDateTime applicationDeadline;

    // Dates
    private OffsetDateTime postedDate;

    private OffsetDateTime updatedAt;

    // Job status
    @Builder.Default
    private boolean active = true;

    private OffsetDateTime lastSeenAt;

    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}