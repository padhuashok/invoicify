package com.galvanize.invoicify.controller;

import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.InvoiceDTO;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.service.InvoiceItemService;
import com.galvanize.invoicify.service.InvoiceService;
import com.galvanize.invoicify.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoicifyController {

    private InvoiceService invoiceService;
    private ItemService itemService;
    private InvoiceItemService invoiceItemService;

    public InvoicifyController(InvoiceService invoiceService, ItemService itemService, InvoiceItemService invoiceItemService) {
        this.invoiceService = invoiceService;
        this.itemService = itemService;
        this.invoiceItemService = invoiceItemService;
    }

    @GetMapping(value = "/healthcheck")
    public String healthCheck() {
        return "Hello Alpha Team";
    }

    @PostMapping("/invoice/items")
    public ResponseEntity<List<InvoiceItem>> addItemToInvoice(@RequestBody List<ItemDto> itemDtos) {
        List<Item> items = itemService.saveItems(itemDtos);
        Invoice invoice = new Invoice();
        invoice = invoiceService.saveInvoice(invoice);
        List<InvoiceItem> invoiceItems = invoiceItemService.saveInvoiceItem(items, invoice);
        return new ResponseEntity<List<InvoiceItem>>(invoiceItems, HttpStatus.CREATED);
    }

    @PostMapping("/invoice")
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceDTO invoiceDTO){
        List<InvoiceItem> invoiceItems = addItemToInvoice(invoiceDTO.getItemDtoList());
        //call company service to check if comapny exists and then call invoice service
        Company c = companyService.getCompany(invoiceDTO.getCompanyId());
        invoiceService.calculateTotalCostAndSetStatus(invoiceItems);
    }


}
