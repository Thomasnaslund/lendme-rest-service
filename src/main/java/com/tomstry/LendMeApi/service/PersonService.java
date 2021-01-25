package com.tomstry.LendMeApi.service;

import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.exceptionhandler.LoanNotFoundException;
import com.tomstry.LendMeApi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    public List<Person> addPeople(List<Person> people) {
        return personRepository.saveAll(people);
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public Optional<Person> getPersonByID(int id) {
        Person person = personRepository.findById(id).orElse(null);
        return Optional.ofNullable(person);
    }

    public void deletePerson(int id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> getPersonByName(String name) {
        return personRepository.findByFullName(name);
    }

    public Person updatePerson(int id, Person newPerson) {
        return personRepository.findById(id).map(p -> {
                    p.setEmail(newPerson.getEmail());
                    p.setFullName(newPerson.getFullName());
                    return personRepository.save(p);
                }
        ).orElseThrow(LoanNotFoundException::new);
    }


}
