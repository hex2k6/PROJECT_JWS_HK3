package com.example.medic.service.impl;

import com.example.medic.dto.*;
import com.example.medic.entity.User;
import com.example.medic.repository.UserRepository;
import com.example.medic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final UserRepository
            userRepository;

    private final PasswordEncoder
            passwordEncoder;

    @Override
    public UserDto createUser(
            CreateUserRequest request
    ) {

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
                        request.getRole()
                )
                .active(true)
                .build();

        userRepository.save(user);

        return mapToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers(
            int page,
            int size,
            String keyword
    ) {

        return userRepository
                .findByUsernameContainingIgnoreCase(
                        keyword,
                        PageRequest.of(
                                page,
                                size
                        )
                )
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public UserDto updateUser(
            Long id,
            UpdateUserRequest request
    ) {

        User user = userRepository
                .findById(id)
                .orElseThrow();

        user.setUsername(
                request.getUsername()
        );

        user.setEmail(
                request.getEmail()
        );

        user.setRole(
                request.getRole()
        );

        user.setActive(
                request.getActive()
        );

        userRepository.save(user);

        return mapToDto(user);
    }

    @Override
    public void deleteUser(
            Long id
    ) {

        userRepository.deleteById(id);
    }

    private UserDto mapToDto(
            User user
    ) {

        return UserDto.builder()
                .id(user.getId())
                .username(
                        user.getUsername()
                )
                .email(
                        user.getEmail()
                )
                .role(
                        user.getRole()
                )
                .active(
                        user.getActive()
                )
                .build();
    }
}