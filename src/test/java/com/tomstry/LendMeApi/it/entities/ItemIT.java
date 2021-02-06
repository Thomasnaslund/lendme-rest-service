package com.tomstry.LendMeApi.it.entities;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.exceptionhandler.LoanNotFoundException;
import com.tomstry.LendMeApi.repository.ItemRepository;
import com.tomstry.LendMeApi.repository.LoanRepository;
import com.tomstry.LendMeApi.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Random;

@DataJpaTest
class ItemIT {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LoanRepository loanRepository;


    @Test
    @DisplayName("Saving item with owner should not throw exceptions")
    public void saveItemWithOwnerShouldNotThrowException() throws Exception {
        Assertions.assertDoesNotThrow(() -> {
            Person person = personRepository.saveAndFlush(new Person("Kent", "Benni@k.se"));
            Item item = generateItem();
            item.setOwner(person);
            itemRepository.saveAndFlush(item);
        });
    }

    @Test
    public void testRetrievingLoanFromItem() {
        Loan loan = generateLoan();
        Item item = generateItem();
        loan.addItem(item);
        loanRepository.save(loan);

        Item storedItem = itemRepository.findById(item.getId()).orElseThrow(LoanNotFoundException::new);
        Assertions.assertTrue(!storedItem.getLoans().isEmpty());
    }

    @DisplayName("flushing item without owner should throw DataIntegrity Exception")
    public void saveItemWithNullOwnerShouldThrowException() throws Exception {
            Item item = new Item("test", "test",BigDecimal.valueOf(20),null);
           Assertions.assertThrows(DataIntegrityViolationException.class, () -> itemRepository.saveAndFlush(item));
    }

    //Generators
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