package com.example.medic.service;

import com.example.medic.dto.MedicalRecordDto;
import org.springframework.web.multipart.MultipartFile;

public interface MedicalRecordService {

    MedicalRecordDto uploadRecord(
            Long patientId,
            String diagnosis,
            MultipartFile file,
            String doctorEmail
    );
}