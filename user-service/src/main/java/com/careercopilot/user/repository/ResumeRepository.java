package com.careercopilot.user.repository;

import com.careercopilot.user.entity.resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<resume, Long> {
    List<resume> findByUserId(Long userId);
}
