package com.tomstry.LendMeApi.service;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.exceptionhandler.LoanNotFoundException;
import com.tomstry.LendMeApi.exceptionhandler.OverlappingDateException;
import com.tomstry.LendMeApi.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
     *
     * @param loan loan which item should be added to.
     * @param item to be added to loan, must at least contain id
     * @throws OverlappingDateException if loan interval overlaps other loans;
     */
    private boolean intervalOverlaps(Loan loan, Item item) {
        List<Loan> loans = (List<Loan>) loanRepository.findByItems_Id(item.getId())
                .orElse(Collections.emptyList());

        boolean loanIntervalOverlap;
        loanIntervalOverlap = loans.stream().anyMatch(
                l -> !l.getEnd().isBefore(loan.getStart()) && !l.getStart().isAfter(loan.getEnd())
        );
        return loanIntervalOverlap;
    }

    public Collection<Loan> getComingDeadlines() {
        Collection loans = loanRepository.findTop10ByOrderByEnd().orElse(Collections.emptyList());
        return loans;
    }

    public Loan updateLoan(int id, Loan loan) {
        return loanRepository.findById(id).map(l -> {
            l.setTerms(loan.getTerms());
            l.setStart(loan.getStart());
            l.setEnd(loan.getEnd());
            return loanRepository.save(l);
        }).orElseThrow(LoanNotFoundException::new);
    }

    public Loan updateLender(int id, Person person) {
        return loanRepository.findById(id).map(l -> {
            l.setLender(person);
            return loanRepository.save(l);
        }).orElseThrow(LoanNotFoundException::new);
    }

    public Loan addItem(int id, Item item) {
        Loan loan = loanRepository.findById(id).orElseThrow(LoanNotFoundException::new);
        if (intervalOverlaps(loan, item)) throw new OverlappingDateException();
        loan.getItems().add(item);
        return loanRepository.save(loan);
    }


    public Loan addItems(int id, List<Item> items) {
        Loan loan = loanRepository.findById(id).orElseThrow(LoanNotFoundException::new);
        boolean overlap = items.stream().anyMatch(i -> intervalOverlaps(loan, i));
        if (overlap) throw new OverlappingDateException();
        items.stream().forEach(i -> loan.getItems().add(i));
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

    public Collection<Item> getItems(int id) {
        return loanRepository.findById(id).map(l -> l.getItems()).orElseThrow(LoanNotFoundException::new);
    }
}
