package com.tomstry.LendMeApi.controller;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

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

    @GetMapping(path = "/{id}/items")
    public Collection<Item> getAllItemsForLoan(@PathVariable int id) {
        return loanService.getAllItemsForLoan(id);
    }

    @PostMapping(path = "/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Item addItemToLoan(@PathVariable int id, @RequestBody Item item) {
        logger.info("New item received");
        return loanService.addItemToExistingLoan(id, item);
    }

    @GetMapping(path = "/{id}")
    public Loan getLoanById(@PathVariable int id) {
        return loanService.findLoan(id);
    }

    @PutMapping(path = "/{id}")
    public Loan updateLoan(@PathVariable int id, @RequestBody @Valid Loan loanToUpdate) {
        return loanService.updateLoan(id, loanToUpdate);
    }
}
