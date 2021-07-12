package com.galvanize.invoicify.controller;

import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.service.InvoiceItemService;
import com.galvanize.invoicify.service.InvoiceService;
import com.galvanize.invoicify.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InvoicifyController {
    private InvoiceService invoiceService;
    private ItemService itemService;
    private InvoiceItemService invoiceItemService;
    public InvoicifyController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
    public InvoicifyController(ItemService itemService) {
        this.itemService = itemService;
    }

    public InvoicifyController(InvoiceItemService invoiceItemService) {
        this.invoiceItemService = invoiceItemService;
    }

    @GetMapping(value = "/healthcheck")
    public String healthCheck(){
        return "Hello Alpha Team";
    }

    @PostMapping("/invoice")
    public void addItemToInvoice(@RequestBody List<ItemDto> itemDtos){

       List<Item> items = itemService.saveItems(itemDtos);
       Invoice invoice = new Invoice();
       invoiceService.saveInvoice(invoice);
       invoiceItemService.saveInvoiceItem(items, invoice);


    }

}
