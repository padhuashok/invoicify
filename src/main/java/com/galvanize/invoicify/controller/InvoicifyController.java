package com.galvanize.invoicify.controller;

import com.galvanize.invoicify.service.InvoiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoicifyController {

    public InvoicifyController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    private InvoiceService invoiceService;

    @GetMapping(value = "/healthcheck")
    public String healthCheck(){
        return "Hello Alpha Team";
    }

}
