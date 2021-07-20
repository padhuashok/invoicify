package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.dto.InvoiceItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("Select max(i.invoiceNumber) from Invoice i")
    int getMaxInvoiceNumber();

    @Transactional
    @Modifying
    @Query(value = "Delete from Invoice i where i.id in(?1)", nativeQuery = true)
    void deleteInvoicesByIds(List<Long> invoiceIds);


//    @Query("Select new com.galvanize.invoicify.dto.InvoiceItemId(i.invoiceItems.id,  i.invoiceItems.item.id, i.id) " +
//           // "from Invoice i where year(sysdate) - year(i.createdDate)>1 and i.invoiceStatus=PAID")
//            "from Invoice i ")
//           // "where i.invoiceStatus=PAID")
//    List<InvoiceItemId> getInvoiceExpiredAndPaid();
}
