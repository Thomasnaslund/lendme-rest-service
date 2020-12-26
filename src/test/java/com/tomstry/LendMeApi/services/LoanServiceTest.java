package com.tomstry.LendMeApi.services;

import com.tomstry.LendMeApi.entities.Loan;
import com.tomstry.LendMeApi.repositories.LoanRepository;
import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.testcontainers.shaded.org.apache.commons.lang.reflect.FieldUtils;

import javax.security.auth.message.AuthException;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {


    @Mock
    LoanRepository dao;

    @InjectMocks
    LoanService loanService;

   /*
    public void setUp() {
        loanService = new LoanService(dao);
    }
    */


    @Test
    public void shouldNotSaveObject() {
        //Create one sample userDto object with test data
        Loan loan = new Loan();
        loan.setId(21);
        loan.setDeadline(Timestamp.valueOf(LocalDateTime.now()));
        Mockito.when(dao.save(Mockito.any(Loan.class))).thenAnswer(i -> i.getArguments()[0]);
        Loan retrievedLoan = loanService.updateLoan(loan);
        Mockito.verify(dao, times(1)).save(Mockito.any(Loan.class));
    }

    @Test
    public void shouldReturnNullLoan() {
        //Create one sample userDto object with test data
        Loan loan = new Loan();
        Loan retrievedLoan = loanService.updateLoan(loan);
        Assertions.assertNull(retrievedLoan);

    }

}