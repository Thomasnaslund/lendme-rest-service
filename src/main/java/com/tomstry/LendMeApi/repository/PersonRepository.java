package com.tomstry.LendMeApi.repository;

import com.tomstry.LendMeApi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByFullName(String fullName);
}
