package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.*;
import com.galvanize.invoicify.dto.ItemDto;
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

    @MockBean
    CompanyService companyService;

    @Test
    void addItemtoInvoice() throws Exception {
        ItemDto itemdto1 = new ItemDto("Dev Items", 5, true, 2.3);
        ItemDto itemdto2 = new ItemDto("Dev Items More", 2, false, 2.3, 10);
        //create request to call api to create and check result
        List<ItemDto> dtoitems = new ArrayList<>();
        dtoitems.add(itemdto1);
        dtoitems.add(itemdto2);
        Item item1 = new Item(itemdto1);
        Item item2 = new Item(itemdto2);
        List<Item> itemList = Arrays.asList(item1, item2);
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        item1.setId(1L);
        item2.setId(2L);
        List<InvoiceItem> invoiceItemList = new ArrayList<>();
        InvoiceItem invoiceItem = new InvoiceItem(item1, invoice);
        InvoiceItem invoiceItem2 = new InvoiceItem(item2, invoice);
        invoiceItemList.add(invoiceItem);
        invoiceItemList.add(invoiceItem2);
        List<ItemDto> itemDtoList = Arrays.asList(itemdto1, itemdto2);
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
}
