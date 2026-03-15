package com.investmentFunds.investmentFunds.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Integer id;
    private String name;
    private Long availableBalance;
    private String email;
    private String cellphoneNumber;
    private String password;
}
