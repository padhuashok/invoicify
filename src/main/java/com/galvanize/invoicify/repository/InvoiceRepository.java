package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Transactional
    @Modifying
    @Query("Delete Invoice i where i.created<expiryDate and i.invoiceStatus = :paid")
    void deleteAllExpiredAndPaidInvoice(Date expiryDate, String paid);
}
