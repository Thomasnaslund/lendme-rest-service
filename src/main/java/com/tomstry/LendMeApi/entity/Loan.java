package com.tomstry.LendMeApi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.apache.catalina.authenticator.Constants;

import javax.persistence.*;
import javax.validation.Valid;
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

    @OneToMany(mappedBy = "loan", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @Valid
    private Set<ItemLoan> itemLoans = new HashSet<>();

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
