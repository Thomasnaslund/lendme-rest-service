package com.tomstry.LendMeApi.controller;

import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/person")
@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public void addPerson(@RequestBody @NonNull Person person) {
        personService.addPerson(person);
    }

    @GetMapping
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    @GetMapping(path = "/{id}")
    public Person getPersonById(@PathVariable int id) {
        Person person = personService.getPersonByID(id).orElse(null);
        return person;
    }

    @DeleteMapping("/{id}")
    public void deletePersonById(@PathVariable int id) {
        personService.deletePerson(id);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@RequestBody @Valid @NonNull Person personToUpdate) {
        return personService.updatePerson(personToUpdate);
    }
}
