package com.example.medic.controller;

import com.example.medic.service.UserService;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Test
    void dummy_test() {

        assert true;
    }

    @Test
    void dummy_test_2() {

        assert true;
    }

    @Test
    void dummy_test_3() {

        assert true;
    }
}