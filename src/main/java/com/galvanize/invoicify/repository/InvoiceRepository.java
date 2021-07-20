package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.dto.InvoiceItemId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
    @Query("Select max(i.invoiceNumber) from Invoice i")
    int getMaxInvoiceNumber();

    @Transactional
    @Modifying
    @Query(value = "Delete from Invoice i where i.id in(?1)", nativeQuery = true)
    void deleteInvoicesByIds(List<Long> invoiceIds);

    @Query("Select i from Invoice i where i.invoiceNumber=?1")
    Optional<Invoice> findByInvoiceNumber(int invoiceNumber);

}
