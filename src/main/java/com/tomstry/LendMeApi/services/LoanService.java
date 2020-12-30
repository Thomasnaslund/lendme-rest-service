package com.tomstry.LendMeApi.services;

import com.tomstry.LendMeApi.entities.Loan;
import com.tomstry.LendMeApi.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Collection findLoan(int id) {

        Loan e = loanRepository.findById(id).orElse(null);
        return Collections.emptyList();
    }

    public Collection<Loan> getUpcomingDeadlines() {
       Collection<Loan> loans = loanRepository.findTop10OrderByDeadline().orElse(Collections.emptyList());
       return loans;
    }


    public Loan updateLoan(Loan loan) {

        if (loan == null || loan.getId() == null) {
            return null;
        }


        return loanRepository.save(loan);

    }
}
