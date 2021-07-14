package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.*;
import com.galvanize.invoicify.dto.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest
public class ServiceTests {

    @Autowired
    private MockMvc mvc;
    @MockBean
    InvoiceService invoiceService;

    @MockBean
    ItemService itemservice;

    @MockBean
    InvoiceItemService invoiceItemService;
    @Test
    void addItemtoInvoice() throws Exception {
        Invoice invoice = new Invoice();
        List<InvoiceItem> invoiceItems = new ArrayList<InvoiceItem>();
        ItemDto itemdto1 = new ItemDto("Dev Items", 5, true, 2.3);
        ItemDto itemdto2 = new ItemDto("Dev Items More", 2, false, 2.3, 10);
        Item item1 = new Item();
        Item item2 = new Item();
        item1.setDescription(itemdto1.getDescription());
        item1.setQuantity(itemdto1.getQuantity());
        itemdto1.setFee(new FlatFee(itemdto1.getAmountFlatFee()));
        item1.setTotalFee(itemdto1.getFee());

        item2.setDescription(itemdto1.getDescription());
        item2.setQuantity(itemdto1.getQuantity());
        itemdto2.setFee(new RateFee(itemdto2.getRateFee(), itemdto2.getQuantityFee()));
        item2.setTotalFee(itemdto1.getFee());

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);

        items.forEach(item -> {
                    InvoiceItem invoiceItem = new InvoiceItem(item, invoice);
                    invoiceItems.add(invoiceItem);
                }
        );
        //call service to save invoiceInvoice
        when(invoiceItemService.saveInvoiceItem(items, invoice)).thenReturn(invoiceItems);
    }
}
