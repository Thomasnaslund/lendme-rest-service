package com.tomstry.LendMeApi.services;

import com.tomstry.LendMeApi.entities.Item;
import com.tomstry.LendMeApi.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;


    public void addItem(Item item) {

    }

    public List<Item> getAllItems() {
        return getAllItems();
    }

    public Item getItemByID(int id) {
     Item retrievedItem = itemRepository.findById(id).orElse(null);

     if (retrievedItem == null) return null;
     return retrievedItem;
    }

    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }

    public Item updateItem(Item itemToUpdate) {


    }
}

