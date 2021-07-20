package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.FlatFee;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.domain.RateFee;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    public Item saveItem(Item myitem) {
       return  itemRepository.save(myitem);
    }

    public List<Item> saveItems(List<ItemDto> items) {
        List<Item> itemList=items.stream().map(e ->{
            return new Item(e);
        })
                .collect(Collectors.toList());
        return itemRepository.saveAll(itemList);
    }

    public void deleteByIds(List<Long> itemIds) {
        System.out.println("delete item");
        itemRepository.deleteAllById(itemIds);
        System.out.println("delete item complete");
    }
}
