package com.example.medic.repository;

import com.example.medic.entity.Appointment;
import com.example.medic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    boolean existsByDoctorAndAppointmentDateAndTimeSlot(
            User doctor,
            LocalDate appointmentDate,
            String timeSlot
    );
}