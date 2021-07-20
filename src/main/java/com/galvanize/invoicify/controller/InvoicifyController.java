package com.galvanize.invoicify.controller;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.InvoiceDTO;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.exception.ResourceNotFoundException;
import com.galvanize.invoicify.service.CompanyService;
import com.galvanize.invoicify.service.InvoiceItemService;
import com.galvanize.invoicify.service.InvoiceService;
import com.galvanize.invoicify.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InvoicifyController {

    private final InvoiceService invoiceService;
    private final ItemService itemService;
    private final InvoiceItemService invoiceItemService;
    private final CompanyService companyService;

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
        return new ResponseEntity<>(invoiceItems, HttpStatus.CREATED);
    }

    @PostMapping("/invoice")
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceDTO invoiceDTO) throws ResourceNotFoundException {
        List<InvoiceItem> invoiceItems = invoiceDTO.getInvoiceItems();
        // List<InvoiceItem> invoiceItems = addItemToInvoice(invoiceDTO.getItemDtoList()).getBody();
        //call company service to check if company exists and then call invoice service
        Company c = companyService.getCompanyById(invoiceDTO.getCompanyId());
        invoiceDTO = invoiceService.calculateTotalCostAndSetStatus(invoiceDTO, c);
        return new ResponseEntity<>(invoiceDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/invoices")
    public ResponseEntity<List<InvoiceDTO>> getInvoices(@RequestParam(defaultValue = "0") int pageNum) {
        return new ResponseEntity<>(invoiceService.getAllInvoicesByPageNum(pageNum), HttpStatus.OK);
    }


}
