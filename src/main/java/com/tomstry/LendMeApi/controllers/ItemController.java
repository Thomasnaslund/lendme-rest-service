package com.tomstry.LendMeApi.controllers;
import com.tomstry.LendMeApi.entities.Item;
import com.tomstry.LendMeApi.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/item")
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public void addItem(@RequestBody @NonNull Item item) {
        itemService.addItem(item);
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping(path = "/{id}")
    public Item getItemById(@PathVariable int id) {
        return itemService.getItemByID(id);
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable int id) {
        itemService.deleteItem(id);
    }

    @PutMapping("/{id}")
    public Item updateItem(@RequestBody @Valid @NonNull Item itemToUpdate) {
        return itemService.updateItem(itemToUpdate);
    }
}

}

