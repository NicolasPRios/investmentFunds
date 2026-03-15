package com.investmentFunds.investmentFunds.infrastructure.entrypoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investmentFunds.investmentFunds.domain.model.investmentfund.InvestmentFund;
import com.investmentFunds.investmentFunds.domain.usecase.InvestmentFundUseCase;
import com.investmentFunds.investmentFunds.domain.usecase.jwt.JwtService;
import com.investmentFunds.investmentFunds.domain.usecase.jwt.JwtAuthenticationFilter;
import com.investmentFunds.investmentFunds.infrastructure.entrypoints.DTO.InvestmentFundDTO;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = InvestmentFundController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
})
@AutoConfigureMockMvc(addFilters = false)
public class InvestmentFundControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InvestmentFundController investmentFundController;

    @MockitoBean
    private InvestmentFundUseCase investmentFundUseCase;

    @MockitoBean
    private JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Debe verificar que el controlador no sea nulo (Cubre Constructor)")
    void contextLoads() {
        assertNotNull(investmentFundController);
    }

    @Test
    @DisplayName("GET / - Debe listar inversiones por ID de usuario")
    void allInvestmentFundsByIdUserTest() throws Exception {
        InvestmentFund investment = InvestmentFund.builder().id(1).state("OPEN").build();
        when(investmentFundUseCase.allInvestmentFundsByIdUser(anyInt())).thenReturn(List.of(investment));

        mockMvc.perform(get("/")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].state").value("OPEN"));
    }

    @Test
    @DisplayName("POST / - Debe crear una nueva inversión")
    void createInvestmentFundTest() throws Exception {

        InvestmentFundDTO dto = new InvestmentFundDTO(1, 1, 1, "OPEN", 1, 10000L);
        InvestmentFund savedInvestment = InvestmentFund.builder().id(1).state("OPEN").build();

        when(investmentFundUseCase.save(any(InvestmentFundDTO.class))).thenReturn(savedInvestment);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.state").value("OPEN"));
    }

    @Test
    @DisplayName("PUT / - Debe cancelar una suscripción")
    void cancelSubscriptionTest() throws Exception {

        InvestmentFund cancelledInvestment = InvestmentFund.builder().id(1).state("CANCELLED").build();
        when(investmentFundUseCase.cancelSubscription(anyInt())).thenReturn(cancelledInvestment);

        mockMvc.perform(put("/")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("CANCELLED"));
    }
}
