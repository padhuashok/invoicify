package com.galvanize.invoicify.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private long invoiceId;
    private int invoiceNumber;
    private double invoiceTotal;
    private String invoiceStatus;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private List<ItemDto> itemDtoList;
    private List<InvoiceItem> invoiceItems;
    private Long companyId;
    private CompanyDTO companyDTO;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}
