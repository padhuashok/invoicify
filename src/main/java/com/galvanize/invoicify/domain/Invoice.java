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
   private long id;
   @OneToMany(mappedBy = "invoice")
   private List<InvoiceItem> invoiceItems;
   @ManyToOne
   @JoinColumn(name="companyId")
   private Company company;

}
