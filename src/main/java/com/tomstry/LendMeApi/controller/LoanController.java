package com.tomstry.LendMeApi.controller;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/loan")
public class LoanController {

    public Logger logger = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    private LoanService loanService;
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Loan addLoan(@RequestBody @Valid Loan loan) {
        Loan temp = loan;
        logger.info("New Loan received");
        Loan retrievedLoan = loanService.addLoan(temp);
        return retrievedLoan;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Loan> getAllLoans() {
        logger.info("Returning all loans to client");
        return loanService.getAllLoans();
    }

    public Loan newLoan() {
        Random rd = new Random();
        Loan loan = new Loan();
        loan.setId(rd.nextInt());
        loan.setBorrower(new Person(rd.nextInt() + "",rd.nextInt() + ""));
        loan.setItem(newItem());
        loan.setTerms("mock");
        loan.setStart(ZonedDateTime.now());
        loan.setEnd(loan.getStart().plusDays(20));
        return loan;
    }

    public Item newItem() {
        Random rd = new Random();
        Item item = new Item(
                rd.nextInt() + "item"
                , rd.nextInt() + "desc"
                , BigDecimal.valueOf(rd.nextInt())
                , new Person("mock", "mockery"));
        item.setId(rd.nextInt());
        return item;
    }

    @GetMapping(path = "/{id}")
    public Loan getLoanById(@PathVariable int id) {
        Loan loan = newLoan();
        return loan;
        //return loanService.findLoan(id);
    }

    @PutMapping(path = "/{id}")
    public Loan updateLoan(@PathVariable int id, @RequestBody @Valid Loan loanToUpdate) {
        return loanService.updateLoan(id, loanToUpdate);
    }
}
