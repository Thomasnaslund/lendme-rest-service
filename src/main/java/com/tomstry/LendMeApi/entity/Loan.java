package com.tomstry.LendMeApi.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.HashSet;
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
    @Valid
    private Person lender;

    @Column(name="start")
    private ZonedDateTime start;

    @Column(name="end")
    private ZonedDateTime end;

    @Column(name="terms")
    private String terms;

}
