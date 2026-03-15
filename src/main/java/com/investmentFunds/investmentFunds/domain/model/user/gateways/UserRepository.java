package com.investmentFunds.investmentFunds.domain.model.user.gateways;

import com.investmentFunds.investmentFunds.domain.model.user.User;

public interface UserRepository {
    User getById(Integer id);
    User saveUser(User user);
    User findByEmail(String email);
}
