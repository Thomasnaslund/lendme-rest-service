package com.tomstry.LendMeApi.entities;

import com.tomstry.LendMeApi.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class PersonTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testSavePerson() {
        Person person = new Person("Kent", "Benni@k.se");
        personRepository.save(person);
    }

}