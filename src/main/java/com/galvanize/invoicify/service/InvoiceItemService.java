package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.repository.InvoiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceItemService {
    @Autowired
    InvoiceItemRepository invoicItemRepository;
    public void saveInvoiceItem(List<Item> items, Invoice invoice) {
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        items.forEach( item -> {
            InvoiceItem invoiceItem = new InvoiceItem(item, invoice);
            invoiceItems.add(invoiceItem);
                }
        );

        invoicItemRepository.saveAll(invoiceItems);
    }
}
