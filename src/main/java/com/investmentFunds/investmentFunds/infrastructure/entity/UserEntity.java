package com.investmentFunds.investmentFunds.infrastructure.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    private Integer id;
    private String name;
    private Long availableBalance;
    private String email;
    private String cellphoneNumber;
    private String password;
}
