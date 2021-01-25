package com.tomstry.LendMeApi.controller;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Loan addLoan(@RequestBody @Valid Loan loan) {
        Loan retrievedLoan = loanService.addLoan(loan);
        return retrievedLoan;
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping(path = "/{id}/items")
    public Collection<Item> getAllItemsForLoan(@PathVariable int id) {
        return loanService.getAllItemsForLoan(id);
    }

    @PostMapping(path = "/{id}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Collection<Item> addItemToLoan(@PathVariable int id, @RequestBody @Valid Item item) {
        return loanService.getItems(id);
    }

    @GetMapping(path = "/{id}")
    public Loan getLoanById(@PathVariable int id) {
        return loanService.findLoan(id);
    }

    @PutMapping(path = "/{id}")
    public Loan UpdateLoan(@PathVariable int id, @RequestBody @Valid Loan loanToUpdate) {
        return loanService.updateLoan(id, loanToUpdate);
    }

}
