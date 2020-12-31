package com.tomstry.LendMeApi.it.entities;

import com.tomstry.LendMeApi.entities.Item;
import com.tomstry.LendMeApi.entities.Loan;
import com.tomstry.LendMeApi.entities.Person;
import com.tomstry.LendMeApi.repositories.ItemRepository;
import com.tomstry.LendMeApi.repositories.LoanRepository;
import com.tomstry.LendMeApi.repositories.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@DataJpaTest
class LoanIT {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void getComingDeadlinesShouldReturnTopTenDeadlines() {

        List<Loan> mockLoans;
        mockLoans = generateLoans(30);
        loanRepository.saveAll(mockLoans);
        loanRepository.flush();
        List<Loan> retrievedLoan = (List<Loan>) loanRepository.findTop10OrderByDeadline().orElse(Collections.emptyList());
        Assertions.assertEquals(retrievedLoan.get(1).getDeadline(),mockLoans.get(1).getDeadline());

    }


    private List<Loan> generateLoans(int amount) {
        List<Person> mockLenders = generatePeople(amount);
        List<Loan> loans = new ArrayList();
        for (int i = 0; i < mockLenders.size(); i++) {
            Random rd = new Random();
            Loan loan = new Loan();
            Timestamp issued = nextDate();
            Timestamp deadline = addDays(rd.nextInt(200), issued);
            loan.setLender(mockLenders.get(i));
            loan.setTerms("Dumbo jumbo");
            loan.setIssued(nextDate());
            loan.setDeadline(deadline);
            loans.add(loan);
        }
        return loans;

    }


    private Long dayToMiliseconds(int days){
        Long result = Long.valueOf(days * 24 * 60 * 60 * 1000);
        return result;
    }

    public Timestamp addDays(int days, Timestamp t1){
        Long miliseconds = dayToMiliseconds(days);
        return new Timestamp(t1.getTime() + miliseconds);
    }

    private List<Person> generatePeople(int amount) {
        List<Person> people = new ArrayList();

        for (int i = 0; i < amount; i++) {
            Random rd = new Random();
            Person person = new Person((i + " " + rd.nextInt()) + "st person", rd.nextInt() + " email");
            people.add(person);
        }
        return people;
    }

    private Timestamp nextDate() {
        Random rd = new Random();
        int starDate = rd.nextInt(2050-1900)+1900;
        long offset = Timestamp.valueOf(starDate + "-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf((starDate + 5) + "-01-01 00:00:00").getTime();
        long diff = end - offset + 1;
        Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
        return rand;
    }

    private List<Item> generateItems(int amount) {
        List<Person> generatedPeople = generatePeople(amount);
        List<Item> items = new ArrayList();
        for (int i = 0; i < generatedPeople.size(); i++) {
            Random rd = new Random();
            Item item = new Item(
                    i + " " + rd.nextInt() + "st item"
                    , rd.nextInt() + " desc"
                    , BigDecimal.valueOf(rd.nextInt())
                    , generatedPeople.get(i)
            );
            items.add(item);
        }
        return items;
    }

    /*
    private List<Item> generateItemLoans(int amount) {

        List<Item> generatedItems = generateItems(amount);
        List<Person> generatedLenders = generatePeople(amount);
        List<ItemLoan> itemsLoans = new ArrayList();
        for (int i = 0; i < generatedLenders.size(); i++) {
            itemsLoans.add(new ItemLoan());

        }
        return itemsLoans;
    }
*/

}