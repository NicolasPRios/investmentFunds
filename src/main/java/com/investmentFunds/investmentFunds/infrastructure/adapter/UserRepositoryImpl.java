package com.investmentFunds.investmentFunds.infrastructure.adapter;

import com.investmentFunds.investmentFunds.domain.model.user.User;
import com.investmentFunds.investmentFunds.domain.model.user.gateways.UserRepository;
import com.investmentFunds.investmentFunds.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserMongoRepository userMongoRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(UserMongoRepository userMongoRepository, UserMapper userMapper) {
        this.userMongoRepository = userMongoRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User getById(Integer id) {
        return userMapper.toUser(userMongoRepository.findById(id).get());
    }

    @Override
    public User saveUser(User user) {
        return userMapper.toUser(userMongoRepository.save(userMapper.toUserEntity(user)));
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.toUser(userMongoRepository.findByEmail(email));
    }
}
