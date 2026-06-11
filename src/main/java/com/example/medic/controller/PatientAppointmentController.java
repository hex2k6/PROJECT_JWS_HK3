package com.example.medic.controller;

import com.example.medic.dto.*;
import com.example.medic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patient/appointments")
@RequiredArgsConstructor
public class PatientAppointmentController {

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
    @GetMapping("/my-history")
    public ApiResponse<List<AppointmentDto>>
    myHistory(

            Authentication authentication
    ) {

        return ApiResponse
                .<List<AppointmentDto>>
                        builder()
                .success(true)
                .message(
                        "Appointment history"
                )
                .data(
                        appointmentService
                                .getMyAppointments(
                                        authentication
                                                .getName()
                                )
                )
                .build();
    }
}