package com.tomstry.LendMeApi.generator;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Random;

public class Generate {

    //Generators
    public static Loan newLoan() {
        Random rd = new Random();
        Loan loan = new Loan();
        loan.setBorrower(new Person(rd.nextInt() + "",rd.nextInt() + ""));
        loan.setTerms("mock");
        loan.setStart(ZonedDateTime.now());
        loan.setEnd(loan.getStart().plusDays(20));
        return loan;
    }

    public static Item newItem() {
        Random rd = new Random();
        Item item = new Item(
                rd.nextInt() + "item"
                , rd.nextInt() + "desc"
                , BigDecimal.valueOf(rd.nextInt())
                , new Person("mock", "mockery"));
        return item;
    }
}
