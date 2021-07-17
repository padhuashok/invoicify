package com.galvanize.invoicify.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceDTO {
    private int invoiceNumber;
    private double invoiceTotal;
    private String invoiceStatus;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    @JsonIgnore
    private List<ItemDto> itemDtoList;
    private Long companyId;
}
