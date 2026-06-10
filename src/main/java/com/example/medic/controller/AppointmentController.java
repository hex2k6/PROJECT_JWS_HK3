package com.example.medic.controller;

import com.example.medic.dto.*;
import com.example.medic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patient/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService
            appointmentService;

    @PostMapping
    public ApiResponse<AppointmentDto>
    createAppointment(

            @RequestBody
            CreateAppointmentRequest request,

            Authentication authentication
    ) {

        return ApiResponse
                .<AppointmentDto>builder()
                .success(true)
                .message(
                        "Appointment created"
                )
                .data(
                        appointmentService
                                .createAppointment(
                                        authentication
                                                .getName(),
                                        request
                                )
                )
                .build();
    }
}