package com.tomstry.LendMeApi.service;

import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;


    public Loan findLoan(int id) {
        Loan loan = loanRepository.findById(id).orElse(null);
        return loan;
    }

    public ResponseEntity<Loan> addLoan(Loan loan) {


        savedLoan = loanRepository.saveAndFlush(loan);
        return savedLoan != null;
    }

    public Collection<Loan> getComingDeadlines() {
       Collection loans = loanRepository.findTop10ByOrderByDeadline().orElse(Collections.emptyList());
       return loans;
    }

    public Loan updateLoan(@NotNull Loan loan) {
        return loanRepository.save(loan);
    }
}
