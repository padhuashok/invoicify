package com.galvanize.invoicify.controller;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.InvoiceDTO;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.exception.ResourceNotFoundException;
import com.galvanize.invoicify.response.GeneralResponse;
import com.galvanize.invoicify.service.CompanyService;
import com.galvanize.invoicify.service.InvoiceItemService;
import com.galvanize.invoicify.service.InvoiceService;
import com.galvanize.invoicify.service.ItemService;
import com.galvanize.invoicify.utils.RestUtils;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class InvoicifyController {

    private InvoiceService invoiceService;
    private ItemService itemService;
    private InvoiceItemService invoiceItemService;
    private CompanyService companyService;

//    public InvoicifyController() {
//
//    }

    public InvoicifyController(InvoiceService invoiceService, ItemService itemService, InvoiceItemService invoiceItemService, CompanyService companyService) {
        this.invoiceService = invoiceService;
        this.itemService = itemService;
        this.invoiceItemService = invoiceItemService;
        this.companyService = companyService;
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
    public ResponseEntity<Invoice> createInvoice(@RequestBody InvoiceDTO invoiceDTO) throws ResourceNotFoundException {
        List<InvoiceItem> invoiceItems = addItemToInvoice(invoiceDTO.getItemDtoList()).getBody();
        //call company service to check if company exists and then call invoice service
        Company c = companyService.findById(invoiceDTO.getCompanyId());
        Invoice invoice = invoiceService.calculateTotalCostAndSetStatus(invoiceItems, invoiceDTO, c);
        return new ResponseEntity<Invoice>(invoice, HttpStatus.CREATED);
    }

    @GetMapping("/invoice/{invoiceNumber}")
    public ResponseEntity<GeneralResponse<Invoice>> searchInvoiceByInvNumber(@PathVariable int invoiceNumber) throws ResourceNotFoundException {
        try {
            Invoice invoice = invoiceService.getInvoiceByInvoiceNumber(invoiceNumber);
            return RestUtils.buildResponse(invoice);
        }catch (ResourceNotFoundException ex){
            System.out.println(ex.getMessage());
            return RestUtils.buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), null);
        }
    }



}
