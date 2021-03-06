package com.galvanize.invoicify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @JoinColumn(name="itemId")
    @ManyToOne
    private Item item;
    @JsonIgnoreProperties(ignoreUnknown = true, value = {"invoiceItems"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="invoiceId")
    private Invoice invoice;
    public InvoiceItem(Item item, Invoice invoice) {
        this.item = item;
        this.invoice = invoice;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "id=" + id +
                ", item.id=" + item.getId() +
                ", invoice.id=" + invoice.getId() +
                '}';
    }
}
