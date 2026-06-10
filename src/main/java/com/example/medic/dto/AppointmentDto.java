package com.example.medic.dto;

import com.example.medic.enums.StatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AppointmentDto {

    private Long id;

    private String patientName;

    private String doctorName;

    private LocalDate appointmentDate;

    private String timeSlot;

    private String symptomDescription;

    private StatusEnum status;
}