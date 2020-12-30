package com.tomstry.LendMeApi.entities;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemLoan> loans = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Person owner;

}
