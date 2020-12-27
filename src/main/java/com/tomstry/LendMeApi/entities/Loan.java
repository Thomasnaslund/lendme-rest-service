package com.tomstry.LendMeApi.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Table(name= "loans")

public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    private List<ItemLoan> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "lender_id")
    private Person lender;

    @Column(name="deadline")
    private Timestamp deadline;

    @Column(name="issued")
    private Timestamp issued;

    @Column(name="terms")
    private String terms;

}
