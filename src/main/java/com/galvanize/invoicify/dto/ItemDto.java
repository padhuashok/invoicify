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
    private boolean isFlatFee;
    private double rateFee;
    private int quantityFee;
    private double amountFlatFee;

    public double getFee() {
        return fee.getTotalFee();
    }

    public boolean getIsFlatFee() {
        return isFlatFee;
    }

    public void setIsFlatFee(boolean flatFee) {
        isFlatFee = flatFee;
    }
}
