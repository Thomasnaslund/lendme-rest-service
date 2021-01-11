package com.tomstry.LendMeApi.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name= "items")
public class Item {

    public Item (String title, String description, BigDecimal price, Person owner) {
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
    private String title;

    @Column(name = "info")
    private String description;

    @Column(name = "cost")
    private BigDecimal price;

    @OneToMany(mappedBy = "item")
    private Set<ItemLoan> loans = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "owner_id", nullable = false)
    private Person owner;

}
