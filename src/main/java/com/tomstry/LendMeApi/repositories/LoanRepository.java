package com.tomstry.LendMeApi.repositories;

import com.tomstry.LendMeApi.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    Optional<Collection<Loan>> findTop10OrderByDeadline();


}