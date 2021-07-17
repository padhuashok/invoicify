package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}
