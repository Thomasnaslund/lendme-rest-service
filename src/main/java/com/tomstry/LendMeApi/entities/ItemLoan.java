package com.tomstry.LendMeApi.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name= "loanItems")
public class ItemLoan {

    @EmbeddedId
    private ItemLoanKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("LoanId")
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ItemId")
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer amount;

}
