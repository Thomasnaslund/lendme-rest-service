package com.tomstry.LendMeApi.controllers;

import com.tomstry.LendMeApi.entities.Person;
import com.tomstry.LendMeApi.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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
        return personService.getPersonByID(id)
                .orElse(null);
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
