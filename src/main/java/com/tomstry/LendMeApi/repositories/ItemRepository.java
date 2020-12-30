package com.tomstry.LendMeApi.repositories;

import com.tomstry.LendMeApi.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByTitle(String title);

}

