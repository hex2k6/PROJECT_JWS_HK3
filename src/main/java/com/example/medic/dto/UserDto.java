package com.example.medic.dto;

import com.example.medic.enums.RoleEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private RoleEnum role;

    private Boolean active;
}