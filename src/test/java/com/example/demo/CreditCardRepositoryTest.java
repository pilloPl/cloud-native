package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreditCardRepositoryTest {

    @Autowired CreditCardRepository creditCardRepository;

    @Test
    void shouldLoadCreditCardsByBalance() {
        //given
        creditCardRepository.save(new CreditCard(BigDecimal.TEN));
        creditCardRepository.save(new CreditCard(new BigDecimal(100)));

        //when
        List<CreditCard> allByBalanceGreaterThan = creditCardRepository.findAllByBalanceGreaterThan(new BigDecimal(50));
        List<CreditCard> all = creditCardRepository.findAll();

        //then
        assertThat(allByBalanceGreaterThan).hasSize(1);
        assertThat(all).hasSize(2);
    }

}