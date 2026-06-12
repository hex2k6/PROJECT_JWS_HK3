package com.example.medic.controller;

import com.example.medic.dto.AuthResponse;
import com.example.medic.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request
        .MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result
        .MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_should_return_ok()
            throws Exception {

        when(authService.login(any()))
                .thenReturn(
                        AuthResponse.builder()
                                .accessToken(
                                        "token"
                                )
                                .refreshToken(
                                        "refresh"
                                )
                                .build()
                );

        String json = """
                {
                    "email":"admin@gmail.com",
                    "password":"123456"
                }
                """;

        mockMvc.perform(
                post(
                        "/api/v1/auth/login"
                )
                        .contentType(
                                MediaType.APPLICATION_JSON
                        )
                        .content(json)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    void register_should_return_created()
            throws Exception {

        when(authService.register(any()))
                .thenReturn(
                        AuthResponse.builder()
                                .accessToken(
                                        "token"
                                )
                                .refreshToken(
                                        "refresh"
                                )
                                .build()
                );

        String json = """
                {
                    "username":"abc",
                    "email":"abc@gmail.com",
                    "password":"123456"
                }
                """;

        mockMvc.perform(
                post(
                        "/api/v1/auth/register"
                )
                        .contentType(
                                MediaType.APPLICATION_JSON
                        )
                        .content(json)
        ).andExpect(
                status().isCreated()
        );
    }
}