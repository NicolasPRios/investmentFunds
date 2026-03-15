package com.investmentFunds.investmentFunds.infrastructure.mapper;

import com.investmentFunds.investmentFunds.domain.model.fund.Fund;
import com.investmentFunds.investmentFunds.infrastructure.entity.FundEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FundMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "minimumAmount", target = "minimumAmount"),
            @Mapping(source = "category", target = "category")
    })
    Fund toFund(FundEntity fundEntity);

    Iterable<Fund> toFunds(Iterable<FundEntity>fundEntities);

    @InheritConfiguration
    FundEntity toFundEntity(Fund fund);
}
