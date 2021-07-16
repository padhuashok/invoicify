package com.galvanize.invoicify.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.utils.InvoicifyStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureRestDocs

public class ApiTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

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

    @BeforeEach
    void setup() {
        itemdto1 = new ItemDto("Dev Items", 1, true, 25);
        itemdto2 = new ItemDto("Dev Items More", 2, false, 2.3);
        dtoitems = Arrays.asList(itemdto1, itemdto2);
        item1 = new Item(itemdto1);
        item2 = new Item(itemdto2);
        itemList = Arrays.asList(item1, item2);
        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setInvoiceTotal(25);
        item1.setId(1L);
        item2.setId(2L);
        invoiceItem = new InvoiceItem(item1, invoice);
        invoiceItem2 = new InvoiceItem(item2, invoice);
        invoiceItemList = Arrays.asList(invoiceItem, invoiceItem2);
    }
    @Test
    void addItemtoInvoice() throws Exception{
        when(itemservice.saveItems(anyList())).thenReturn(itemList);
        when(invoiceService.saveInvoice(isA(Invoice.class))).thenReturn(invoice);
        when(invoiceItemService.saveInvoiceItem(anyList(),isA(Invoice.class))).thenReturn(invoiceItemList);
        mvc.perform(post("/invoice/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(InvoicifyStringUtils.asJsonString(dtoitems)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("[0].item").value(invoiceItemList.get(0).getItem()))
                .andExpect(jsonPath("[0].invoice").value(invoiceItemList.get(0).getInvoice()))
                .andExpect(jsonPath("[1].item").value(invoiceItemList.get(1).getItem()))
                .andExpect(jsonPath("[1].invoice").value(invoiceItemList.get(1).getInvoice()))
                .andDo((document("Add Items to Invoice POST", responseFields(
                fieldWithPath("[]").description("One or more line items of invoice"),
                fieldWithPath("[].id").description("Invoice Item ID"),
                fieldWithPath("[].item.id").description("Internal ID of Items"),
                fieldWithPath("[].item.description").description("Description of line item for invoice"),
                fieldWithPath("[].item.quantity").description("Number of persons involved for the work"),
                fieldWithPath("[].item.totalFee").description("Identifies the type of cost ( flat/rate based)"),
                fieldWithPath("[].item.invoiceItems").description("Flat fee Amount charged for an item "),
                fieldWithPath("[].invoice.id").description("Rate per person involved in the work "),
                fieldWithPath("[].invoice.invoiceItems").description("Amount for each person involved"),
                fieldWithPath("[].invoice.invoiceTotal").description("Rate per person involved in the work ")))));
    }

    @Test
    public void createInvoice(){


    }
}