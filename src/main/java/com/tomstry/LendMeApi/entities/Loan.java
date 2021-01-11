package com.tomstry.LendMeApi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@RequiredArgsConstructor
@Getter
@Table(name= "loans")

public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToMany(mappedBy = "loan", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Valid
    @NotEmpty
    private Set<ItemLoan> items = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lender_id")
    @NotEmpty
    private Person lender;

    @Column(name="start")
    @NotNull
    private Timestamp start;

    @Column(name="end")
    @NotNull
    private Timestamp end;

    @Column(name="terms")
    private String terms;

}
