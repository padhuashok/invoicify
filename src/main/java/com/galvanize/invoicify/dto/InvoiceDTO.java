package com.galvanize.invoicify.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.galvanize.invoicify.domain.InvoiceItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceDTO {
    private long id;
    private int invoiceNumber;
    private double invoiceTotal;
    private String invoiceStatus;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private List<ItemDto> itemDtoList;
    @JsonIgnore
    private List<InvoiceItem> invoiceItems;
    private Long companyId;
    private String companyName;
}
