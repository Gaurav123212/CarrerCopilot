package com.careercopilot.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "career_preferences")
@Getter
@Setter
@NoArgsConstructor
public class careerPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String preferredWorkMode;

    private Double expectedSalary;

    private String currency;

    private Boolean willingToRelocate;

    @OneToMany(mappedBy = "careerPreference")
    private List<preferredRole> preferredRoles = new ArrayList<>();

    @OneToMany(mappedBy = "careerPreference")
    private List<preferredLocation> preferredLocations = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}