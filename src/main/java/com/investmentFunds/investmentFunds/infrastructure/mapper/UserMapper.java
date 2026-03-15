package com.investmentFunds.investmentFunds.infrastructure.mapper;

import com.investmentFunds.investmentFunds.domain.model.user.User;
import com.investmentFunds.investmentFunds.infrastructure.entity.UserEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "availableBalance", target = "availableBalance"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "cellphoneNumber", target = "cellphoneNumber"),
            @Mapping(source = "password", target = "password")
    })

    User toUser(UserEntity userEntity);

    Iterable<User> toUsers(Iterable<UserEntity> userEntities);

    @InheritConfiguration
    UserEntity toUserEntity(User user);
}
