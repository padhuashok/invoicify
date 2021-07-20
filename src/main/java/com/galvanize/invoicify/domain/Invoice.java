package com.galvanize.invoicify.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.galvanize.invoicify.dto.CompanyDTO;
import com.galvanize.invoicify.dto.InvoiceDTO;
import com.galvanize.invoicify.dto.ItemDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Invoice {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Long id;
   @Column(unique = true)
   private int invoiceNumber;

   @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY)
   private List<InvoiceItem> invoiceItems;
   private double invoiceTotal;
   private String invoiceStatus;
   private LocalDate createdDate;
   private LocalDate modifiedDate;
   @ManyToOne
   private Company company;

   public Invoice(InvoiceDTO d, Company c){
      this.invoiceTotal = d.getInvoiceTotal();
      this.createdDate = d.getCreatedDate();
      this.invoiceStatus = d.getInvoiceStatus();
      this.company = c;
      this.invoiceNumber = d.getInvoiceNumber();
      this.invoiceItems = d.getInvoiceItems();
   }
   public void createInvoiceFromDto(InvoiceDTO d, Company c){
      this.invoiceTotal = d.getInvoiceTotal();
      this.createdDate = d.getCreatedDate();
      this.invoiceStatus = d.getInvoiceStatus();
      this.company = c;
      this.invoiceNumber = d.getInvoiceNumber();
      this.invoiceItems = d.getInvoiceItems();
   }
   public Invoice(InvoiceDTO d){
      this.invoiceTotal = d.getInvoiceTotal();
      this.createdDate = d.getCreatedDate();
      this.invoiceStatus = d.getInvoiceStatus();
   }

   public InvoiceDTO convertToDTo() {
      InvoiceDTO invoiceDTo = new InvoiceDTO();
      invoiceDTo.setInvoiceTotal( this.getInvoiceTotal());
      invoiceDTo.setCreatedDate(this.getCreatedDate());
      invoiceDTo.setInvoiceStatus(this.getInvoiceStatus());
      invoiceDTo.setInvoiceNumber(this.getInvoiceNumber());
      invoiceDTo.setInvoiceItems(this.getInvoiceItems());
      List<ItemDto> itemDtoList = new ArrayList<>();
      for (InvoiceItem i: this.getInvoiceItems())
         if (i.getItem() != null)
            itemDtoList.add(new ItemDto(i.getItem()));
      invoiceDTo.setItemDtoList(itemDtoList);
      CompanyDTO companyDTO =  new CompanyDTO(this.company);
      invoiceDTo.setCompanyDTO(companyDTO);
      return invoiceDTo;
   }

    public void convertFromDTOAndCompany(InvoiceDTO d, Company c) {
       this.invoiceTotal = d.getInvoiceTotal();
       this.createdDate = d.getCreatedDate();
       this.invoiceStatus = d.getInvoiceStatus();
       this.company = c;
       this.invoiceNumber = d.getInvoiceNumber();
       this.invoiceItems = d.getInvoiceItems();
    }
}
