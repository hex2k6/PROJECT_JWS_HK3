package com.example.medic.controller;

import com.example.medic.dto.*;
import com.example.medic.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AuthResponse> register(

            @RequestBody
            @Valid
            RegisterRequest request
    ) {

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Register successfully")
                .data(
                        authService.register(request)
                )
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(

            @RequestBody
            @Valid
            LoginRequest request
    ) {

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Login successfully")
                .data(
                        authService.login(request)
                )
                .build();
    }
    @PostMapping("/refresh")
    public ApiResponse<AuthResponse> refresh(

            @RequestBody
            RefreshTokenRequest request
    ) {

        return ApiResponse
                .<AuthResponse>builder()
                .success(true)
                .message(
                        "Refresh token success"
                )
                .data(
                        authService.refreshToken(
                                request
                        )
                )
                .build();
    }
    @PostMapping("/logout")
    public ApiResponse<?> logout(

            @RequestHeader(
                    "Authorization"
            ) String authHeader
    ) {

        String token =
                authHeader.substring(7);

        authService.logout(token);

        return ApiResponse.builder()
                .success(true)
                .message(
                        "Logout success"
                )
                .data(null)
                .build();
    }
    @PutMapping("/change-password")
    public ApiResponse<String>
    changePassword(

            @RequestBody
            ChangePasswordRequest request,

            Authentication authentication
    ) {

        authService
                .changePassword(
                        authentication
                                .getName(),
                        request
                );

        return ApiResponse
                .<String>builder()
                .success(true)
                .message(
                        "Password changed"
                )
                .data(null)
                .build();
    }
}