package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.dto.InvoiceItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem,Long> {


    @Query(value = "Select * " +
            // "from Invoice i where year(sysdate) - year(i.createdDate)>1 and i.invoiceStatus=PAID")
            "from InvoiceItem i ", nativeQuery = true)
        // "where i.invoiceStatus=PAID")
    List<InvoiceItem> getInvoiceExpiredAndPaid();
}
