package com.example.medic.service;

import com.example.medic.dto.*;

import java.util.List;

public interface UserService {

    UserDto createUser(
            CreateUserRequest request
    );

    List<UserDto> getAllUsers(
            int page,
            int size,
            String keyword
    );

    UserDto updateUser(
            Long id,
            UpdateUserRequest request
    );

    void deleteUser(
            Long id
    );
}