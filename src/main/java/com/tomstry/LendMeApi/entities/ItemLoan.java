package com.tomstry.LendMeApi.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name= "loanItems")
@NoArgsConstructor
public class ItemLoan {

    public ItemLoan (Loan loan, Item item, Integer amount) {
        setLoan(loan);
        setItem(item);
        setAmount(amount);
    }

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
