package com.example.medic.controller;

import com.example.medic.dto.*;
import com.example.medic.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserDto>
    createUser(

            @RequestBody
            @Valid
            CreateUserRequest request
    ) {

        return ApiResponse
                .<UserDto>builder()
                .success(true)
                .message(
                        "User created"
                )
                .data(
                        userService.createUser(
                                request
                        )
                )
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserDto>>
    getUsers(

            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "5"
            )
            int size,

            @RequestParam(
                    defaultValue = ""
            )
            String keyword
    ) {

        return ApiResponse
                .<List<UserDto>>builder()
                .success(true)
                .message(
                        "Users fetched"
                )
                .data(
                        userService.getAllUsers(
                                page,
                                size,
                                keyword
                        )
                )
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserDto>
    updateUser(

            @PathVariable
            Long id,

            @RequestBody
            UpdateUserRequest request
    ) {

        return ApiResponse
                .<UserDto>builder()
                .success(true)
                .message(
                        "Updated successfully"
                )
                .data(
                        userService.updateUser(
                                id,
                                request
                        )
                )
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String>
    deleteUser(

            @PathVariable
            Long id
    ) {

        userService.deleteUser(id);

        return ApiResponse
                .<String>builder()
                .success(true)
                .message(
                        "Deleted successfully"
                )
                .data(null)
                .build();
    }
}