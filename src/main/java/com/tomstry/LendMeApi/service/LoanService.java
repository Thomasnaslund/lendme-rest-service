package com.tomstry.LendMeApi.service;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.ItemLoan;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.repository.ItemRepository;
import com.tomstry.LendMeApi.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;


    public Loan findLoan(int id) {
        Loan loan = loanRepository.findById(id).orElse(null);
        return loan;
    }

    //TODO: Should split this to multiple general functions
    public Loan addLoan(Loan loan) {

            for (ItemLoan itemloan : loan.getItems()) {
                // is item is attached to other loans?
                if (!itemloan.getItem().getLoans().isEmpty()) {
                    //Check if dates overlap
                    Set<ItemLoan> loans = itemloan.getItem().getLoans();
                    for (ItemLoan existingLoan : loans) {
                        ZonedDateTime start = existingLoan.getLoan().getStart();
                        ZonedDateTime end = existingLoan.getLoan().getEnd();
                        if (hasOverlap(start, end, loan.getStart(), loan.getEnd())) {
                            // TODO: Should Log overlap here
                        }
                    }
                }
            }

        Loan savedLoan = loanRepository.save(loan);
        return savedLoan;
    }




    private static boolean hasOverlap(ZonedDateTime t1,ZonedDateTime t2 , ZonedDateTime p1, ZonedDateTime p2) {
        return !t2.isBefore(p1) && !t1.isAfter(p2);
    }

    public Collection<Loan> getComingDeadlines() {
       Collection loans = loanRepository.findTop10ByOrderByDeadline().orElse(Collections.emptyList());
       return loans;
    }

    public Loan updateLoan(@NotNull Loan loan) {
        return loanRepository.save(loan);
    }
}
