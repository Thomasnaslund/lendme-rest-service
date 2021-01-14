package com.tomstry.LendMeApi.it.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomstry.LendMeApi.controller.LoanController;
import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.ItemLoan;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.service.LoanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(LoanController.class)
public class LoanControllerIT {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private LoanService loanService;

    @Test
    public void addLoanWithShouldItemsReturnBadRequest() throws Exception {

        Loan mockLoan = new Loan();
        Item item = generateItems(1).get(0);


        ItemLoan e = new ItemLoan();
        e.setItem(item);
        e.setAmount(10);
        mockLoan.getItemLoans().add(e);
        mockLoan.setStart(ZonedDateTime.now());
        mockLoan.setEnd(mockLoan.getStart().plusDays(10));
        Person john_dumbo = new Person("", "Dumbo@live.com");
        mockLoan.setLender(john_dumbo);

        String content = objectMapper.writeValueAsString(mockLoan);


        mockMvc.perform(post("/api/v1/loan/")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

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