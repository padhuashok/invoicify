package com.galvanize.invoicify.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Item {
    private long id;
    private String description;
    private int quantity;
    private Fee fee;
    @OneToMany(mappedBy = "items")
    private List<Invoice> invoices;



}
