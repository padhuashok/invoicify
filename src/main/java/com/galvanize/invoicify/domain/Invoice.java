package com.galvanize.invoicify.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Invoice {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   Long id;
   @OneToMany(mappedBy = "invoice")
   private List<InvoiceItem> invoiceItems;

   private double invoiceTotal;

   public double calculateInvoiceTotal(){
      double temp=0;
      for(InvoiceItem invoiceitem : invoiceItems){

         invoiceTotal+=invoiceitem.getItem().getFee();

      }
   return invoiceTotal;
   }
}
