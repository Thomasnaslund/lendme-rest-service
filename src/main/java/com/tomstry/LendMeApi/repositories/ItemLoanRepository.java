package com.tomstry.LendMeApi.repositories;

import com.tomstry.LendMeApi.entities.Item;
import com.tomstry.LendMeApi.entities.ItemLoan;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemLoanRepository extends JpaRepository<ItemLoan, Integer> {

}
