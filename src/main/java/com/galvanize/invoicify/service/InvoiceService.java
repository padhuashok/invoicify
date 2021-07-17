package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.dto.InvoiceDTO;
import com.galvanize.invoicify.exception.ResourceNotFoundException;
import com.galvanize.invoicify.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
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

    public Invoice calculateTotalCostAndSetStatus(List<InvoiceItem> invoiceItems, InvoiceDTO invoiceDTO, Company company){
        double totalCost = 0.0;
        for (InvoiceItem i:
             invoiceItems) {
            totalCost += i.getItem().getTotalFee();
        }
        invoiceDTO.setInvoiceTotal(totalCost);
        invoiceDTO.setInvoiceStatus("UNPAID");
        invoiceDTO.setCreatedDate(LocalDate.now());
        Invoice invoice = new Invoice(invoiceDTO,company);
        return invoiceRepo.save(invoice);
    }

    public Invoice getInvoiceByInvoiceNumber(int invoiceNumber) throws ResourceNotFoundException {
       return  invoiceRepo.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice number"+ invoiceNumber+" not found"));
    }
}



