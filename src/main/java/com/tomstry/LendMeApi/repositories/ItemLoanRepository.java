package com.tomstry.LendMeApi.repositories;

import com.tomstry.LendMeApi.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemLoanRepository extends JpaRepository<Item, Integer> {

}
