package com.tomstry.LendMeApi.unit.services;

import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.repository.LoanRepository;
import com.tomstry.LendMeApi.service.LoanService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {


    @Mock
    LoanRepository dao;

    @InjectMocks
    LoanService loanService;

    @Test
    public void shouldNotSaveObject() {
        //Create one sample userDto object with test data
        Loan loan = new Loan();
        loan.setId(21);
        loan.setStart(ZonedDateTime.now());
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