package com.tomstry.LendMeApi.it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.generator.Generate;
import com.tomstry.LendMeApi.repository.ItemRepository;
import com.tomstry.LendMeApi.repository.LoanRepository;
import com.tomstry.LendMeApi.repository.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
//TODO Add mock entities in before annotation init method
public class LoanControllerIT {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private LoanRepository loanRepository;

    @Autowired private ItemRepository itemRepository;

    @Autowired private PersonRepository personRepository;

    private Item seededLoanItem;
    private Loan seededLoan;
    private Person seededLender;

    @Before
    public void init () {
        seededLoanItem = itemRepository.save(Generate.newItem());
        seededLoan = loanRepository.save(Generate.newLoan());
        seededLender = personRepository.save(new Person("a", "a"));

        seededLoan.addItem(seededLoanItem);
        seededLoan.setLender(seededLender);
        seededLoan = loanRepository.save(seededLoan);
    }

    @Test
    public void addLoanWithItemsShouldItemsReturnOKAndLoan() throws Exception {
        Loan loan = Generate.newLoan();
        loan.getItems().add(Generate.newItem());
        loan.getItems().add(Generate.newItem());
        mockMvc.perform(get("/api/v1/loan/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddingItemToLoanShouldReturnCreated() throws Exception {
        Item itemToBePosted = Generate.newItem();
        itemToBePosted = itemRepository.save(itemToBePosted);
        String ItemPost = objectMapper.writeValueAsString(itemToBePosted);

         mockMvc.perform(post("/api/v1/loan/"+ seededLoan.getId()+"/items")
                .contentType(MediaType.APPLICATION_JSON).content(ItemPost))
                .andExpect(status().isCreated());
    }

    @Test
    public void testPostNewLoanWithAlreadyBookedItemShouldReturn400() throws Exception {
        Item newItem = new Item();
        newItem.setId(seededLoanItem.getId());
        Loan newLoan = Generate.newLoan();
        newLoan.getItems().add(newItem);
        newLoan.setLender(new Person("a", "a"));
        newLoan.setStart(seededLoan.getStart().minusDays(2));
        newLoan.setEnd(seededLoan.getStart().plusDays(1));

        String va = objectMapper.writeValueAsString(newLoan);

        mockMvc.perform(post("/api/v1/loan")
                .contentType(MediaType.APPLICATION_JSON).content(va))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Item with Id 1 has other loans that intersects this one"));
    }

    @Test
    public void testPostBookedItemToLoanShouldReturn400() throws Exception {
        Item newItem = Generate.newItem();
        Loan newLoan = Generate.newLoan();
        newItem.setId(seededLoanItem.getId());
        newLoan.setStart(seededLoan.getStart().minusDays(2));
        newLoan.setEnd(seededLoan.getStart().plusDays(1));
        loanRepository.saveAndFlush(newLoan);

        String va = objectMapper.writeValueAsString(newItem);

        mockMvc.perform(post("/api/v1/loan/"+newLoan.getId()+"/items")
                .contentType(MediaType.APPLICATION_JSON).content(va))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Item with Id 1 has other loans that intersects this one"));
    }

}