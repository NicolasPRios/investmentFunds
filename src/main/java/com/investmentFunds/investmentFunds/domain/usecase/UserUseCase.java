package com.investmentFunds.investmentFunds.domain.usecase;

import com.investmentFunds.investmentFunds.domain.model.common.BusinessException;
import com.investmentFunds.investmentFunds.domain.model.user.User;
import com.investmentFunds.investmentFunds.domain.model.user.gateways.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user){
            if (user.getAvailableBalance() >= 500000) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.saveUser(user);
            }
            throw new BusinessException("El saldo inicial para registro, debe ser mayor o igual de COP $500.000.", "SALDO_MENOR");

    }

    public User updateUser(User user){
        return userRepository.saveUser(user);
    }
}
