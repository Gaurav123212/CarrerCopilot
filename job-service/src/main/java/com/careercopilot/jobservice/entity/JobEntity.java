
package com.careercopilot.jobservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "jobs",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_job_external_id",
                        columnNames = "external_id"
                )
        },
        indexes = {
                @Index(name = "idx_job_status", columnList = "active"),
                @Index(name = "idx_job_posted_date", columnList = "posted_date"),
                @Index(name = "idx_job_company", columnList = "company_name")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "external_id", nullable = false)
    private Long externalId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String companyName;

    private String location;

    private String jobType;

    private String category;

    private String salary;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String officialJobUrl;

    private OffsetDateTime postedDate;

    private OffsetDateTime updatedAt;

    @Builder.Default
    private boolean active = true;

    private OffsetDateTime lastSeenAt;

    private OffsetDateTime createdAt;

    private OffsetDateTime modifiedAt;
}