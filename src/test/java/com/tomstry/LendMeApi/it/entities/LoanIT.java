package com.tomstry.LendMeApi.it.entities;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.repository.ItemRepository;
import com.tomstry.LendMeApi.repository.LoanRepository;
import com.tomstry.LendMeApi.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

@DataJpaTest
class LoanIT {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testRetrievingLenderFromLoan() {
        Person person = new Person("john", "travolta");
        Loan loan = generateLoan();
        loan.setLender(person);
        loan = loanRepository.saveAndFlush(loan);
        Loan storedLoan = loanRepository.findById(loan.getId()).orElseThrow();
        Assertions.assertTrue(storedLoan.getLender().getFullName().equals(person.getFullName()));
    }

    @Test
    public void testRetrievingItemFromLoan() {
        Person person = new Person("john", "travolta");
        Loan loan = generateLoan();
        loan.addItem(generateItem());
        loan = loanRepository.saveAndFlush(loan);
        Loan storedLoan = loanRepository.findById(loan.getId()).orElseThrow();
        Assertions.assertTrue(!storedLoan.getItems().isEmpty());
    }

    private Loan generateLoan() {
        Random rd = new Random();
        Loan loan = new Loan();
        loan.setLender(new Person(rd.nextInt() + "",rd.nextInt() + ""));
        loan.setTerms("mock");
        loan.setStart(ZonedDateTime.now());
        loan.setEnd(loan.getStart().plusDays(20));
        return loanRepository.save(loan);
    }

    private Item generateItem() {
        Random rd = new Random();
        Item item = new Item(
                rd.nextInt() + "item"
                , rd.nextInt() + "desc"
                , BigDecimal.valueOf(rd.nextInt())
                , new Person("mock", "mockery"));
        return itemRepository.save(item);
    }

}