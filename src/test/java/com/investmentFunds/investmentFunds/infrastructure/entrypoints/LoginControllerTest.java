package com.investmentFunds.investmentFunds.infrastructure.entrypoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investmentFunds.investmentFunds.domain.model.user.User;
import com.investmentFunds.investmentFunds.domain.usecase.auth.AuthService;
import com.investmentFunds.investmentFunds.domain.usecase.jwt.JwtService;
import com.investmentFunds.investmentFunds.domain.usecase.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoginController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
})
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void loginTest() throws Exception {

        User userRequest = User.builder()
                .email("nicolas@test.com")
                .password("123456")
                .build();

        String tokenSimulado = "jwt-token-de-prueba";

        when(authService.login(anyString(), anyString())).thenReturn(tokenSimulado);

        // 2. Act & Assert
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(tokenSimulado));
    }
}
