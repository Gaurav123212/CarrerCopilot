package com.careercopilot.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "preferred_locations")
@Getter
@Setter
@NoArgsConstructor
public class preferredLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locationName;

    private String country;

    @ManyToOne
    @JoinColumn(name = "career_preference_id", nullable = false)
    private careerPreference careerPreference;
}