package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal balance;

    CreditCard(BigDecimal balance) {
        this.balance = balance;
    }

    public CreditCard() {
    }
}
