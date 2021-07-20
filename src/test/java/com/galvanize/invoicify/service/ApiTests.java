package com.galvanize.invoicify.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.InvoiceDTO;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.exception.ResourceNotFoundException;
import com.galvanize.invoicify.utils.InvoicifyStringUtils;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
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

    @MockBean
    CompanyService companyService;

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
    InvoiceDTO invoiceDTO;
    Company company;

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
        Company c = new Company();
        c.setContactName("Hey There");
        c.setName("My First Company");
        invoiceDTO = new InvoiceDTO();
        invoiceDTO.setItemDtoList(dtoitems);
        invoiceDTO.setCompanyId(1L);
        double totalCost = 0.0;
        for (InvoiceItem i:
                invoiceItemList) {
            totalCost += i.getItem().getTotalFee();
        }
        invoiceDTO.setInvoiceTotal(totalCost);
        invoiceDTO.setInvoiceStatus("UNPAID");
        invoiceDTO.setCreatedDate(LocalDate.now());
        invoice = new Invoice(invoiceDTO, c);
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
                fieldWithPath("[].invoice.invoiceNumber").description("Amount for each person involved"),
                fieldWithPath("[].invoice.invoiceTotal").description("Rate per person involved in the work "),
                        fieldWithPath("[].invoice.invoiceStatus").description("Amount for each person involved"),
                        fieldWithPath("[].invoice.createdDate").description("Amount for each person involved"),
                        fieldWithPath("[].invoice.modifiedDate").description("Amount for each person involved"),
                        fieldWithPath("[].invoice.company").description("Amount for each person involved"),
                        fieldWithPath("[].invoice.company").description("Amount for each person involved")))));
    }

  /*  @Test
    public void createInvoiceAndCalculateInvoiceTotal() throws ResourceNotFoundException, Exception {
        when(itemservice.saveItems(anyList())).thenReturn(itemList);
        when(invoiceItemService.saveInvoiceItem(anyList(),isA(Invoice.class))).thenReturn(invoiceItemList);
        when(companyService.findById(1L)).thenReturn(company);
        when(invoiceService.calculateTotalCostAndSetStatus(anyList(),isA(InvoiceDTO.class),isA(Company.class))).thenReturn(invoice);
        mvc.perform(post("/invoice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(InvoicifyStringUtils.asJsonString(invoiceDTO)))
                .andExpect(status().isCreated());
//                .andExpect(jsonPath("$").value(invoice))
//                .andExpect(jsonPath("$.invoiceStatus").value("UNPAID"))
//                .andExpect(jsonPath("$.invoiceItems[0].item").value(item1));
//                //.andExpect(jsonPath("[1].invoice").value(invoiceItemList.get(1).getInvoice()));
    }*/

    @Test
    public void searchInvoiceByInvalidInvoiceNumber() throws ResourceNotFoundException, Exception {
        int invoiceNumber = 1;
        when(invoiceService.getInvoiceByInvoiceNumber(invoiceNumber)).thenThrow( new ResourceNotFoundException("Invoice number"+ invoiceNumber+" not found"));
        mvc.perform(get("/invoice/{invoiceNumber}", invoiceNumber))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Invoice number"+ invoiceNumber+" not found"))
                .andExpect(jsonPath("$.data").doesNotExist());

    }

    @Test
    public void searchInvoiceByInvoiceNumber() throws ResourceNotFoundException, Exception {
        int invoiceNumber = 1;
        invoice.getCompany().setId(1L);
        invoice.setId(1L);
        invoice.setInvoiceNumber(invoiceNumber);
        when(invoiceService.getInvoiceByInvoiceNumber(invoiceNumber)).thenReturn(invoice);
        System.out.println(invoice);
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
                        fieldWithPath("data.invoiceNumber").description("Invoice number is uniques for each invoice"),
                        fieldWithPath("data.invoiceItems").description("Description of line item for invoice"),
                        fieldWithPath("data.invoiceTotal").description("Total cost for a invoice"),
                        fieldWithPath("data.invoiceStatus").description("Invoice can have unpaid, paid status"),
                        fieldWithPath("data.createdDate").description("Create date of invoice"),
                        fieldWithPath("data.modifiedDate").description("Modified Date"),
                        fieldWithPath("data.company.id").description("Company id"),
                        fieldWithPath("data.company.name").description("Company name"),
                        fieldWithPath("data.company.address").description("Company address"),
                        fieldWithPath("data.company.contactName").description("Company contact Name"),
                        fieldWithPath("data.company.contactTitle").description("Company contactTitle"),
                        fieldWithPath("data.company.contactPhoneNumber").description("Company contactPhoneNumber"),
                        fieldWithPath("data.company.invoices").description("Company invoices")))));

    }
}