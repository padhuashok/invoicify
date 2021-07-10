package com.galvanize.invoicify.domain;

import lombok.Getter;

import javax.persistence.*;
@Entity
@Getter
public class Fee {
    private double totalFee;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;


}
