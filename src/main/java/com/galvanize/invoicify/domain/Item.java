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
    @OneToOne
    private Fee fee;

    @OneToMany(mappedBy = "item")
    private List<InvoiceItem> invoiceItems;


    public Item(String dev_items,  Fee fee) {
        this.description=dev_items;
        this.fee=fee;
    }

    public double getFee(  ) {

        return fee.getTotalFee();
    }
}
