package com.galvanize.invoicify.domain;

import com.galvanize.invoicify.dto.CompanyDTO;
import com.galvanize.invoicify.dto.InvoiceDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
public class Invoice {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Long id;
   @Column(unique = true)
   private int invoiceNumber;
   @OneToMany(mappedBy = "invoice")
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
