package com.tomstry.LendMeApi.it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.repository.ItemRepository;
import com.tomstry.LendMeApi.repository.LoanRepository;
import com.tomstry.LendMeApi.repository.PersonRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoanControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void addLoanWithItemsShouldItemsReturnOKAndLoan() throws Exception {

        Loan loan = generateLoans(1).get(0);
        loan.getItems().add(generateItems(1).get(0));
        loan.getItems().add(generateItems(1).get(0));
        mockMvc.perform(get("/api/v1/loan/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddingItemToLoanShouldReturnCreated() throws Exception {
        Person person = new Person("john", "travolta");
        person = personRepository.save(person);
        Loan loan = generateLoans(1).get(0);
        loan.setLender(person);
        loan = loanRepository.save(loan);
        Item item = generateItems(1).get(0);
        item = itemRepository.save(item);

        Item itemToBePosted = new Item();
        itemToBePosted.setId(item.getId());

        String ItemPost = objectMapper.writeValueAsString(itemToBePosted);

         mockMvc.perform(post("/api/v1/loan/"+loan.getId()+"/items")
                .contentType(MediaType.APPLICATION_JSON).content(ItemPost))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostLoanWithBookedItemShouldReturn400() throws Exception {
        Item item = generateItems(1).get(0);
        item = itemRepository.save(item);
        Loan loan = generateLoans(1).get(0);
        loan.getItems().add(item);
        loan = loanRepository.save(loan);

        Item newItem = generateItems(1).get(0);
        Loan newLoan = generateLoans(1).get(0);
        newLoan.getItems().add(newItem);
        newLoan.setStart(loan.getStart().minusDays(2));
        newLoan.setEnd(loan.getStart().plusDays(1));

        String va = objectMapper.writeValueAsString(newLoan);

        mockMvc.perform(post("/api/v1/loan")
                .contentType(MediaType.APPLICATION_JSON).content(va))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Item with Id 1 has other loans that intersects this one"));
    }

    @Test
    public void testPostBookedItemToLoanShouldReturn400() throws Exception {
        Item item = generateItems(1).get(0);
        item = itemRepository.save(item);
        Loan loan = generateLoans(1).get(0);
        loan.getItems().add(item);
        loan = loanRepository.save(loan);

        Item newItem = generateItems(1).get(0);
        newItem.setId(loan.getId());
        Loan newLoan = generateLoans(1).get(0);

        //This time overlap another booked time for this item
        newLoan.setStart(loan.getStart().minusDays(2));
        newLoan.setEnd(loan.getStart().plusDays(1));
        loanRepository.save(newLoan);

        String va = objectMapper.writeValueAsString(newItem);

        mockMvc.perform(post("/api/v1/loan/"+newLoan.getId()+"/items")
                .contentType(MediaType.APPLICATION_JSON).content(va))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Item with Id 1 has other loans that intersects this one"));

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

    private List<Loan> generateLoans(int amount) {
        List<Person> mockLenders = generatePeople(amount);
        List<Loan> loans = new ArrayList();
        for (int i = 0; i < mockLenders.size(); i++) {
            Random rd = new Random();
            Loan loan = new Loan();
            loan.setLender(mockLenders.get(i));
            loan.setTerms("Dumbo jumbo");
            loan.setStart(ZonedDateTime.now());
            loan.setEnd(loan.getStart().plusDays(10));
            loans.add(loan);
        }
        return loans;
    }

    private List<Item> generateItems(int amount) {
        List<Person> generatedPeople = generatePeople(amount);
        List<Item> itemDetails = new ArrayList();
        for (int i = 0; i < generatedPeople.size(); i++) {
            Random rd = new Random();
            Item item2 = new Item(
                    i + " " + rd.nextInt() + "st item"
                    , rd.nextInt() + " desc"
                    , BigDecimal.valueOf(rd.nextInt())
                    , generatedPeople.get(i)
            );
            itemDetails.add(item2);
        }
        return itemDetails;
    }
}