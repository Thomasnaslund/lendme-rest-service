package com.tomstry.LendMeApi.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@RequiredArgsConstructor
@Getter
@Table(name= "items")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Item.class)
public class Item {

    public Item(String title, String description, BigDecimal price, Person owner) {
        setTitle(title);
        setDescription(description);
        setPrice(price);
        setOwner(owner);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    @NotBlank
    private String title;

    @NotBlank
    @Column(name = "info")
    private String description;

    @Column(name = "cost")
    private BigDecimal price;

    @OneToMany(mappedBy = "item", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Loan> loans = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Person owner;

}
