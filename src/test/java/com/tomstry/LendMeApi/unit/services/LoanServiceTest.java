package com.tomstry.LendMeApi.unit.services;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.repository.LoanRepository;
import com.tomstry.LendMeApi.service.LoanService;
import org.hibernate.mapping.Any;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {


    @Mock
    LoanRepository dao;

    @InjectMocks
    LoanService loanService;

    @Test
    public void IntervalOverlapsShouldReturnTrueOnOverlappingDates() {
        //Mock data for Mockito
         List<Loan> returnedLoans = new ArrayList<>();
        Loan el1 = new Loan();
        Loan el2 = new Loan();
        el1.setStart(ZonedDateTime.now());
        el1.setEnd(ZonedDateTime.now().plusDays(5));
        el2.setStart(ZonedDateTime.now().minusWeeks(3));
        el2.setEnd(ZonedDateTime.now().minusWeeks(1));
        returnedLoans.add(el1);
        returnedLoans.add(el2);
        Mockito.when(dao.findByItems_Id(Mockito.any(Integer.class)))
                .thenReturn(java.util.Optional.of(returnedLoans));


        //Actual test
        Item newItem = new Item("K", "d", BigDecimal.valueOf(46), new Person("a", "b"));
        newItem.setId(4);
        Loan loan = new Loan();
        loan.setStart(ZonedDateTime.now().plusDays(3));
        loan.setEnd(ZonedDateTime.now().plusDays(7));

        boolean result = loanService.intervalOverlaps(loan, newItem);
        Mockito.verify(dao).findByItems_Id(Mockito.any(Integer.class));
        Assertions.assertTrue(result);

    }

    @Test
    public void shouldReturnNullLoan() {
        //Create one sample userDto object with test data

    }



}