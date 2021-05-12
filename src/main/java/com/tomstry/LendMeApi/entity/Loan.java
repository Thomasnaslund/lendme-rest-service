package com.tomstry.LendMeApi.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @NotEmpty
    private Item item;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "borrower_id")
    @NotEmpty
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
/*
    public void setItem(Item item) {
        this.item = item;
        item.getLoans().add(this);
    }

    public void setBorrower(Person borrower) {
        this.borrower = borrower;
        borrower.getLoans().add(this);
    }

 */
}
