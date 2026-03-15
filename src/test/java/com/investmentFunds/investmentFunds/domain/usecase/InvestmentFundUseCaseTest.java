package com.investmentFunds.investmentFunds.domain.usecase;

import com.investmentFunds.investmentFunds.domain.model.common.BusinessException;
import com.investmentFunds.investmentFunds.domain.model.fund.Fund;
import com.investmentFunds.investmentFunds.domain.model.fund.gateways.FundRepository;
import com.investmentFunds.investmentFunds.domain.model.investmentfund.InvestmentFund;
import com.investmentFunds.investmentFunds.domain.model.investmentfund.gateways.InvestmentFundRepository;
import com.investmentFunds.investmentFunds.domain.model.user.User;
import com.investmentFunds.investmentFunds.domain.model.user.gateways.UserRepository;
import com.investmentFunds.investmentFunds.domain.usecase.email.EmailService;
import com.investmentFunds.investmentFunds.infrastructure.entrypoints.DTO.InvestmentFundDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvestmentFundUseCaseTest {
    @Mock private InvestmentFundRepository investmentFundRepository;
    @Mock private FundRepository fundRepository;
    @Mock private UserRepository userRepository;
    @Mock private EmailService emailService;

    @InjectMocks
    private InvestmentFundUseCase useCase;

    private User user;
    private Fund fund;
    private InvestmentFundDTO dto;

    @BeforeEach
    void setUp() {
        user = User.builder().id(1).email("test@mail.com").availableBalance(1000000L).build();
        fund = Fund.builder().id(1).name("Fondo Test").minimumAmount(100000L).build();
        dto = new InvestmentFundDTO(1, 1, 1, "Aperturado", 1, 50000L);
    }

    @Test
    @DisplayName("Save: Debe guardar inversión y enviar correo (Preference 1)")
    void saveInvestmentSuccessEmail() {
        when(fundRepository.getById(anyInt())).thenReturn(fund);
        when(userRepository.getById(anyInt())).thenReturn(user);
        when(investmentFundRepository.save(any())).thenReturn(new InvestmentFund());

        useCase.save(dto);

        assertEquals(950000L, user.getAvailableBalance());
        verify(emailService, times(1)).enviarCorreo(anyString(), anyString(), anyString());
        verify(userRepository).saveUser(user);
    }

    @Test
    @DisplayName("Save: Debe guardar inversión y simular SMS (Preference 2)")
    void saveInvestmentSuccessSMS() {
        dto = new InvestmentFundDTO(1, 1, 1, "Aperturado", 2, 50000L);
        when(fundRepository.getById(anyInt())).thenReturn(fund);
        when(userRepository.getById(anyInt())).thenReturn(user);

        useCase.save(dto);

        verify(emailService, never()).enviarCorreo(any(), any(), any());
        verify(investmentFundRepository).save(any());
    }

    @Test
    @DisplayName("Save: Debe lanzar excepción por saldo insuficiente")
    void saveInvestmentInsufficientBalance() {
        user.setAvailableBalance(10L);
        when(fundRepository.getById(anyInt())).thenReturn(fund);
        when(userRepository.getById(anyInt())).thenReturn(user);

        assertThrows(BusinessException.class, () -> useCase.save(dto));
    }

    @Test
    @DisplayName("allInvestmentFundsByIdUser: Debe llamar al repositorio")
    void allInvestmentFundsTest() {
        when(investmentFundRepository.allInvestmentFundsByIdUser(anyInt())).thenReturn(List.of());

        Iterable<InvestmentFund> result = useCase.allInvestmentFundsByIdUser(1);

        assertNotNull(result);
        verify(investmentFundRepository).allInvestmentFundsByIdUser(1);
    }

    @Test
    @DisplayName("CancelSubscription: Debe cancelar exitosamente y devolver saldo")
    void cancelSubscriptionSuccess() {
        InvestmentFund investment = InvestmentFund.builder()
                .id(1).state("Aperturado").user(user).fund(fund).build();

        when(investmentFundRepository.byId(anyInt())).thenReturn(investment);

        InvestmentFund result = useCase.cancelSubscription(1);

        assertEquals("Cancelado", result.getState());
        assertEquals(1100000L, user.getAvailableBalance());
        verify(userRepository).saveUser(user);
        verify(investmentFundRepository).save(investment);
    }

    @Test
    @DisplayName("CancelSubscription: Debe fallar si ya está cancelado")
    void cancelSubscriptionFail() {
        InvestmentFund investment = InvestmentFund.builder().id(1).state("Cancelado").build();
        when(investmentFundRepository.byId(anyInt())).thenReturn(investment);

        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.cancelSubscription(1));
        assertEquals("ESTADO_CANCELADO", ex.getCode());
    }
}
