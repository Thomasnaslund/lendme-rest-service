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

    @Valid
    @ManyToMany(mappedBy = "likedCourses", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private Set<Item> items = new HashSet<>();

    @ManyToOne
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
