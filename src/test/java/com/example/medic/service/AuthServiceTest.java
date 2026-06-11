package com.example.medic.service;

import com.example.medic.dto.LoginRequest;
import com.example.medic.dto.RegisterRequest;
import com.example.medic.entity.User;
import com.example.medic.enums.RoleEnum;
import com.example.medic.repository.TokenBlacklistRepository;
import com.example.medic.repository.UserRepository;
import com.example.medic.security.JwtService;
import com.example.medic.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenBlacklistRepository blacklistRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void register_should_success() {

        RegisterRequest request =
                new RegisterRequest();

        request.setUsername("test");
        request.setEmail("test@gmail.com");
        request.setPassword("123456");

        when(userRepository.existsByEmail(
                request.getEmail()
        )).thenReturn(false);

        when(passwordEncoder.encode(
                request.getPassword()
        )).thenReturn("encoded");

        when(jwtService.generateAccessToken(
                anyString()
        )).thenReturn("access");

        when(jwtService.generateRefreshToken(
                anyString()
        )).thenReturn("refresh");

        var result =
                authService.register(
                        request
                );

        assertNotNull(result);
        assertEquals(
                "access",
                result.getAccessToken()
        );
    }

    @Test
    void register_email_exists_should_throw() {

        RegisterRequest request =
                new RegisterRequest();

        request.setEmail(
                "test@gmail.com"
        );

        when(userRepository.existsByEmail(
                request.getEmail()
        )).thenReturn(true);

        assertThrows(
                RuntimeException.class,
                () -> authService.register(
                        request
                )
        );
    }

    @Test
    void login_success() {

        LoginRequest request =
                new LoginRequest();

        request.setEmail(
                "admin@gmail.com"
        );

        request.setPassword(
                "123456"
        );

        User user =
                User.builder()
                        .email(
                                "admin@gmail.com"
                        )
                        .password(
                                "123"
                        )
                        .role(
                                RoleEnum.ADMIN
                        )
                        .build();

        when(userRepository.findByEmail(
                anyString()
        )).thenReturn(
                Optional.of(user)
        );

        when(jwtService.generateAccessToken(
                anyString()
        )).thenReturn(
                "token"
        );

        when(jwtService.generateRefreshToken(
                anyString()
        )).thenReturn(
                "refresh"
        );

        var result =
                authService.login(
                        request
                );

        assertNotNull(result);
    }
}