
package com.careercopilot.jobservice.repository;

import com.careercopilot.jobservice.entity.JobEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    Optional<JobEntity> findByExternalId(Long externalId);

    boolean existsByExternalId(Long externalId);

    @Query("""
    SELECT j FROM JobEntity j
    WHERE j.active = true
      AND (:search IS NULL OR
           LOWER(j.title) LIKE LOWER(CONCAT('%', :search, '%'))
           OR LOWER(j.companyName) LIKE LOWER(CONCAT('%', :search, '%')))
      AND (:location IS NULL OR
           LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))
    ORDER BY j.postedDate DESC
    """)
    Page<JobEntity> searchActiveJobs(@Param("search") String search, @Param("location") String location, Pageable pageable);

    @Query("""
        SELECT j FROM JobEntity j
        WHERE j.active = true
          AND j.lastSeenAt < :cutoffDate
    """)
    List<JobEntity> findStaleJobs(@Param("cutoffDate") OffsetDateTime cutoffDate);

    @Modifying
    @Query("""
    UPDATE JobEntity j
    SET j.active = false
    WHERE j.active = true
      AND j.lastSeenAt < :cutoffDate
""")
    int deactivateStaleJobs(
            @Param("cutoffDate") OffsetDateTime cutoffDate
    );

    Page<JobEntity> findByActiveTrue(Pageable pageable);

    Optional<JobEntity> findByIdAndActiveTrue(UUID id);

    Page<JobEntity> findByActiveTrueAndTitleContainingIgnoreCaseOrActiveTrueAndCompanyNameContainingIgnoreCase(
            String title, String companyName, Pageable pageable);

    Page<JobEntity> findByActiveTrueAndLocationContainingIgnoreCase(
            String location, Pageable pageable);

    Page<JobEntity> findByActiveTrueAndCompanyNameContainingIgnoreCase(
            String companyName, Pageable pageable);
}