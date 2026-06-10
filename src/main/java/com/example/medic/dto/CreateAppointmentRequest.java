package com.example.medic.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateAppointmentRequest {

    private Long doctorId;

    private LocalDate appointmentDate;

    private String timeSlot;

    private String symptomDescription;
}