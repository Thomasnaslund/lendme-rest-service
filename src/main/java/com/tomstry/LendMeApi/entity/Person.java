package com.tomstry.LendMeApi.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Setter
@RequiredArgsConstructor
@Getter
@Table(name= "persons")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Person.class)
public class Person {

    public Person (String name, String email) {
        setFullName(name);
        setEmail(email);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "full_name")
    @NotEmpty
    private String fullName;

    @Column(name = "email")
    @NotEmpty
    private String email;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
    private List<Loan> loans;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"owner", "loans"})
    private List<Item> items;
}
