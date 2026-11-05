package com.careercopilot.user.repository;

import com.careercopilot.user.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
