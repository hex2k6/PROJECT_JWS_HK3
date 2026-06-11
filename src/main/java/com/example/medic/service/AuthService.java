package com.example.medic.service;

import com.example.medic.dto.*;

public interface AuthService {

    AuthResponse register(
            RegisterRequest request
    );

    AuthResponse login(
            LoginRequest request
    );

    AuthResponse refreshToken(
            RefreshTokenRequest request
    );

    void logout(
            String token
    );
    void changePassword(
            String email,
            ChangePasswordRequest request
    );
}