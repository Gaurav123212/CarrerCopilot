package com.careercopilot.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "preferred_roles")
@Getter
@Setter
@NoArgsConstructor
public class preferredRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    @ManyToOne
    @JoinColumn(name = "career_preference_id", nullable = false)
    private careerPreference careerPreference;
}