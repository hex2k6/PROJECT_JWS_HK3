package com.example.medic.service.impl;

import com.example.medic.dto.AppointmentDto;
import com.example.medic.dto.CreateAppointmentRequest;
import com.example.medic.entity.Appointment;
import com.example.medic.entity.User;
import com.example.medic.enums.RoleEnum;
import com.example.medic.enums.StatusEnum;
import com.example.medic.repository.AppointmentRepository;
import com.example.medic.repository.UserRepository;
import com.example.medic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl
        implements AppointmentService {

    private final AppointmentRepository
            appointmentRepository;

    private final UserRepository
            userRepository;

    @Override
    public AppointmentDto createAppointment(
            String patientEmail,
            CreateAppointmentRequest request
    ) {

        User patient =
                userRepository
                        .findByEmail(patientEmail)
                        .orElseThrow();

        User doctor =
                userRepository
                        .findById(
                                request.getDoctorId()
                        )
                        .orElseThrow();

        if (doctor.getRole()
                != RoleEnum.DOCTOR) {

            throw new RuntimeException(
                    "Invalid doctor"
            );
        }

        boolean existed =
                appointmentRepository
                        .existsByDoctorAndAppointmentDateAndTimeSlot(
                                doctor,
                                request.getAppointmentDate(),
                                request.getTimeSlot()
                        );

        if (existed) {

            throw new RuntimeException(
                    "Doctor already booked"
            );
        }

        Appointment appointment =
                Appointment.builder()
                        .patient(patient)
                        .doctor(doctor)
                        .appointmentDate(
                                request
                                        .getAppointmentDate()
                        )
                        .timeSlot(
                                request.getTimeSlot()
                        )
                        .symptomDescription(
                                request
                                        .getSymptomDescription()
                        )
                        .status(
                                StatusEnum.PENDING
                        )
                        .createdAt(
                                LocalDateTime.now()
                        )
                        .build();

        appointmentRepository
                .save(appointment);

        return AppointmentDto
                .builder()
                .id(appointment.getId())
                .patientName(
                        patient.getUsername()
                )
                .doctorName(
                        doctor.getUsername()
                )
                .appointmentDate(
                        appointment
                                .getAppointmentDate()
                )
                .timeSlot(
                        appointment.getTimeSlot()
                )
                .symptomDescription(
                        appointment
                                .getSymptomDescription()
                )
                .status(
                        appointment.getStatus()
                )
                .build();
    }
}