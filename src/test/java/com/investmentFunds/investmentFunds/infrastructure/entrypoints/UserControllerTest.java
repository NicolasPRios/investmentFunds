package com.investmentFunds.investmentFunds.infrastructure.entrypoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investmentFunds.investmentFunds.domain.model.user.User;
import com.investmentFunds.investmentFunds.domain.model.user.gateways.UserRepository;
import com.investmentFunds.investmentFunds.domain.usecase.UserUseCase;
import com.investmentFunds.investmentFunds.domain.usecase.jwt.JwtService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserUseCase userUseCase;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createdUserTest() throws Exception {
        User user = User.builder()
                .id(1)
                .name("Nicolas")
                .availableBalance(100000L)
                .email("nicolas@test.com")
                .cellphoneNumber("3001234567")
                .password("encriptada123")
                .build();

        when(userUseCase.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Nicolas"))
                .andExpect(jsonPath("$.email").value("nicolas@test.com"));
    }
}
