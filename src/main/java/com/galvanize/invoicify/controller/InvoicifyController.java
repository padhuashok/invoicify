package com.galvanize.invoicify.controller;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.InvoiceDTO;
import com.galvanize.invoicify.dto.InvoiceItemId;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.exception.ResourceNotFoundException;
import com.galvanize.invoicify.response.GeneralResponse;
import com.galvanize.invoicify.service.CompanyService;
import com.galvanize.invoicify.service.InvoiceItemService;
import com.galvanize.invoicify.service.InvoiceService;
import com.galvanize.invoicify.service.ItemService;
import com.galvanize.invoicify.utils.Constants;
import com.galvanize.invoicify.utils.RestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        invoiceDTO= invoiceService.calculateTotalCostAndSetStatus(invoiceDTO,c);
        return new ResponseEntity<>(invoiceDTO, HttpStatus.CREATED) ;
    }

    @DeleteMapping("/invoice")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GeneralResponse<String>> deleteAllExpiredAndPaidInvoice(){
        try {
            List<InvoiceItem>  invoiceItemList = invoiceItemService.getInvoiceExpiredAndPaid();

            List<InvoiceItemId> expiredList = invoiceItemList.stream().map( invIt -> new InvoiceItemId(invIt)).collect(Collectors.toList());
            if (!expiredList.isEmpty()) {
                List<Long> invoiceItemIds = expiredList.stream().map(ex -> ex.getInvoiceItemId()).collect(Collectors.toList());
                invoiceItemService.deleteExpiredAndPaidInv(invoiceItemIds);

                List<Long> itemIds = expiredList.stream().map(ex -> ex.getItemId()).collect(Collectors.toList());
                itemService.deleteByIds(itemIds);
                List<Long> invoiceIds = expiredList.stream().map(ex -> ex.getInvoiceId()).collect(Collectors.toList());
                invoiceService.deleteByIds(invoiceIds);
            }
        }catch (Exception e){
            System.out.println(e);
            return RestUtils.buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), "FAILED");
        }
        return RestUtils.buildResponse("SUCCESSED");

    }

    @PutMapping("/invoice/{invoiceNumber}")
    public ResponseEntity<InvoiceDTO> updateInvoice(@PathVariable int invoiceNumber, @RequestBody InvoiceDTO invoiceDTO) throws ResourceNotFoundException {
        Company c = companyService.getCompanyById(invoiceDTO.getCompanyId());
        Invoice invoiceDB = invoiceService.findUnpaidInvoiceByInvoiceNumber(invoiceNumber);
        List<InvoiceItem> invoiceItems = invoiceDTO.getInvoiceItems();
        if(!invoiceItems.isEmpty()){
            invoiceDB.setModifiedDate(LocalDate.now());
            invoiceService.saveInvoice(invoiceDB);
            List<Item> items = invoiceItems.stream().map(invoiceItem  -> {
                invoiceItem.setInvoice(invoiceDB);
                return invoiceItem.getItem();
            }).collect(Collectors.toList());
            items = itemService.updateItems(items);
            invoiceDTO = invoiceService.calculateTotalCostAndSetStatus(invoiceDTO, c);
        }
        return new ResponseEntity<>(invoiceDTO, HttpStatus.OK) ;
    }

}
