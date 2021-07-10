package com.galvanize.invoicify.domain;

import javax.persistence.*;

@Entity
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @JoinColumn(name="itemId")
    @ManyToOne
    private Item item;
    @ManyToOne
    @JoinColumn(name="invoiceId")
    private Invoice invoice;
}
