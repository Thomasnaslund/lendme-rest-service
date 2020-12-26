package com.tomstry.LendMeApi.repositories;

import com.tomstry.LendMeApi.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByFullName(String fullName);
}
