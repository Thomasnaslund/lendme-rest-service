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
import org.springframework.transaction.annotation.Transactional;

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
        addItems(loan, loan.getItems());
        loan.setLender(loan.getLender());
        return loanDao.save(loan);
    }

    /**
     * Checks if item has any other loans during provided loan period.
     *
     * @param newLoan loan which item should be added to.
     * @param item to be added to loan, must at least contain id
     * @throws OverlappingDateException if item is already booked during loean period;
     */
    public boolean isBooked(Loan newLoan, Item item) {
        List<Loan> loans = (List<Loan>) loanDao.findByItems_Id(item.getId())
                .orElse(Collections.emptyList());

        return loans.stream().anyMatch(l -> hasOverlappingDates(l, newLoan));

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
            l.setLender(person);
            return loanDao.save(l);
        }).orElseThrow(() -> new EntityNotFoundException(Loan.class));
    }

    public Item addItemToExistingLoan(int loanId, Item item) {
        Loan loan = loanDao.findById(loanId).orElseThrow(() -> new EntityNotFoundException(Loan.class));
        return addItem(loan, item);
    }

    public Item addItem(Loan loan, Item item) {
        //TODO should be changed if allowed to add non Existing Items
        item = itemDao.findById(item.getId()).orElseThrow(() -> new EntityNotFoundException(Item.class));
        if (isBooked(loan, item)) {
            logger.warn("Item with id: " + item.getId() + " is already booked");
            throw new OverlappingDateException(item.getId());
        }
        loan.addItem(item);
        loanDao.save(loan);
        return item;
    }

    @Transactional
    public Collection<Item> addItems(Loan loan, Collection<Item> items) {
       items.stream().forEach(item -> addItem(loan,item));
        return items;
    }

    //TODO: add paging and sorting
    public List<Loan> getAllLoans() {
        return loanDao.findAll();
    }

    public Collection<Item> getAllItemsForLoan(int id) {
        Loan loan = loanDao.findById(id).orElseThrow(() -> new EntityNotFoundException(Loan.class));
        return loan.getItems();
    }

    public Collection<Item> getItems(int id) {
        return loanDao.findById(id).map(l -> l.getItems()).orElseThrow(() -> new EntityNotFoundException(Loan.class));
    }
}
