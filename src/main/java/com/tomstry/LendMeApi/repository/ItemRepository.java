package com.tomstry.LendMeApi.repository;

import com.tomstry.LendMeApi.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByTitle(String title);

    @Query("SELECT p FROM Item p WHERE CONCAT(p.title, ' ', p.description, ' ', p.price) LIKE %?1%")
    Optional<List<Item>> search(String keyword);

    Optional<List<Item>> findTop5ByTitleContaining(String subString);

}

