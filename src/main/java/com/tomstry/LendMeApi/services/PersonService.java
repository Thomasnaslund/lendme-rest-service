package com.tomstry.LendMeApi.services;

import com.tomstry.LendMeApi.entities.Person;
import com.tomstry.LendMeApi.repositories.PersonRepository;
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

    public int deletePerson(int id) {
        personRepository.deleteById(id);
        return 1;
    }

    public Optional<Person> getPersonByName(String name) {
        return personRepository.findByFullName(name);
    }

    public Person updatePerson(Person person) {
        if (person == null);
        Person existingPerson = personRepository.findById(person.getId()).orElse(null);
        existingPerson.setEmail(person.getEmail());
        existingPerson.setFullName(person.getFullName());
        existingPerson.setId(person.getId());
        return personRepository.save(existingPerson);
    }


}
