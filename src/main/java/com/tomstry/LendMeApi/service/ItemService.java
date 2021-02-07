package com.tomstry.LendMeApi.service;

import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.entity.Loan;
import com.tomstry.LendMeApi.exceptionhandler.EntityNotFoundException;
import com.tomstry.LendMeApi.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Item addItem(Item item) {
           item.setOwner(item.getOwner());
            return itemRepository.save(item);
    }

    public List<Item> getAllItems() {
         List items = itemRepository.findAll();
         return items;
    }

    public Item getItemByID(int id) {
     Item retrievedItem = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Item.class));
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

