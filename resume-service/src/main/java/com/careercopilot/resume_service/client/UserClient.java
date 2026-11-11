package com.careercopilot.resume_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 'name' matches the registered application name in Eureka
@FeignClient(name = "user-service")
public interface UserClient {

    // Maps directly to the REST endpoint exposed by user-service
    @GetMapping("/api/v1/users/{userId}")
    Object getUserById(@PathVariable("userId") Long userId);
}
