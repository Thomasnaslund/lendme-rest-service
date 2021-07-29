package com.tomstry.LendMeApi.controller;
import com.tomstry.LendMeApi.entity.Item;
import com.tomstry.LendMeApi.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/item")
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@RequestBody @Valid Item item) {
        Item itemDto = item;
        System.out.println(item.getOwner().getId());
        itemService.addItem(itemDto);
    }

    @GetMapping
    public List<Item> getAllItems(@RequestParam(required=false) Map<String,String> qparams) {
        List<Item> items = new ArrayList<>();
        if (qparams != null && !qparams.isEmpty()) {
            items.addAll(itemService.getAllItems(qparams));
        } else {
            items.addAll(itemService.getAllItems());
        }

        return items;
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
    public Item updateItem(@PathVariable int id, @RequestBody @Valid Item itemToUpdate) {
        return itemService.updateItem(id, itemToUpdate);
    }
}



