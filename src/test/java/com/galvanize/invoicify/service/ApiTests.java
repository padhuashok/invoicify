package com.galvanize.invoicify.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.invoicify.domain.*;
import com.galvanize.invoicify.service.InvoiceItemService;
import com.galvanize.invoicify.service.InvoiceService;
import com.galvanize.invoicify.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest
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
        Invoice invoice= new Invoice();
        List<InvoiceItem> invoiceItems= new ArrayList<InvoiceItem>();
        Item myitem=new Item("Dev Items",5,20.5);
        Item myitemNext = new Item("Dev Items More",2,20.5);
        InvoiceItem invoiceItem=new InvoiceItem(myitem,invoice);
        invoiceItems.add( new InvoiceItem(myitem, invoice));
        //call service to save invoice
        when(invoiceService.saveInvoice(invoice)).thenReturn(invoice);
        //call service to save item
        when(itemservice.saveItem(myitem)).thenReturn(myitem);

        //call service to save invoiceItem

       // when(invoiceItemService.saveInvoiceItem(invoiceItem)).thenReturn(invoiceItem);

        //create request to call api to create and check result






    }


        }