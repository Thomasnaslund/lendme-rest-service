package com.tomstry.LendMeApi.unit.services;

import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.generator.Generate;
import com.tomstry.LendMeApi.repository.LoanRepository;
import com.tomstry.LendMeApi.service.LoanService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class LoanServiceTest {

    @Mock
    LoanRepository dao;

    @InjectMocks
    LoanService loanService;

    private Collection<Loan> mockLoans;
    private Loan mockLoan;

    @Before
    public void init () {
        mockLoan = Generate.newLoan();
        mockLoans = new ArrayList<>();
        mockLoans.add(mockLoan);

        //Mocking spring data functions
        Mockito.when(dao.findByItem_Id(null)).thenReturn(Optional.of(mockLoans));
    }

    @Test
    public void testIsBooked() {
        Loan newMockLoan = Generate.newLoan();

        //New loan overlaps
        newMockLoan.setStart(mockLoan.getStart().minusDays(2));
        newMockLoan.setEnd(mockLoan.getStart().plusDays(1));
        boolean booked = loanService.isItemBooked(newMockLoan);
        Assertions.assertTrue(booked);
    }

    @Test
    public void testHasOverlappingDates() {
        Loan existingMockLoan = Generate.newLoan();
        Loan newMockLoan = Generate.newLoan();
        newMockLoan.setStart(existingMockLoan.getStart().minusDays(2));
        newMockLoan.setEnd(existingMockLoan.getStart().plusDays(1));
        Assertions.assertTrue(LoanService.hasOverlappingDates(existingMockLoan, newMockLoan));
    }
}