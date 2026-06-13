package com.example.medic.service.impl;

import com.example.medic.dto.*;
import com.example.medic.entity.TokenBlacklist;
import com.example.medic.entity.User;
import com.example.medic.enums.RoleEnum;
import com.example.medic.exception.ConflictException;
import com.example.medic.exception.ResourceNotFoundException;
import com.example.medic.repository.UserRepository;
import com.example.medic.security.JwtService;
import com.example.medic.service.AuthService;
import com.example.medic.service.RedisBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.medic.dto.ForgotPasswordRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository
            userRepository;

    private final RedisBlacklistService
            blacklistService;

    private final PasswordEncoder
            passwordEncoder;

    private final JwtService
            jwtService;

    private final AuthenticationManager
            authenticationManager;

    @Override
    public AuthResponse register(
            RegisterRequest request
    ) {

        if (userRepository.existsByEmail(
                request.getEmail()
        )) {

            throw new ConflictException(
                    "Email already exists"
            );
        }

        User user = User.builder()
                .username(
                        request.getUsername()
                )
                .email(
                        request.getEmail()
                )
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .role(
                        RoleEnum.PATIENT
                )
                .active(
                        true
                )
                .build();

        userRepository.save(
                user
        );

        String accessToken =
                jwtService.generateAccessToken(
                        user.getEmail()
                );

        String refreshToken =
                jwtService.generateRefreshToken(
                        user.getEmail()
                );

        return AuthResponse.builder()
                .accessToken(
                        accessToken
                )
                .refreshToken(
                        refreshToken
                )
                .build();
    }

    @Override
    public AuthResponse login(
            LoginRequest request
    ) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user =
                userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        String accessToken =
                jwtService.generateAccessToken(
                        user.getEmail()
                );

        String refreshToken =
                jwtService.generateRefreshToken(
                        user.getEmail()
                );

        return AuthResponse.builder()
                .accessToken(
                        accessToken
                )
                .refreshToken(
                        refreshToken
                )
                .build();
    }

    @Override
    public AuthResponse refreshToken(
            RefreshTokenRequest request
    ) {

        String refreshToken =
                request.getRefreshToken();

        String email =
                jwtService.extractUsername(
                        refreshToken
                );

        User user =
                userRepository
                        .findByEmail(
                                email
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        UserDetails userDetails =
                new org.springframework
                        .security
                        .core
                        .userdetails
                        .User(

                        user.getEmail(),

                        user.getPassword(),

                        List.of()
                );

        if (!jwtService.isTokenValid(
                refreshToken,
                user.getEmail()
        )) {

            throw new RuntimeException(
                    "Invalid refresh token"
            );
        }

        String newAccessToken =
                jwtService.generateAccessToken(
                        user.getEmail()
                );

        return AuthResponse.builder()
                .accessToken(
                        newAccessToken
                )
                .refreshToken(
                        refreshToken
                )
                .build();
    }

    @Override
    public void logout(
            String token
    ) {

        long expiration =
                jwtService.getExpiration(
                        token
                );

        blacklistService.blacklistToken(
                token,
                expiration
        );
    }
    @Override
    public void changePassword(

            String email,

            ChangePasswordRequest request
    ) {

        User user =
                userRepository
                        .findByEmail(
                                email
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        boolean matches =
                passwordEncoder.matches(
                        request.getOldPassword(),
                        user.getPassword()
                );

        if (!matches) {

            throw new ConflictException(
                    "Old password incorrect"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(
                user
        );
    }
    @Override
    public void forgotPassword(
            ForgotPasswordRequest request
    ) {

        User user =
                userRepository
                        .findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Email not found"
                                )
                        );

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(
                user
        );
    }
}