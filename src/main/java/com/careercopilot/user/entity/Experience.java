package com.careercopilot.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String experienceType;
    private String organizationName;
    private String description;
    private String technologies;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCurrent;
    private String location;
    private String referenceUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}