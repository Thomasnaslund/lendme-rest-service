package com.tomstry.LendMeApi.services;

import com.tomstry.LendMeApi.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository personRepository;



}

