package com.example.medic.controller;

import com.example.medic.dto.*;
import com.example.medic.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(
        "/api/v1/doctor/records"
)
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService
            medicalRecordService;

    @PostMapping("/upload")
    public ApiResponse<
            MedicalRecordDto
            >
    upload(

            @RequestParam
            Long patientId,

            @RequestParam
            String diagnosis,

            @RequestParam
            MultipartFile file,

            Authentication authentication
    ) {

        return ApiResponse
                .<MedicalRecordDto>
                        builder()
                .success(true)
                .message(
                        "Uploaded"
                )
                .data(
                        medicalRecordService
                                .uploadRecord(
                                        patientId,
                                        diagnosis,
                                        file,
                                        authentication
                                                .getName()
                                )
                )
                .build();
    }
}