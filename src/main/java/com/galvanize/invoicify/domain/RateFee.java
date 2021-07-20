package com.galvanize.invoicify.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Setter
public class RateFee extends Fee {
    private double rate;
    private int quantity;


    public RateFee(double rate, int quantity) {
        this.rate = rate;
        this.quantity = quantity;
    }

    @Override
    public double getTotalFee(){
        return quantity*rate;
    }

}
