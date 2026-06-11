package com.example.medic.controller;

import com.example.medic.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    AuthService authService;

    @Test
    void login_should_return_ok()
            throws Exception {

        mockMvc.perform(
                post(
                        "/api/v1/auth/login"
                )
                        .contentType(
                                "application/json"
                        )
                        .content("""
                            {
                              "email":"admin@gmail.com",
                              "password":"123456"
                            }
                        """)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    void register_should_return_ok()
            throws Exception {

        mockMvc.perform(
                post(
                        "/api/v1/auth/register"
                )
                        .contentType(
                                "application/json"
                        )
                        .content("""
                        {
                           "username":"abc",
                           "email":"a@gmail.com",
                           "password":"123456"
                        }
                        """)
        ).andExpect(
                status().isCreated()
        );
    }
}