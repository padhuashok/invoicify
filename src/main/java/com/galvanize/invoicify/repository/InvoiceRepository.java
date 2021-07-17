package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("Select i from Invoice i where i.invoiceNumber=?1")
    Optional<Invoice> findByInvoiceNumber(int invoiceNumber);

    @Query("Select max(i.invoiceNumber) from Invoice i")
    public int getMaxInvoiceNumber();
}
