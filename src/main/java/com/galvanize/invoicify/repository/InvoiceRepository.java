package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("Select max(i.invoiceNumber) from Invoice i")
    int getMaxInvoiceNumber();

    List<Invoice> getInvoicesByPageNumber(Pageable pageable);
}
