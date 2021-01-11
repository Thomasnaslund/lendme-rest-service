package com.tomstry.LendMeApi.entity;

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

    @Id
    @GeneratedValue
    public Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer amount;

}
