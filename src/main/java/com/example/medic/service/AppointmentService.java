package com.example.medic.service;

import com.example.medic.dto.AppointmentDto;
import com.example.medic.dto.CreateAppointmentRequest;

public interface AppointmentService {

    AppointmentDto createAppointment(
            String patientEmail,
            CreateAppointmentRequest request
    );
}