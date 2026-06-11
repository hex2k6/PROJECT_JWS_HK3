package com.example.medic.controller;

import com.example.medic.dto.ApiResponse;
import com.example.medic.dto.AppointmentDto;
import com.example.medic.enums.StatusEnum;
import com.example.medic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        "/api/v1/doctor/appointments"
)
@RequiredArgsConstructor
public class DoctorAppointmentController {

    private final AppointmentService
            appointmentService;

    @PutMapping("/{id}/status")
    public ApiResponse<AppointmentDto>
    updateStatus(

            @PathVariable
            Long id,

            @RequestParam
            StatusEnum status
    ) {

        return ApiResponse
                .<AppointmentDto>builder()
                .success(true)
                .message(
                        "Appointment updated"
                )
                .data(
                        appointmentService
                                .updateStatus(
                                        id,
                                        status
                                )
                )
                .build();
    }
}