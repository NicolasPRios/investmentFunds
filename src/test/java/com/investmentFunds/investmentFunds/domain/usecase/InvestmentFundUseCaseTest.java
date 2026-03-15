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
        user = User.builder()
                .id(1)
                .email("test@mail.com")
                .availableBalance(100000L)
                .build();

        fund = Fund.builder()
                .name("Fondo Test")
                .minimumAmount(50000L)
                .build();

        dto = new InvestmentFundDTO();
        dto.setId(1);
        dto.setIdUser(1);
        dto.setIdFund(1);
        dto.setOpeningValue(20000L);
        dto.setState("Aperturado");
        dto.setMessagePreference(1); // 1 = Email
    }

    @Test
    void save_ExitosoConEmail() {
        // Arrange
        when(fundRepository.getById(anyInt())).thenReturn(fund);
        when(userRepository.getById(anyInt())).thenReturn(user);
        when(investmentFundRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // Act
        InvestmentFund result = useCase.save(dto);

        // Assert
        assertNotNull(result);
        assertEquals(80000L, user.getAvailableBalance()); // 100k - 20k
        verify(userRepository).saveUser(user);
        verify(emailService).enviarCorreo(eq("test@mail.com"), anyString(), anyString());
        verify(investmentFundRepository).save(any());
    }

    @Test
    void save_LanzaExcepcionPorSaldoInsuficiente() {
        // Arrange: Saldo de usuario (10k) menor al mínimo del fondo (50k)
        user.setAvailableBalance(10000L);
        when(fundRepository.getById(anyInt())).thenReturn(fund);
        when(userRepository.getById(anyInt())).thenReturn(user);

        // Act & Assert
        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.save(dto));
        assertEquals("SALDO_INSUFICIENTE", ex.getCode());
        verify(investmentFundRepository, never()).save(any());
    }

    @Test
    void cancelSubscription_Exitoso() {
        // Arrange
        InvestmentFund inv = InvestmentFund.builder()
                .id(1)
                .state("Aperturado")
                .openingValue(20000L)
                .user(user)
                .build();

        when(investmentFundRepository.byId(1)).thenReturn(inv);

        // Act
        InvestmentFund result = useCase.cancelSubscription(1);

        // Assert
        assertEquals("Cancelado", result.getState());
        assertEquals(120000L, user.getAvailableBalance()); // 100k + 20k devueltos
        verify(investmentFundRepository).save(inv);
        verify(userRepository).saveUser(user);
    }

    @Test
    void cancelSubscription_FallaSiYaEstaCancelado() {
        // Arrange
        InvestmentFund inv = InvestmentFund.builder()
                .id(1)
                .state("Cancelado")
                .build();
        when(investmentFundRepository.byId(1)).thenReturn(inv);

        // Act & Assert
        assertThrows(BusinessException.class, () -> useCase.cancelSubscription(1));
    }

    @Test
    void allInvestmentFundsByIdUser_RetornaLista() {
        // Arrange
        when(investmentFundRepository.allInvestmentFundsByIdUser(1))
                .thenReturn(List.of(new InvestmentFund()));

        // Act
        Iterable<InvestmentFund> result = useCase.allInvestmentFundsByIdUser(1);

        // Assert
        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
    }
}
