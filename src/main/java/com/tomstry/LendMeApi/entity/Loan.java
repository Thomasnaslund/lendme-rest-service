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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @ManyToMany(mappedBy = "loans", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Item> items = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "lender_id")
    private Person lender;

    @Column(name="start")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime start;

    @Column(name="end")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime end;

    @Column(name="terms")
    private String terms;

    public void addItem(Item item) {
        this.items.add(item);
        item.getLoans().add(this);
    }

}
