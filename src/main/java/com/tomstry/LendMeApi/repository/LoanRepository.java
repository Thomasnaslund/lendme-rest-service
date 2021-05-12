package com.tomstry.LendMeApi.repository;

import com.tomstry.LendMeApi.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    Optional<Collection<Loan>> findTop10ByOrderByEnd();

    Optional<Collection<Loan>> findByItem_Id(Integer id);
}