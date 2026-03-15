package com.investmentFunds.investmentFunds.domain.usecase;

import com.investmentFunds.investmentFunds.domain.model.fund.Fund;
import com.investmentFunds.investmentFunds.domain.model.fund.gateways.FundRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FundUseCaseTest {
    @Mock
    private FundRepository fundRepository;

    @InjectMocks
    private FundUseCase fundUseCase;

    @Test
    @DisplayName("Debe guardar un fondo correctamente llamando al repositorio")
    void saveFundTest() {

        Fund fundInput = Fund.builder()
                .name("Fondo Balanceado")
                .minimumAmount(50000L)
                .category("Moderado")
                .build();

        when(fundRepository.saveFund(any(Fund.class))).thenReturn(fundInput);

        Fund result = fundUseCase.saveFund(fundInput);

        assertNotNull(result);
        assertEquals("Fondo Balanceado", result.getName());

        verify(fundRepository, times(1)).saveFund(any(Fund.class));
    }
}
