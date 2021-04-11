package com.tomstry.LendMeApi.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.apache.catalina.authenticator.Constants;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@RequiredArgsConstructor
@Getter
@Table(name= "loans")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Loan.class)
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToMany(mappedBy = "loans", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @NotEmpty
    private Set<Item> items = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "borrower_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Person borrower;

    @Column(name="start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    @NotNull
    private ZonedDateTime start;

    @Column(name="end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    @NotNull
    private ZonedDateTime end;

    @Column(name="terms")
    private String terms;

    public void addItem(Item item) {
        this.items.add(item);
        item.getLoans().add(this);
    }

}
