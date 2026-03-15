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

public class InvestmentFundUseCase {

    private final InvestmentFundRepository investmentFundRepository;
    private final FundRepository fundRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public InvestmentFundUseCase(InvestmentFundRepository investmentFundRepository, FundRepository fundRepository, UserRepository userRepository, EmailService emailService) {
        this.investmentFundRepository = investmentFundRepository;
        this.fundRepository = fundRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }


    public InvestmentFund save(InvestmentFundDTO investmentFundDTO){
        Fund fund = fundRepository.getById(investmentFundDTO.getIdFund());
        User user = userRepository.getById(investmentFundDTO.getIdUser());
        if(fund.getMinimumAmount()<user.getAvailableBalance()&&investmentFundDTO.getOpeningValue()<user.getAvailableBalance()) {
            user.setAvailableBalance(user.getAvailableBalance()-investmentFundDTO.getOpeningValue());
            InvestmentFund investmentFund = InvestmentFund.builder()
                    .id(investmentFundDTO.getId())
                    .fund(fund)
                    .user(user)
                    .state(investmentFundDTO.getState())
                    .build();
            userRepository.saveUser(user);
            sendingInformation(investmentFundDTO.getMessagePreference(), user,fund);
            return investmentFundRepository.save(investmentFund);
        }
        throw new BusinessException("No tiene saldo disponible para vincularse al fondo " + fund.getName(), "SALDO_INSUFICIENTE");

    }


    private void sendingInformation(Integer messagePreference, User user, Fund fund){
        if(messagePreference==1) {
            emailService.enviarCorreo(user.getEmail(), "Registro a fondo de inversion" + fund.getName(), "Usted se acaba de registrar al fondo de inversion");
        }if(messagePreference==2){
            System.out.println("Se envia por medio de SMS");
        }
    }

    public Iterable<InvestmentFund> allInvestmentFundsByIdUser(Integer idUser){
        return investmentFundRepository.allInvestmentFundsByIdUser(idUser);
    }

    public InvestmentFund cancelSubscription(Integer id) {
        InvestmentFund investmentFund = investmentFundRepository.byId(id);
        if(investmentFund.getState().equals("Aperturado")) {
            User user = investmentFund.getUser();
            user.setAvailableBalance(user.getAvailableBalance() + investmentFund.getOpeningValue());
            investmentFund.setState("Cancelado");
            investmentFundRepository.save(investmentFund);
            userRepository.saveUser(user);
            System.out.println(investmentFund.getId());
            System.out.println(user.getId());
            return investmentFund;
        }
        throw new BusinessException("El Fondo de inversion ya se encuentra en estado cancelado ", "ESTADO_CANCELADO");
    }
}
