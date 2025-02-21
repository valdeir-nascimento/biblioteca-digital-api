package io.github.biblioteca.digital.api.application.adapter.in.controller;

import io.github.biblioteca.digital.api.common.builder.UrlBuilder;
import io.github.biblioteca.digital.api.common.dto.UserDTO;
import io.github.biblioteca.digital.api.common.mock.UserMockFactory;
import io.github.biblioteca.digital.api.common.util.JsonUtils;
import io.github.biblioteca.digital.api.domain.port.in.UserRegisterUseCasePort;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserRegisterControllerTest {

    private static final String PATH = "/api/v1/users";

    @InjectMocks
    private UserRegisterController userRegisterController;
    @Mock
    private UserRegisterUseCasePort userRegisterUseCasePort;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userRegisterController).build();
    }

    @Test
    @SneakyThrows
    void givenValidUserDTO_whenCreate_thenReturnStatusCreated() {
        final var url = UrlBuilder.builder(PATH).build();
        final var jsonUserCreatedSuccess = JsonUtils.getContentFromResource("/json/user-create-success.json");
        final var expectedResponse = UserMockFactory.getUserSaved();
        when(userRegisterUseCasePort.create(any(UserDTO.class))).thenReturn(expectedResponse);
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserCreatedSuccess))
                .andExpect(status().isCreated());
    }

    @Test
    @SneakyThrows
    void givenInvalidUserDTO_whenCreateUser_thenReturnsBadRequest() {
        final var url = UrlBuilder.builder(PATH).build();
        final var jsonUserCreatedParamInvalid = JsonUtils.getContentFromResource("/json/book-created-param-invalid.json");
        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUserCreatedParamInvalid))
                .andExpect(status().isBadRequest());
    }

}