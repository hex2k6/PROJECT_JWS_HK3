package com.example.medic.entity;

import com.example.medic.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Long id;

    private LocalDate appointmentDate;

    private String timeSlot;

    private String symptomDescription;

    @Enumerated(
            EnumType.STRING
    )
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(
            name = "patient_id"
    )
    private User patient;

    @ManyToOne
    @JoinColumn(
            name = "doctor_id"
    )
    private User doctor;

    private LocalDateTime createdAt;
}