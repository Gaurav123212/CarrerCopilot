package com.careercopilot.user.repository;

import com.careercopilot.user.entity.preferredRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferredRoleRepository extends JpaRepository<preferredRole, Long> {
}
