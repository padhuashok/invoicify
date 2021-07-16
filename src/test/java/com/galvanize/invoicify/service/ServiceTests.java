package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.*;
import com.galvanize.invoicify.dto.ItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isA;
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

    ItemDto itemdto1;
    ItemDto itemdto2;
    List<ItemDto> dtoitems;
    Item item1;
    Item item2;
    List<Item> itemList;
    Invoice invoice;
    List<InvoiceItem> invoiceItemList;
    InvoiceItem invoiceItem;
    InvoiceItem invoiceItem2;
    List<ItemDto> itemDtoList;

    @BeforeEach
    void setup(){
        itemdto1 = new ItemDto("Dev Items", 1, true, 25);
        itemdto2 = new ItemDto("Dev Items More", 2, false, 2.3);
        //create request to call api to create and check result
        dtoitems = new ArrayList<>();
        dtoitems.add(itemdto1);
        dtoitems.add(itemdto2);
        item1 = new Item(itemdto1);
        item2 = new Item(itemdto2);
        itemList = Arrays.asList(item1, item2);
        invoice = new Invoice();
        invoice.setId(1L);
        item1.setId(1L);
        item2.setId(2L);
        invoiceItemList = new ArrayList<>();
        invoiceItem = new InvoiceItem(item1, invoice);
        invoiceItem2 = new InvoiceItem(item2, invoice);
        invoiceItemList.add(invoiceItem);
        invoiceItemList.add(invoiceItem2);
        itemDtoList = Arrays.asList(itemdto1, itemdto2);
    }
    @Test
    void addItemtoInvoice() throws Exception {
        when(itemservice.saveItems(itemDtoList)).thenReturn(itemList);
        List<Item> actualItems = itemservice.saveItems(itemDtoList);
        assertEquals(itemList, actualItems);
        when(invoiceService.saveInvoice(invoice)).thenReturn(invoice);
        Invoice actualInvoice = invoiceService.saveInvoice(invoice);
        assertEquals(invoice, actualInvoice);
        //call service to save invoiceInvoice
        when(invoiceItemService.saveInvoiceItem(itemList, invoice)).thenReturn(invoiceItemList);
        List<InvoiceItem> actuals = invoiceItemService.saveInvoiceItem(itemList, invoice);
        assertEquals(invoiceItemList, actuals);
    }

    @Test
    void createInvoice(){

    }
}
