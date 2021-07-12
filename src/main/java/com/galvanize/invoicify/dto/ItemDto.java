package com.galvanize.invoicify.dto;

import com.galvanize.invoicify.domain.Fee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToOne;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ItemDto {
    private String description;
    private int quantity;
    private Fee fee;

    public double getFee() {
        return fee.getTotalFee();
    }
}
