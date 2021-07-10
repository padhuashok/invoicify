package com.galvanize.invoicify.domain;

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

    public double getTotalRateFee(  ){
        return quantity*rate;
    }

}
