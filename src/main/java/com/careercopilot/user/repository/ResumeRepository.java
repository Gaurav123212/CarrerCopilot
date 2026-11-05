package com.careercopilot.user.repository;

import com.careercopilot.user.entity.resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<resume, Long> {
}
