package com.galvanize.invoicify.domain;

import javax.persistence.*;
@Entity
public class Fee {
    private double totalFee;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
}
