package com.investmentFunds.investmentFunds.infrastructure.adapter;

import com.investmentFunds.investmentFunds.infrastructure.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserEntity,Integer> {
    UserEntity findByEmail(String email);
}
