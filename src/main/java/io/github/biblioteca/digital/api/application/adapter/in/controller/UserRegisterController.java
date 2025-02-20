package io.github.biblioteca.digital.api.application.adapter.in.controller;

import io.github.biblioteca.digital.api.common.dto.UserDTO;
import io.github.biblioteca.digital.api.domain.port.in.UserRegisterUseCasePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRegisterController {

    private final UserRegisterUseCasePort userRegisterUseCasePort;

    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
        final var response = userRegisterUseCasePort.create(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
