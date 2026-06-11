package com.example.medic.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicalRecordDto {

    private Long id;

    private String fileUrl;

    private String diagnosis;

    private String patientName;

    private String doctorName;
}