package com.careercopilot.user.repository;

import com.careercopilot.user.entity.careerPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerPreferenceRepository extends JpaRepository<careerPreference, Long> {
}
