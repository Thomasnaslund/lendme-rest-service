package com.tomstry.LendMeApi.repository;

import com.tomstry.LendMeApi.entity.ItemLoan;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemLoanRepository extends JpaRepository<ItemLoan, Integer> {

}
