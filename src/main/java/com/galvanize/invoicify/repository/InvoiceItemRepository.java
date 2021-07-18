package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.dto.InvoiceItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem,Long> {

    @Query(value="select * from invoiceitem ii,Invoice i " +
            "where ii.invoiceid=i.id and i.invoicestatus='PAID' and  ((Year(Now())-Year(i.createddate)>1))",nativeQuery = true)
    List<InvoiceItem> getInvoiceExpiredAndPaid();

}
