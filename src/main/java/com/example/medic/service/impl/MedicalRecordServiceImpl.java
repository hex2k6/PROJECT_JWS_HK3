package com.example.medic.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.medic.dto.MedicalRecordDto;
import com.example.medic.entity.MedicalRecord;
import com.example.medic.entity.User;
import com.example.medic.exception.ConflictException;
import com.example.medic.exception.ResourceNotFoundException;
import com.example.medic.repository.MedicalRecordRepository;
import com.example.medic.repository.UserRepository;
import com.example.medic.service.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl
        implements MedicalRecordService {

    private final Cloudinary
            cloudinary;

    private final UserRepository
            userRepository;

    private final MedicalRecordRepository
            medicalRecordRepository;

    @Override
    public MedicalRecordDto uploadRecord(

            Long patientId,

            String diagnosis,

            MultipartFile file,

            String doctorEmail
    ) {

        try {

            // kiểm tra file
            if (file == null
                    || file.isEmpty()) {

                throw new ResourceNotFoundException(
                        "File is empty"
                );
            }

            // lấy doctor
            User doctor =
                    userRepository
                            .findByEmail(
                                    doctorEmail
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Doctor not found"
                                    )
                            );

            // lấy patient
            User patient =
                    userRepository
                            .findById(
                                    patientId
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Patient not found"
                                    )
                            );

            // upload lên cloudinary
            Map<?, ?> uploadResult =
                    cloudinary
                            .uploader()
                            .upload(
                                    file.getBytes(),
                                    ObjectUtils.emptyMap()
                            );

            String fileUrl =
                    uploadResult
                            .get(
                                    "secure_url"
                            )
                            .toString();

            // save db
            MedicalRecord
                    medicalRecord =
                    MedicalRecord
                            .builder()
                            .fileUrl(
                                    fileUrl
                            )
                            .diagnosis(
                                    diagnosis
                            )
                            .doctor(
                                    doctor
                            )
                            .patient(
                                    patient
                            )
                            .createdAt(
                                    LocalDateTime.now()
                            )
                            .build();

            medicalRecordRepository
                    .save(
                            medicalRecord
                    );

            // return dto
            return MedicalRecordDto
                    .builder()
                    .id(
                            medicalRecord
                                    .getId()
                    )
                    .fileUrl(
                            fileUrl
                    )
                    .diagnosis(
                            diagnosis
                    )
                    .doctorName(
                            doctor
                                    .getUsername()
                    )
                    .patientName(
                            patient
                                    .getUsername()
                    )
                    .build();

        } catch (Exception e) {

            // in lỗi thật ra console
            e.printStackTrace();

            throw new ConflictException(
                    "Upload failed: "
                            + e.getMessage()
            );
        }
    }
}