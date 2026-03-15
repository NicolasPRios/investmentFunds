package com.investmentFunds.investmentFunds.infrastructure.entrypoints;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.investmentFunds.investmentFunds.domain.model.fund.Fund;
import com.investmentFunds.investmentFunds.domain.usecase.FundUseCase;
import com.investmentFunds.investmentFunds.domain.usecase.jwt.JwtService;
import com.investmentFunds.investmentFunds.domain.usecase.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FundsController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
})
@AutoConfigureMockMvc(addFilters = false)
public class FundsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FundsController fundsController;

    @MockitoBean
    private FundUseCase fundUseCase;

    @MockitoBean
    private JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Debe verificar que el controlador de fondos no sea nulo")
    void contextLoads() {
        assertNotNull(fundsController);
    }

    @Test
    @DisplayName("Debe crear un fondo exitosamente y retornar 201 Created")
    void createFundTest() throws Exception {

        Fund fundRequest = Fund.builder()
                .id(1)
                .name("Fondo Equilibrado")
                .minimumAmount(75000L)
                .category("Inversion")
                .build();

        when(fundUseCase.saveFund(any(Fund.class))).thenReturn(fundRequest);

        mockMvc.perform(post("/fund")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fundRequest)))
                .andExpect(status().isCreated()) // Verifica el HttpStatus.CREATED
                .andExpect(jsonPath("$.name").value("Fondo Equilibrado"))
                .andExpect(jsonPath("$.minimumAmount").value(75000L))
                .andExpect(jsonPath("$.category").value("Inversion"));
    }
}
