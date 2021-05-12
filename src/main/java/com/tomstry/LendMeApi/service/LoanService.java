package com.tomstry.LendMeApi.service;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.exceptionhandler.EntityNotFoundException;
import com.tomstry.LendMeApi.exceptionhandler.OverlappingDateException;
import com.tomstry.LendMeApi.repository.ItemRepository;
import com.tomstry.LendMeApi.repository.LoanRepository;
import com.tomstry.LendMeApi.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoanService {

    Logger logger = LoggerFactory.getLogger(LoanService.class);

    @Autowired
    private LoanRepository loanDao;

    @Autowired
    private ItemRepository itemDao;

    @Autowired
    private PersonRepository personDao;

    public LoanService() {
    }

    public Loan findLoan(int id) {
        Loan loan = loanDao.findById(id).orElseThrow(() -> new EntityNotFoundException(Loan.class));
        return loan;
    }

    public Loan addLoan(Loan loan) {
        loan.setBorrower(fetchBorrower(loan.getBorrower().getId()));
        loan.setItem(fetchItem(loan.getItem().getId()));
        if (isItemBooked(loan)) {
            logger.warn("Item with id: " + loan.getItem().getId() + " is already booked");
            throw new OverlappingDateException(loan.getItem().getId());
        }
        return loanDao.save(loan);
    }

    private Person fetchBorrower (int id) {
        return personDao.findById(id).orElseThrow(
                () -> new EntityNotFoundException(Person.class)
        );
    }

    public boolean isItemBooked(Loan loan) {
        List<Loan> loans = (List<Loan>) loanDao.findByItem_Id(loan.getItem().getId())
                .orElse(Collections.emptyList());
        return loans.stream().anyMatch(l -> hasOverlappingDates(l, loan));

    }
    public static boolean hasOverlappingDates(Loan l1, Loan l2) {
        return !l2.getEnd().isBefore(l1.getStart()) && !l2.getStart().isAfter(l1.getEnd());
    }

    public Loan updateLoan(int id, Loan loan) {
        return loanDao.findById(id).map(l -> {
            l.setTerms(loan.getTerms());
            l.setStart(loan.getStart());
            l.setEnd(loan.getEnd());
            return loanDao.save(l);
        }).orElseThrow(() -> new EntityNotFoundException(Loan.class));
    }

    public Loan updateLender(int id, Person person) {
        return loanDao.findById(id).map(l -> {
            l.setBorrower(person);
            return loanDao.save(l);
        }).orElseThrow(() -> new EntityNotFoundException(Loan.class));
    }

    public Item fetchItem(int id) throws OverlappingDateException{
        Item item = itemDao.findById(id).orElseThrow(() -> new EntityNotFoundException(Item.class));
        return item;
    }

    //TODO: add paging and sorting
    public List<Loan> getAllLoans() {
        return loanDao.findAll();
    }
}
