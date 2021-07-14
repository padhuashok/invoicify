package com.galvanize.invoicify.integration;

import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.repository.InvoiceItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest
public class MockedRepoTests{
    @MockBean
    private MockMvc mvc;
    @MockBean
    InvoiceItemRepository invoiceItemRepository;
    @Test
    public void addItemToInvoice() throws Exception{
//        Invoice invoice=new Invoice();
//        Item item1 = new Item("DevItems",5,25);
//        Item item2 = new Item("Dev Items More", 2, 10);
//        InvoiceItem invoiceItem1=new InvoiceItem(item1,invoice);
//        InvoiceItem invoiceItem2=new InvoiceItem(item2,invoice);
//        List<InvoiceItem> items=new ArrayList<>();
//        when(invoiceItemRepository.saveAll(items)).thenReturn(items);

    }

}