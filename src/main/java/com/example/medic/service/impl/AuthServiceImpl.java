package com.example.medic.service.impl;

import com.example.medic.dto.AuthResponse;
import com.example.medic.dto.LoginRequest;
import com.example.medic.dto.RefreshTokenRequest;
import com.example.medic.dto.RegisterRequest;
import com.example.medic.entity.TokenBlacklist;
import com.example.medic.entity.User;
import com.example.medic.enums.RoleEnum;
import com.example.medic.repository.TokenBlacklistRepository;
import com.example.medic.repository.UserRepository;
import com.example.medic.security.JwtService;
import com.example.medic.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository userRepository;

    private final TokenBlacklistRepository
            blacklistRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager
            authenticationManager;

    @Override
    public AuthResponse register(
            RegisterRequest request
    ) {

        if (userRepository.existsByEmail(
                request.getEmail()
        )) {

            throw new RuntimeException(
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
                .role(RoleEnum.PATIENT)
                .active(true)
                .build();

        userRepository.save(user);

        String accessToken =
                jwtService.generateAccessToken(
                        user.getEmail()
                );

        String refreshToken =
                jwtService.generateRefreshToken(
                        user.getEmail()
                );

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
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

        User user = userRepository
                .findByEmail(
                        request.getEmail()
                )
                .orElseThrow(() ->
                        new RuntimeException(
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
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthResponse refreshToken(
            RefreshTokenRequest request
    ) {

        String email =
                jwtService.extractUsername(
                        request.getRefreshToken()
                );

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        )
                );

        String newAccessToken =
                jwtService.generateAccessToken(
                        user.getEmail()
                );

        return AuthResponse.builder()
                .accessToken(
                        newAccessToken
                )
                .refreshToken(
                        request.getRefreshToken()
                )
                .build();
    }

    @Override
    public void logout(
            String token
    ) {

        TokenBlacklist blacklist =
                TokenBlacklist.builder()
                        .token(token)
                        .build();

        blacklistRepository.save(
                blacklist
        );
    }
}