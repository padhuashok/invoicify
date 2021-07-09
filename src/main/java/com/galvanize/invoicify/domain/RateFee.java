package com.galvanize.invoicify.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RateFee extends Fee {
    private double rate;
    private int quantity;


}
