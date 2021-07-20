package com.galvanize.invoicify.integration;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.exception.ResourceNotFoundException;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    public void addItemToInvoice() throws Exception {
        ItemDto itemdto1 = new ItemDto("Dev Items", 1, true, 50);
        ItemDto itemdto2 = new ItemDto("Dev Items More", 2, false, 2.3);
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
        when(itemRepository.saveAll(anyList())).thenReturn(itemList);
        when(invoiceRepository.save(isA(Invoice.class))).thenReturn(invoice);
        when(invoiceItemRepository.saveAll(anyList())).thenReturn(invoiceItemList);
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
                        fieldWithPath("[].invoice.invoiceNumber").description("Invoice Number"),
                        fieldWithPath("[].invoice.invoiceStatus").description("Description of invoice status(PAID/UNPAID)"),
                        fieldWithPath("[].invoice.createdDate").description("Date of invoice created"),
                        fieldWithPath("[].invoice.modifiedDate").description("Date of invoice updated"),
                        fieldWithPath("[].invoice.company").description("Company the invoice is not associated yet"),
                        fieldWithPath("[].invoice.invoiceTotal").description("Rate per person involved in the work not calculated yet")))));
    }

    @Test
    public void searchInvoiceByInvoiceNumber() throws ResourceNotFoundException, Exception {
        int invoiceNumber = 1;
        Company company = new Company();
        company.setId(1L);
        company.setContactName("Hey There");
        company.setName("My First Company");
        Invoice invoice = new Invoice();
        invoice.setCompany(company);
        invoice.setId(1L);
        invoice.setInvoiceNumber(invoiceNumber);
        invoice.setCreatedDate(LocalDate.now());
        invoice.setInvoiceTotal(250.0);
        invoice.setInvoiceStatus("UNPAID");
        when(invoiceRepository.findByInvoiceNumber(anyInt())).thenReturn(Optional.of(invoice));
        mvc.perform(get("/invoice/{invoiceNumber}", invoiceNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("$.data.id").value(invoice.getId()))
                .andExpect(jsonPath("$.data.invoiceNumber").value(invoice.getInvoiceNumber()))
                //.andExpect(jsonPath("$.invoiceItems").value(invoiceItemList))
                .andExpect(jsonPath("$.data.invoiceTotal").value(invoice.getInvoiceTotal()))
                .andExpect(jsonPath("$.data.invoiceStatus").value(invoice.getInvoiceStatus()))
                .andExpect(jsonPath("$.data.createdDate").value(invoice.getCreatedDate().toString()))
                .andDo((document("Search Invoice GET", responseFields(
                        fieldWithPath("status").description("Response status"),
                        fieldWithPath("message").description("Response message"),
                        fieldWithPath("data.id").description("Invoice ID"),
                        fieldWithPath("data.invoiceTotal").description("Invoice Total"),
                        fieldWithPath("data.invoiceItems").description("Invoice ITEMS"),
                        fieldWithPath("data.invoiceStatus").description("Invoice Status"),
                        fieldWithPath("data.createdDate").description("Create date of invoice"),
                        fieldWithPath("data.modifiedDate").description("Modified Date"),
                        fieldWithPath("data.company.id").description("Company id"),
                        fieldWithPath("data.company.name").description("Company name"),
                        fieldWithPath("data.company.address").description("Company address"),
                        fieldWithPath("data.company.contactName").description("Company contact Name"),
                        fieldWithPath("data.company.contactTitle").description("Company contactTitle"),
                        fieldWithPath("data.company.contactPhoneNumber").description("Company contactPhoneNumber"),
                        fieldWithPath("data.company.invoices").description("Company invoices"),
                        fieldWithPath("data.invoiceNumber").description("Invoice number is uniques for each invoice")))));

    }

}