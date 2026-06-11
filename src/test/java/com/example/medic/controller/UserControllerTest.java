package com.example.medic.controller;

import com.example.medic.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockitoBean
    UserService userService;

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