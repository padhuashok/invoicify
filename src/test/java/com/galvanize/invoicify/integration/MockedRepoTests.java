package com.galvanize.invoicify.integration;

import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.repository.InvoiceItemRepository;
import com.galvanize.invoicify.repository.InvoiceRepository;
import com.galvanize.invoicify.repository.ItemRepository;
import com.galvanize.invoicify.utils.InvoicifyStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class MockedRepoTests{
    @Autowired
    private MockMvc mvc;
    @MockBean
    InvoiceItemRepository invoiceItemRepository;
    @MockBean
    ItemRepository itemRepository;
    @MockBean
    InvoiceRepository invoiceRepository;
    @Test
    public void addItemToInvoice() throws Exception{
        Invoice invoice=new Invoice();
        invoice.setInvoiceTotal(250);
        Item item1 = new Item("DevItems",5,25);
        Item item2 = new Item("Dev Items More", 2, 10);
        InvoiceItem invoiceItem1=new InvoiceItem(item1,invoice);
        InvoiceItem invoiceItem2=new InvoiceItem(item2,invoice);
        List<Item> itemList =  Arrays.asList(item1,item2);
        List<InvoiceItem> invoiceItems=new ArrayList<>();
        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        when(itemRepository.saveAll(itemList)).thenReturn(itemList);
        when(invoiceItemRepository.saveAll(invoiceItems)).thenReturn(invoiceItems);
        mvc.perform(post("/invoice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(InvoicifyStringUtils.asJsonString(invoiceItems)))
                .andExpect(status().isCreated()).andDo((document("Add Items to Invoice POST", responseFields(
                fieldWithPath("[]").description("One or more line invoiceItems of invoice"),
                fieldWithPath("[].id").description("Internal ID of Items"),
                fieldWithPath("[].description").description("Description of line item for invoice"),
                fieldWithPath("[].quantity").description("Number of persons involved for the work"),
                fieldWithPath("[].isFlatFee").description("Identifies the type of cost ( flat/rate based)"),
                fieldWithPath("[].amountFlatFee").description("Flat fee Amount charged for an item "),
                fieldWithPath("[].rateFee").description("Rate per person involved in the work "),
                fieldWithPath("[].quantityFee").description("Amount for each person involved")))));
    }

}