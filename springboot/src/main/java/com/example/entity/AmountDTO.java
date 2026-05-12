package com.example.entity;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class AmountDTO {
    private BigDecimal Amount;  //总价
    private BigDecimal Discount;  //总价
    private BigDecimal money;  //总价
}
