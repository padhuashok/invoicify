package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.*;
import com.galvanize.invoicify.dto.InvoiceDTO;
import com.galvanize.invoicify.dto.InvoiceItemId;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@WebMvcTest
public class ServiceTests {

    @Autowired
    private MockMvc mvc;
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
        when(itemService.saveItems(itemDtoList)).thenReturn(itemList);
        List<Item> actualItems = itemService.saveItems(itemDtoList);
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
    void createInvoiceAndUpdateCompany() throws Exception, ResourceNotFoundException {
        Company c = new Company();
        c.setId(1L);
        c.setContactName("Hey There");
        c.setName("My First Company");
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceItems(invoiceItemList);
        invoiceDTO.setCompanyId(1L);
        double totalCost = 0.0;
        for (InvoiceItem i:
                invoiceItemList) {
            totalCost += i.getItem().getTotalFee();
        }
        invoiceDTO.setInvoiceTotal(totalCost);
        invoiceDTO.setInvoiceStatus("UNPAID");
        invoiceDTO.setCreatedDate(LocalDate.now());
        Invoice invoice = new Invoice(invoiceDTO, c);
        when(companyService.getCompanyById(invoiceDTO.getCompanyId())).thenReturn(c);
        when(itemService.saveItems(itemDtoList)).thenReturn(itemList);
        when(invoiceItemService.saveInvoiceItem(itemList, invoice)).thenReturn(invoiceItemList);
        when(invoiceService.calculateTotalCostAndSetStatus(invoiceDTO,c)).thenReturn(invoiceDTO);
        InvoiceDTO actualInvoice = invoiceService.calculateTotalCostAndSetStatus(invoiceDTO,c);
        assertEquals(invoice,actualInvoice);
    }

    @Test
    public void deleteExpiredAndPaidInvoice() throws Exception{
        List<Item> actualItems = itemService.saveItems(itemDtoList);
        Invoice actualInvoice = invoiceService.saveInvoice(invoice);

        List<InvoiceItem> invoiceItemList = invoiceItemService.saveInvoiceItem(itemList, invoice);
        Company c = new Company();
        c.setId(1L);
        c.setContactName("Hey There");
        c.setName("My First Company");
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceItems(invoiceItemList);
        invoiceDTO.setCompanyId(1L);
        double totalCost = 0.0;
        for (InvoiceItem i:
                invoiceItemList) {
            totalCost += i.getItem().getTotalFee();
        }
        invoiceDTO.setInvoiceTotal(totalCost);
        invoiceDTO.setInvoiceStatus("UNPAID");
        invoiceDTO.setCreatedDate(LocalDate.now());
        Invoice invoice = new Invoice(invoiceDTO, c);
        List<InvoiceItemId> invoiceItemIdList= new ArrayList<>();
        invoiceItemList.forEach( invIt -> {
            invoiceItemIdList.add(new InvoiceItemId(invIt.getId(), invIt.getItem().getId(), invIt.getInvoice().getId()));
        });
        invoice.setCreatedDate(LocalDate.now().minusYears(1));
        invoice.setInvoiceStatus("PAID");
        when(invoiceService.getInvoiceExpiredAndPaid()).thenReturn(invoiceItemIdList);

        List<Long> invoiceItemIds = invoiceItemIdList.stream().map(ex -> ex.getInvoiceItemId()).collect(Collectors.toList());
        List<Long> itemIds = invoiceItemIdList.stream().map(ex -> ex.getItemId()).collect(Collectors.toList());
        List<Long> invoiceIds = invoiceItemIdList.stream().map(ex -> ex.getInvoiceId()).collect(Collectors.toList());
        doNothing().when(invoiceItemService).deleteExpiredAndPaidInv(invoiceItemIds);
        doNothing().when(itemService).deleteByIds(itemIds);
        doNothing().when(invoiceItemService).deleteExpiredAndPaidInv(invoiceIds);

        invoiceItemService.deleteExpiredAndPaidInv(invoiceItemIds);
        itemService.deleteByIds(itemIds);
        invoiceService.deleteByIds(invoiceIds);
    }

    @Test
    void getListOfInvoices() throws Exception{
        List<Invoice> invoices = new ArrayList<>();
        for(int i=0;i<15;i++){
            Invoice in = new Invoice();
            in.setId((1L+i));
        }
    }
}
