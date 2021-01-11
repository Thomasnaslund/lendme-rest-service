package com.tomstry.LendMeApi.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name= "persons")
@NoArgsConstructor
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
    private String fullName;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "lender", cascade = CascadeType.ALL)
    private List<Loan> loans;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Item> items;

}
