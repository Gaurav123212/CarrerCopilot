package com.careercopilot.user.repository;

import com.careercopilot.user.entity.preferredLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferredLocationRepository extends JpaRepository<preferredLocation, Long> {
}
