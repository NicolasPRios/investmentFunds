package com.investmentFunds.investmentFunds.domain.model.investmentfund;

import com.investmentFunds.investmentFunds.domain.model.fund.Fund;
import com.investmentFunds.investmentFunds.domain.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class InvestmentFund {

    private Integer id;
    private Fund fund;
    private User user;
    private String state;
    private Integer messagePreference;
    private Long openingValue;

}
