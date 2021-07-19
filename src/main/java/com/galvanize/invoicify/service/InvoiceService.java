package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.InvoiceDTO;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class InvoiceService {


    @Autowired
    InvoiceRepository invoiceRepo;

    public List<Invoice> findAllInvoices() {
        return invoiceRepo.findAll();
    }

    public void addInvoice(Invoice inv) {

        invoiceRepo.save(inv);
    }

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepo.save(invoice);
    }

    public InvoiceDTO calculateTotalCostAndSetStatus(InvoiceDTO invoiceDTO, Company company) {
        double totalCost = 0.0;
        for (InvoiceItem i :
                invoiceDTO.getInvoiceItems()) {
            totalCost += i.getItem().getTotalFee();
        }
        invoiceDTO.setInvoiceTotal(totalCost);
        invoiceDTO.setInvoiceStatus("UNPAID");
        invoiceDTO.setCreatedDate(LocalDate.now());
        int invoiceNumber = invoiceRepo.getMaxInvoiceNumber() + 1;
        invoiceDTO.setInvoiceNumber(invoiceNumber);
        Invoice invoice = invoiceDTO.getInvoiceItems().get(0).getInvoice();
        invoice.convertFromDTOAndCompany(invoiceDTO, company);
        invoice = saveInvoice(invoice);
        invoiceDTO =  invoice.convertToDTo();
        List<ItemDto> itemDToList =
                invoice.getInvoiceItems().stream()
                        .map(invoicItem -> {
                            Item item = invoicItem.getItem();
                            return new ItemDto(item);
                        }).collect(Collectors.toList());
        invoiceDTO.setItemDtoList(itemDToList);
        invoiceDTO.setInvoiceId(invoice.getId());
        return invoiceDTO;
    }

    public List<InvoiceDTO> getAllInvoicesByPageNum(int pageNum) {
        Pageable p = (Pageable) PageRequest.of(pageNum,10, Sort.DEFAULT_DIRECTION);
        return invoiceRepo.getInvoicesByPageNumber(p).stream().map(e -> e.convertToDTo()).collect(Collectors.toList());
    }
}



