package com.careercopilot.jobservice.scheduler;

import com.careercopilot.jobservice.service.RemotiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemotiveJobScheduler {

    private final RemotiveService remotiveService;

    @Scheduled(fixedRate = 5 * 60 * 60 * 1000, initialDelay = 0)
    public void fetchJobsPeriodically() {

        System.out.println("Starting scheduled Remotive job fetch...");

        remotiveService.fetchAndSaveJobs();

        System.out.println("Remotive job fetch completed.");
    }
}
