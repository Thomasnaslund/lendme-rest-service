package com.tomstry.LendMeApi.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
public class ItemLoanKey implements Serializable {

    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "loan_id")
    private Integer loanId;

}
