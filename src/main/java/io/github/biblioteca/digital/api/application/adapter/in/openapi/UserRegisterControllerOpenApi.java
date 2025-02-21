package io.github.biblioteca.digital.api.application.adapter.in.openapi;

import io.github.biblioteca.digital.api.common.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Users", description = "Endpoints for managing user registrations")
public interface UserRegisterControllerOpenApi {

    @Operation(
            summary = "Create a new user",
            description = "Registers a new user in the application and returns the created user data."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content)
    })
    ResponseEntity<UserDTO> create(
            @RequestBody(
                    description = "User data for registration",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))
            )
            UserDTO userDTO
    );
}