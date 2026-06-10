package com.example.medic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis;

    private String prescription;

    private String fileUrl;

    private LocalDateTime createdAt;

    @ManyToOne
    private User patient;

    @ManyToOne
    private User doctor;

    @OneToOne
    private Appointment appointment;
}