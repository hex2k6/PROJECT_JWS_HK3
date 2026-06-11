package com.example.medic.service;

import com.example.medic.dto.AppointmentDto;
import com.example.medic.dto.CreateAppointmentRequest;
import com.example.medic.enums.StatusEnum;

import java.util.List;

public interface AppointmentService {

    AppointmentDto createAppointment(
            String patientEmail,
            CreateAppointmentRequest request
    );

    List<AppointmentDto>
    getMyAppointments(
            String email
    );

    AppointmentDto updateStatus(
            Long appointmentId,
            StatusEnum status
    );
}