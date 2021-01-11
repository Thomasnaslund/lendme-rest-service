package com.tomstry.LendMeApi.repository;

import com.tomstry.LendMeApi.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    Optional<Collection<Loan>> findTop10ByOrderByDeadline();

}