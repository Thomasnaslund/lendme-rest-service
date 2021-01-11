package com.tomstry.LendMeApi.it.entities;

import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PersonIT {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testSavePerson() {
        Person person = new Person("Kent", "Benni@k.se");
        personRepository.save(person);
    }

}