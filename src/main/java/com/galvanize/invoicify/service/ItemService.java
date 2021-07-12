package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.Item;
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
            Item item= new Item();
            item.setQuantity(e.getQuantity());
            item.setTotalFee(e.getFee());
            item.setDescription(e.getDescription());
            return item;
        })
                .collect(Collectors.toList());
        return itemRepository.saveAll(itemList);
    }
}
