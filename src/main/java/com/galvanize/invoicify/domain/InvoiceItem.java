package com.galvanize.invoicify.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class InvoiceItem {
    @JoinColumn(name="itemId")
    @ManyToOne
    private Item item;
    @ManyToOne
    @JoinColumn(name="invoiceId")
    private Invoice invoice;
}
