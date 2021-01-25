package com.tomstry.LendMeApi.service;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.exceptionhandler.LoanNotFoundException;
import com.tomstry.LendMeApi.exceptionhandler.OverlappingDateException;
import com.tomstry.LendMeApi.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;


    public Loan findLoan(int id) {
        Loan loan = loanRepository.findById(id).orElseThrow(LoanNotFoundException::new);
        return loan;
    }

    public Loan addLoan(Loan loan) throws OverlappingDateException {

        boolean loanIntervalOverlaps = loan.getItems().stream().anyMatch(l ->
                intervalOverlaps(loan, l)
        );

        if (loanIntervalOverlaps) throw new OverlappingDateException();
        //TODO Log here
        return loanRepository.save(loan);

    }

    /**
     * Checks if item has any other loans during provided loan period.
     * @param loan loan which item should be added to.
     * @param item to be added to loan, must at least contain id
     * @throws OverlappingDateException if loan interval overlaps other loans;
     */
    private boolean intervalOverlaps(Loan loan, Item item) throws OverlappingDateException {
        List<Loan> loans = (List<Loan>) loanRepository.findByItems_Id(item.getId())
                .orElse(Collections.emptyList());

        ZonedDateTime start = loan.getStart();
        ZonedDateTime end = loan.getEnd();

        boolean loanIntervalOverlap;
        loanIntervalOverlap = loans.stream().anyMatch(
                l -> !l.getEnd().isBefore(start) && !l.getStart().isAfter(end)
        );

        return loanIntervalOverlap;

    }

    public Collection<Loan> getComingDeadlines() {
       Collection loans = loanRepository.findTop10ByOrderByEnd().orElse(Collections.emptyList());
       return loans;
    }

    public Loan updateLoan(Loan loan) {
        return loanRepository.save(loan);
    }


    //TODO: add paging and sorting
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Collection<Item> getAllItemsForLoan(int id) {

        Loan loan = loanRepository.findById(id).orElseThrow(LoanNotFoundException::new);
        return loan.getItems();
    }

    public Item getItems(int id) {
    }
}
