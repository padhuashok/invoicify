package com.galvanize.invoicify.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

public class FlatFee extends Fee {
    private double amount;

    public FlatFee(double amount) {
        this.amount = amount;
    }
    @Override
    public double getTotalFee(  ){

        return amount;
    }



}
