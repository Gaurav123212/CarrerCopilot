package com.careercopilot.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<String> technologies = new ArrayList<>();    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCurrent;
    private String location;
    private String referenceUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}