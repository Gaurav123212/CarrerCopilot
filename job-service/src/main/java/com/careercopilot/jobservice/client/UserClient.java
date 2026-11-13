package com.careercopilot.jobservice.client;

import com.careercopilot.jobservice.dto.UserProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {
    @GetMapping("/api/v1/users/{userId}/profile")
    UserProfileResponseDto getUserProfile(@PathVariable("userId") Long userId);
}