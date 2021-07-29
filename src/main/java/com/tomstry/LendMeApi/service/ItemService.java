package com.tomstry.LendMeApi.service;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.entity.Person;
import com.tomstry.LendMeApi.exceptionhandler.EntityNotFoundException;
import com.tomstry.LendMeApi.repository.ItemRepository;
import com.tomstry.LendMeApi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PersonRepository personRepository;

    public Item addItem(Item item) {
        Person owner = personRepository.findById(item.getOwner().getId()).orElseThrow(
                () -> new EntityNotFoundException(Item.class));
           item.setOwner(owner);
           return itemRepository.save(item);
    }

    public List<Item> getAllItems(Map<String,String> qparams) {
        List<Item> items = new ArrayList<>();
        qparams.forEach((key, value) -> {
            if (key == "keyword") {
                items.addAll(itemRepository.findTop5ByTitleContaining(value).orElse(Collections.emptyList()));
            }
        });
        return items;
    }

    public List<Item> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items;
    }

    public Item getItemByID(int id) {
     Item retrievedItem = itemRepository.findById(id).orElseThrow(
             () -> new EntityNotFoundException(Item.class));
     return retrievedItem;
    }

    public void deleteItem(int id) {
         itemRepository.deleteById(id);
    }

    public Item updateItem(int id, Item itemToUpdate) {
        return itemRepository.findById(id)
                .map(item -> {
                    item.setTitle(itemToUpdate.getTitle());
                    item.setDescription(itemToUpdate.getDescription());
                    item.setDescription(itemToUpdate.getDescription());
                    return itemRepository.save(item);
                }).orElseThrow(() -> new EntityNotFoundException(Item.class));
    }
}

