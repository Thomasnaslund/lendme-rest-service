package com.tomstry.LendMeApi.it.controllers;

import com.tomstry.LendMeApi.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest

public class ItemControllerIT {


    @Autowired
    private ItemService itemService;

    @Test
    void addItem() {

    }

    @Test
    void getAllItems() {
    }

    @Test
    void getItemById() {
    }

    @Test
    void deleteItemById() {
    }

    @Test
    void updateItem() {
    }
}
