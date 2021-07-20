package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("Select max(i.invoiceNumber) from Invoice i")
    Optional<Integer> getMaxInvoiceNumber();

    //List<Invoice> getInvoicesByPageNumber(Pageable pageable);
}
