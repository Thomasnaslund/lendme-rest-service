package com.tomstry.LendMeApi.services;

import com.tomstry.LendMeApi.entities.Loan;
import com.tomstry.LendMeApi.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan findLoan(int id) {
        Loan e = loanRepository.findById(id).orElse(new Loan());
        return e;
    }

    public Collection<Loan> getComingDeadlines() {
       Collection loans = loanRepository.findTop10OrderByDeadline().orElse(Collections.emptyList());
       return loans;
    }

    public Loan updateLoan(@NotNull Loan loan) {
        return loanRepository.save(loan);
    }
}
