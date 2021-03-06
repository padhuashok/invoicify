package com.galvanize.invoicify.dto;

import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import lombok.*;

@Data
public class InvoiceItemId {
    private Long invoiceItemId;
    private Long itemId;
    private Long invoiceId;

    public InvoiceItemId(Long invoiceItemId, Long itemId, Long invoiceId) {
        this.invoiceItemId =  invoiceItemId;
        this.itemId = itemId;
        this.invoiceId = invoiceId;
    }
    public InvoiceItemId(InvoiceItem invoiceItem){
        this.setInvoiceItemId(invoiceItem.getId());
        this.setInvoiceId(invoiceItem.getInvoice().getId());
        this.setItemId(invoiceItem.getItem().getId());
    }
}
