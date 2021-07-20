package com.galvanize.invoicify.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.InvoiceDTO;
import com.galvanize.invoicify.dto.InvoiceItemId;
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
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    ItemService itemService;

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

        invoice.setInvoiceTotal(25);
        item1.setId(1L);
        item2.setId(2L);
        invoiceItem = new InvoiceItem(item1, invoice);
        invoiceItem2 = new InvoiceItem(item2, invoice);
        invoiceItemList = Arrays.asList(invoiceItem, invoiceItem2);
        company = new Company();
        company.setId(1L);
        company.setContactName("Hey There");
        company.setName("My First Company");
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
        invoiceDTO.setInvoiceNumber(1);
        invoiceDTO.setInvoiceItems(invoiceItemList);
        invoice.createInvoiceFromDto(invoiceDTO, company);
        invoice.setId(1L);
        invoiceDTO.setInvoiceId(1L);
    }

    @Test
    void addItemtoInvoice() throws Exception {
        when(itemService.saveItems(anyList())).thenReturn(itemList);
        when(invoiceService.saveInvoice(isA(Invoice.class))).thenReturn(invoice);
        when(invoiceItemService.saveInvoiceItem(anyList(), isA(Invoice.class))).thenReturn(invoiceItemList);
        mvc.perform(post("/invoice/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(InvoicifyStringUtils.asJsonString(dtoitems)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("[0].item.id").value(invoiceItemList.get(0).getItem().getId()))
                .andExpect(jsonPath("[0].invoice.id").value(invoiceItemList.get(0).getInvoice().getId()))
                .andExpect(jsonPath("[1].item.id").value(invoiceItemList.get(1).getItem().getId()))
                .andExpect(jsonPath("[1].invoice.id").value(invoiceItemList.get(1).getInvoice().getId()))
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
                        fieldWithPath("[].invoice.company.id").description("Company the invoice is not associated yet"),
                        fieldWithPath("[].invoice.company.id").description("Company id the invoice is not associated yet"),
                        fieldWithPath("[].invoice.company.name").description("Company name the invoice is not associated yet"),
                        fieldWithPath("[].invoice.company.address").description("Company address the invoice is not associated yet"),
                        fieldWithPath("[].invoice.company.contactName").description("Company contact person name the invoice is not associated yet"),
                        fieldWithPath("[].invoice.company.contactTitle").description("Company contact person title the invoice is not associated yet"),
                        fieldWithPath("[].invoice.company.contactPhoneNumber").description("Company contact phone number the invoice is not associated yet"),
                        fieldWithPath("[].invoice.company.invoices").description("Company the invoice is not associated yet"),
                        fieldWithPath("[].invoice.invoiceTotal").description("Rate per person involved in the work not calculated yet")))));
    }

    @Test
    public void createInvoiceAndCalculateInvoiceTotal() throws ResourceNotFoundException, Exception {
        when(companyService.getCompanyById(1L)).thenReturn(company);
        when(invoiceService.calculateTotalCostAndSetStatus(isA(InvoiceDTO.class),isA(Company.class)))
                .thenReturn(invoiceDTO);
        mvc.perform(post("/invoice")
                .contentType(MediaType.APPLICATION_JSON)
                .content(InvoicifyStringUtils.asJsonString(invoiceDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.invoiceId").value(1L))
                .andExpect(jsonPath("$.invoiceStatus").value("UNPAID"))
                .andExpect(jsonPath("$.itemDtoList.length()").value(invoiceDTO.getItemDtoList().size()))
                .andExpect(jsonPath("$.itemDtoList[0].description").value(invoiceDTO.getItemDtoList().get(0).getDescription()))
                .andExpect(jsonPath("$.itemDtoList[0].quantity").value(invoiceDTO.getItemDtoList().get(0).getQuantity()))
                .andExpect(jsonPath("$.itemDtoList[0].amount").value(invoiceDTO.getItemDtoList().get(0).getAmount()))
                .andExpect(jsonPath("$.itemDtoList[0].totalFee").value(invoiceDTO.getItemDtoList().get(0).getTotalFee()))
                .andExpect(jsonPath("$.companyId").value(company.getId()))
                .andDo((document("Create Invoice POST", responseFields(
                        fieldWithPath("invoiceId").description("Rate per person involved in the work "),
                        fieldWithPath("invoiceNumber").description("Invoice Number"),
                        fieldWithPath("invoiceStatus").description("Description of invoice status(PAID/UNPAID)"),
                        fieldWithPath("createdDate").description("Date of invoice created"),
                        fieldWithPath("modifiedDate").description("Date of invoice updated"),
                        fieldWithPath("invoiceTotal").description(" invoice total"),
                        fieldWithPath("modifiedDate").description("Date of invoice updated"),
                        fieldWithPath("itemDtoList[].description").description("Description of item "),
                        fieldWithPath("itemDtoList[].quantity").description("Quantity of item "),
                        fieldWithPath("itemDtoList[].amount").description("Amount of item "),
                        fieldWithPath("itemDtoList[].totalFee").description("totalFee of item list "),
                        fieldWithPath("companyId").description("Company id associated with the invoice"),
                        fieldWithPath("companyDTO").description("Company information")))));

    }
    @Test
    public void deleteExpiredAndPaidInvoice() throws Exception{
        invoice.setCreatedDate(LocalDate.now().minusYears(1));
        invoice.setInvoiceStatus("PAID");
        List<InvoiceItemId> invoiceItemIdList= new ArrayList<>();
        invoiceItemList.forEach( invIt -> {
            invoiceItemIdList.add(new InvoiceItemId(invIt.getId(), invIt.getItem().getId(), invIt.getInvoice().getId()));
        });


        List<Long> invoiceItemIds = invoiceItemIdList.stream().map(ex -> ex.getInvoiceItemId()).collect(Collectors.toList());
        List<Long> itemIds = invoiceItemIdList.stream().map(ex -> ex.getItemId()).collect(Collectors.toList());
        List<Long> invoiceIds = invoiceItemIdList.stream().map(ex -> ex.getInvoiceId()).collect(Collectors.toList());
        doNothing().when(invoiceItemService).deleteExpiredAndPaidInv(invoiceItemIds);
        doNothing().when(itemService).deleteByIds(itemIds);
        doNothing().when(invoiceItemService).deleteExpiredAndPaidInv(invoiceIds);
        mvc.perform(delete("/invoice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("$.data").value("SUCCEEDED"))
                .andDo((document("Delete Expired and Paid Invoice DELETE",responseFields(
                        fieldWithPath("status").description("Response Status"),
                        fieldWithPath("message").description("Response Message"),
                        fieldWithPath("data").description("Response Data")))));

    }
}