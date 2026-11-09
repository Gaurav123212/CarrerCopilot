package com.careercopilot.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "resumes")
@Getter
@Setter
@NoArgsConstructor
public class resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resumeName;

    private String fileName;

    private String fileUrl;

    private String fileType;

    private Long fileSize;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
