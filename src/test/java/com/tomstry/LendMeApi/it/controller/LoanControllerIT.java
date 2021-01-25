package com.tomstry.LendMeApi.it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.repository.LoanRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
public class LoanControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void addLoanWithItemsShouldItemsReturnOKAndLoan() throws Exception {


/*
        mockMvc.perform(get("/api/v1/loan/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
*/
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

    /*
    private List<Loan> generateLoanWithItem(int amount) {


    }

     */

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