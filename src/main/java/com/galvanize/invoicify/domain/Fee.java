package com.galvanize.invoicify.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Fee {
    private double totalFee;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

}
