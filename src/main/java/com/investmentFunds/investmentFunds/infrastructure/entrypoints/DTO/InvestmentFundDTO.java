package com.investmentFunds.investmentFunds.infrastructure.entrypoints.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentFundDTO {
    private Integer id;
    private Integer idFund;
    private Integer idUser;
    private String state;
    private Integer messagePreference;
    private Long openingValue;
}
