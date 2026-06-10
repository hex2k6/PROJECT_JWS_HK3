package com.example.medic.dto;

import com.example.medic.enums.RoleEnum;
import lombok.Data;

@Data
public class UpdateUserRequest {

    private String username;

    private String email;

    private RoleEnum role;

    private Boolean active;
}