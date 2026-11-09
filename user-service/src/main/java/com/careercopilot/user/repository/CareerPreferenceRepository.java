package com.careercopilot.user.repository;

import com.careercopilot.user.entity.careerPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareerPreferenceRepository extends JpaRepository<careerPreference, Long> {
    Optional<careerPreference> findByUserId(Long userId);
}
