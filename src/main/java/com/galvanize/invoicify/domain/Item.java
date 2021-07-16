package com.galvanize.invoicify.domain;

import com.galvanize.invoicify.dto.ItemDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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
    public Item(ItemDto e){

        if(e.getIsFlatFee()) {
            e.setFee(new FlatFee(e.getAmount()));
        }
        else{
            e.setFee(new RateFee(e.getRateFee(), e.getQuantity()));
        }
        this.setQuantity(e.getQuantity());
        this.setTotalFee(e.getFee());
        this.setDescription(e.getDescription());

    }

}
