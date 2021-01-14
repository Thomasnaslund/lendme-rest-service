package com.tomstry.LendMeApi.controller;

import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.service.LoanService;
import com.tomstry.LendMeApi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.HttpConstraint;
import javax.validation.Valid;
import java.util.List;

@RestController
@ControllerAdvice
@RequestMapping("/api/v1/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public Loan addLoan(@RequestBody @Valid Loan loan) {
        Loan retrievedLoan = loanService.addLoan(loan);
        return retrievedLoan;
    }

    @GetMapping
    public List<Loan> getAllLoans() {
        return loanService.getAllLoans();
    }


}
