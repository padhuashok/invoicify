package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class InvoiceService {


    @Autowired
    InvoiceRepository invoiceRepo;

    public List<Invoice> findAllInvoices() {
        return invoiceRepo.findAll();
    }

    public void addInvoice(Invoice inv) {

        invoiceRepo.save(inv);
    }

    public Invoice saveInvoice(Invoice invoice) {
       return  invoiceRepo.save(invoice);
    }
}



