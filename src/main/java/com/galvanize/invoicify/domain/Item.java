package com.galvanize.invoicify.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String description;
    private int quantity;
    private double totalFee;

    @OneToMany(mappedBy = "item")
    private List<InvoiceItem> invoiceItems;

    public Item(String description, int quantity, double totalFee) {
        this.description = description;
        this.quantity = quantity;
        this.totalFee = totalFee;
    }
}
