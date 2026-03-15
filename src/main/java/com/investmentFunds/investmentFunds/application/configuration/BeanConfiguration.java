package com.investmentFunds.investmentFunds.application.configuration;

import com.investmentFunds.investmentFunds.domain.model.fund.gateways.FundRepository;
import com.investmentFunds.investmentFunds.domain.model.investmentfund.gateways.InvestmentFundRepository;
import com.investmentFunds.investmentFunds.domain.model.user.gateways.UserRepository;
import com.investmentFunds.investmentFunds.domain.usecase.email.EmailService;
import com.investmentFunds.investmentFunds.domain.usecase.FundUseCase;
import com.investmentFunds.investmentFunds.domain.usecase.InvestmentFundUseCase;
import com.investmentFunds.investmentFunds.domain.usecase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public InvestmentFundUseCase investmentFundUseCase(InvestmentFundRepository investmentFundRepository, FundRepository fundRepository, UserRepository userRepository, EmailService emailService){
        return new InvestmentFundUseCase(investmentFundRepository,fundRepository,userRepository,emailService);
    }

    @Bean
    public FundUseCase fundUseCase(FundRepository fundRepository){
        return new FundUseCase(fundRepository);
    }

    @Bean
    public UserUseCase userUseCase(UserRepository userRepository, PasswordEncoder  passwordEncoder){
        return new UserUseCase(userRepository, passwordEncoder);
    }
}
