package com.careercopilot.jobservice.dto;

import lombok.Data;

import java.util.List;

// This will take data from greenhouse api
@Data
public class GreenhouseResponseDTO {

    private List<GreenhouseJobDTO> jobs;

}