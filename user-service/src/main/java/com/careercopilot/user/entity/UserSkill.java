package com.careercopilot.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_skills")
@Getter
@Setter
@NoArgsConstructor
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;

    private String proficiencyLevel;

    private Double yearsOfExperience;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Bringing user into this matlab ye skill kaunse user ki hai ye vo user hai
}
