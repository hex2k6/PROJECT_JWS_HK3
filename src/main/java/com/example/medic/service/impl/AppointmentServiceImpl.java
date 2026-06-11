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
import java.util.List;

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
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Patient not found"
                                )
                        );

        User doctor =
                userRepository
                        .findById(
                                request.getDoctorId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Doctor not found"
                                )
                        );

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
                                request.getAppointmentDate()
                        )
                        .timeSlot(
                                request.getTimeSlot()
                        )
                        .symptomDescription(
                                request.getSymptomDescription()
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

        return mapToDto(
                appointment
        );
    }

    @Override
    public List<AppointmentDto>
    getMyAppointments(
            String email
    ) {

        User patient =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Patient not found"
                                )
                        );

        return appointmentRepository
                .findByPatient(
                        patient
                )
                .stream()
                .map(
                        this::mapToDto
                )
                .toList();
    }

    @Override
    public AppointmentDto
    updateStatus(

            Long appointmentId,

            StatusEnum status
    ) {

        Appointment appointment =
                appointmentRepository
                        .findById(
                                appointmentId
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Appointment not found"
                                )
                        );

        appointment.setStatus(
                status
        );

        appointmentRepository
                .save(
                        appointment
                );

        return mapToDto(
                appointment
        );
    }

    private AppointmentDto
    mapToDto(
            Appointment appointment
    ) {

        return AppointmentDto
                .builder()
                .id(
                        appointment.getId()
                )
                .patientName(
                        appointment
                                .getPatient()
                                .getUsername()
                )
                .doctorName(
                        appointment
                                .getDoctor()
                                .getUsername()
                )
                .appointmentDate(
                        appointment
                                .getAppointmentDate()
                )
                .timeSlot(
                        appointment
                                .getTimeSlot()
                )
                .symptomDescription(
                        appointment
                                .getSymptomDescription()
                )
                .status(
                        appointment
                                .getStatus()
                )
                .build();
    }
    
}