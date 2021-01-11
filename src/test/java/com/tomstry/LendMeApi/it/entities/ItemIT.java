package com.tomstry.LendMeApi.it.entities;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.repository.ItemRepository;
import com.tomstry.LendMeApi.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.math.BigDecimal;

@DataJpaTest
class ItemIT {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PersonRepository personRepository;

    private static final String itemTestName = "dum";
    private static final String itemTestDesc = "dum grej lizom";

    @Test
    @DisplayName("Saving item with owner should not throw exceptions")
    public void saveItemWithOwnerShouldNotThrowException() throws Exception {
        Assertions.assertDoesNotThrow(() -> {
            Person person = personRepository.saveAndFlush(new Person("Kent", "Benni@k.se"));
            Item item = new Item(itemTestName, itemTestDesc,BigDecimal.valueOf(20), person);
            Item savedEntity = itemRepository.save(item);
            itemRepository.flush();
        });
    }

    @Test
    @DisplayName("flushing item without owner should throw DataIntegrity Exception")
    public void saveItemWithNullOwnerShouldThrowException() throws Exception {
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            Item item = new Item(itemTestName, itemTestDesc,BigDecimal.valueOf(20),null);
            Item savedEntity = itemRepository.save(item);
            itemRepository.flush();
        });
    }


    @Test()
    @DisplayName("Should find Item by title")
    public void findPersonByTitleShouldWork() {
        Person person = personRepository.saveAndFlush(new Person("Kent", "Benni@k.se"));
        Item item = new Item(itemTestName, itemTestDesc,BigDecimal.valueOf(20), person);
        Item savedEntity = itemRepository.saveAndFlush(item);
        Assertions.assertEquals(savedEntity.getId(), itemRepository.findByTitle(itemTestName).orElse(null).getId());
    }

}