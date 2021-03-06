package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.repository.InvoiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceItemService {
    @Autowired
    InvoiceItemRepository invoicItemRepository;
    public List<InvoiceItem> saveInvoiceItem(List<Item> items, Invoice invoice) {
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        items.forEach( item -> {
            InvoiceItem invoiceItem = new InvoiceItem(item, invoice);
            invoiceItems.add(invoiceItem);
                }
        );
        return invoicItemRepository.saveAll(invoiceItems);
    }

    public void deleteExpiredAndPaidInv(List<Long> invoiceItemIds) {
        invoicItemRepository.deleteAllById(invoiceItemIds);
    }
    public List<InvoiceItem> getInvoiceExpiredAndPaid() {
        LocalDate endDate =  LocalDate.now().minusYears(1);
        return invoicItemRepository.getInvoiceExpiredAndPaid(endDate);
        //return invoicItemRepository.getInvoiceExpiredAndPaid();
    }
}
