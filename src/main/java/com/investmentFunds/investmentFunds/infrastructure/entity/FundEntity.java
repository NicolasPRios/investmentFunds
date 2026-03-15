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
public class FundEntity {
    @Id
    private Integer id;
    private String name;
    private Long minimumAmount;
    private String category;
}
