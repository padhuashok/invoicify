package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.Invoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("Select createdDate,invoiceStatus,invoiceTotal from Invoice order by createdDate asc")
    List<Invoice> getInvoiceList(Pageable pageWith10Elements);

}
