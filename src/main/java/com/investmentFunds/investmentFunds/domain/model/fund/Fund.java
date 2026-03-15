package com.investmentFunds.investmentFunds.domain.model.fund;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Fund {
    private Integer id;
    private String name;
    private Long minimumAmount;
    private String category;
}
