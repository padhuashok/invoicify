package com.galvanize.invoicify.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.ItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doAnswer;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void addItemtoInvoice() throws Exception{
        ItemDto itemdto1=new ItemDto("Dev Items",5,true,2.3);
        ItemDto itemdto2 = new ItemDto("Dev Items More",2,false,2.3,10);
        //create request to call api to create and check result
        List<ItemDto> dtoitems=new ArrayList<>();
        dtoitems.add(itemdto1);
        dtoitems.add(itemdto2);
        Item item1=new Item(itemdto1);
        Item item2=new Item(itemdto2);
        List<Item> itemList= Arrays.asList(item1,item2);
        when(itemservice.saveItems(anyList())).thenReturn(itemList);
        Invoice invoice=new Invoice();
        when(invoiceService.saveInvoice(isA(Invoice.class))).thenReturn(invoice);
        invoice.setId(1L);
        item1.setId(1L);
        item2.setId(2L);
        List<InvoiceItem> invoiceItemList=new ArrayList<>();
        InvoiceItem invoiceItem=new InvoiceItem(item1,invoice);
        InvoiceItem invoiceItem2=new InvoiceItem(item2,invoice);
        invoiceItemList.add(invoiceItem);
        invoiceItemList.add(invoiceItem2);
        when(invoiceItemService.saveInvoiceItem(anyList(),isA(Invoice.class))).thenReturn(invoiceItemList);
//        doAnswer(invocation -> {
//            List<Item> items = invocation.getArgument(0);
//            Invoice invoice2=invocation.getArgument(1);
//            List<InvoiceItem> invoiceItems = new ArrayList<>();
//            AtomicLong id=new AtomicLong(1L);
//            items.forEach( item -> {
//                        InvoiceItem invoiceItem = new InvoiceItem(item, invoice);
//                        invoiceItems.add(invoiceItem);
//                        invoiceItem.setId(id.getAndIncrement());
//                    }
//            );
//            return invoiceItems;
//        }).when(invoiceItemService).saveInvoiceItem(anyList(),isA(Invoice.class));
        mvc.perform(post("/invoice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dtoitems)))
                .andExpect(status().isCreated()).
                andDo((document("Add Items to Invoice POST", responseFields(
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
    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}