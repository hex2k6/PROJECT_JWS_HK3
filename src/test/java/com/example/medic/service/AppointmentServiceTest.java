package com.example.medic.service;

import com.example.medic.dto.CreateAppointmentRequest;
import com.example.medic.entity.User;
import com.example.medic.enums.RoleEnum;
import com.example.medic.repository.AppointmentRepository;
import com.example.medic.repository.UserRepository;
import com.example.medic.service.impl.AppointmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    AppointmentRepository appointmentRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AppointmentServiceImpl service;

    @Test
    void createAppointment_success() {

        User patient =
                User.builder()
                        .email("p@gmail.com")
                        .build();

        User doctor =
                User.builder()
                        .id(1L)
                        .role(RoleEnum.DOCTOR)
                        .build();

        CreateAppointmentRequest request =
                new CreateAppointmentRequest();

        request.setDoctorId(1L);
        request.setAppointmentDate(
                LocalDate.now()
        );

        request.setTimeSlot(
                "08:00"
        );

        when(userRepository.findByEmail(
                anyString()
        )).thenReturn(
                Optional.of(patient)
        );

        when(userRepository.findById(
                1L
        )).thenReturn(
                Optional.of(doctor)
        );

        when(appointmentRepository
                .existsByDoctorAndAppointmentDateAndTimeSlot(
                        any(),
                        any(),
                        any()
                ))
                .thenReturn(false);

        assertNotNull(
                service.createAppointment(
                        "p@gmail.com",
                        request
                )
        );
    }

    @Test
    void invalid_doctor_should_throw() {

        User patient =
                User.builder()
                        .email("p@gmail.com")
                        .build();

        User fakeDoctor =
                User.builder()
                        .role(RoleEnum.PATIENT)
                        .build();

        when(userRepository.findByEmail(
                anyString()
        )).thenReturn(
                Optional.of(patient)
        );

        when(userRepository.findById(
                anyLong()
        )).thenReturn(
                Optional.of(fakeDoctor)
        );

        assertThrows(
                RuntimeException.class,
                () -> service.createAppointment(
                        "p@gmail.com",
                        new CreateAppointmentRequest()
                )
        );
    }
}