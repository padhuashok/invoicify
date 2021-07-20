package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.InvoiceItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem,Long> {

    @Query(value="select * from InvoiceItem ii,Invoice i " +
            "where ii.invoiceId=i.id and i.invoiceStatus='PAID' and  ((Year(Now())-Year(i.createdDate)>1))",nativeQuery = true)
    List<InvoiceItem> getInvoiceExpiredAndPaid();

//    @Query("select i  from InvoiceItem i where i.invoice.createdDate < ?1 and i.invoice.invoiceStatus='PAID'")
//    List<InvoiceItem> getInvoiceExpiredAndPaid( LocalDate endDate);

}
