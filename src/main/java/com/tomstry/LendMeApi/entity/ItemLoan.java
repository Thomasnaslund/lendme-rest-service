package com.tomstry.LendMeApi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Data
@Table(name= "loanItems")
@NoArgsConstructor
public class ItemLoan {

    public ItemLoan(Loan loan, Item item, Integer amount) {
        setLoan(loan);
        setItem(item);
        setAmount(amount);
    }

    @Id
    @GeneratedValue
    public Integer id;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    @JsonIgnore
    private Loan loan;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    @Valid
    private Item item;

    private Integer amount = 1;

}
