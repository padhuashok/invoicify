package com.galvanize.invoicify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoicifyController {

    @GetMapping(value = "/healthcheck")
    public String healthCheck(){
        return "Hello Alpha Team";
    }

}
