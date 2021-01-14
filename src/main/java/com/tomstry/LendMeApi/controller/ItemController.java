package com.tomstry.LendMeApi.controller;
import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/v1/item")
@RestController
@Validated
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
    public Item updateItem(@RequestBody @Valid Item itemToUpdate) {
        return itemService.updateItem(itemToUpdate);
    }
}



