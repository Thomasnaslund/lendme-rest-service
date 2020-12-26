package com.tomstry.LendMeApi.repositories;

import com.tomstry.LendMeApi.entities.Loan;
import com.tomstry.LendMeApi.entities.Person;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    Optional<Collection<Loan>> findTop10OrderByDeadline();


}