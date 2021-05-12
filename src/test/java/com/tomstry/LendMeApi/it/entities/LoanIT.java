package com.tomstry.LendMeApi.it.entities;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.generator.Generate;
import com.tomstry.LendMeApi.repository.ItemRepository;
import com.tomstry.LendMeApi.repository.LoanRepository;
import com.tomstry.LendMeApi.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


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
        Loan loan = loanRepository.save(Generate.newLoan());
        loan.setBorrower(person);
        loan = loanRepository.saveAndFlush(loan);
        Loan storedLoan = loanRepository.findById(loan.getId()).orElseThrow();
        Assertions.assertTrue(storedLoan.getBorrower().getFullName().equals(person.getFullName()));
    }

    @Test
    public void testRetrievingItemFromLoan() {
        Loan loan = Generate.newLoan();
        loan.setItem(itemRepository.save(Generate.newItem()));
        loan = loanRepository.saveAndFlush(loan);
        Loan storedLoan = loanRepository.findById(loan.getId()).orElseThrow();
        Assertions.assertNotNull(storedLoan.getItem());
    }
}