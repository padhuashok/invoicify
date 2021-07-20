package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.domain.InvoiceItem;
import com.galvanize.invoicify.domain.Item;
import com.galvanize.invoicify.dto.InvoiceDTO;
import com.galvanize.invoicify.dto.InvoiceItemId;
import com.galvanize.invoicify.dto.ItemDto;
import com.galvanize.invoicify.repository.InvoiceItemRepository;
import com.galvanize.invoicify.repository.InvoiceRepository;
import com.galvanize.invoicify.utils.InvoicifyStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    InvoiceRepository invoiceRepo;

    InvoiceItemRepository invoiceItemRepository;

    public InvoiceService(InvoiceRepository invoiceRepo, InvoiceItemRepository invoiceItemRepo){
        this.invoiceRepo = invoiceRepo;
        this.invoiceItemRepository = invoiceItemRepo;
    }
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

//    public void deleteAllExpiredAndPaidInvoice() {
//
//        Calendar cal = Calendar.getInstance();
//        Date today = cal.getTime();
//        cal.add(Calendar.YEAR, -1);
//        Date expiryDate = cal.getTime();
//        invoiceRepo.deleteAllExpiredAndPaidInvoice(expiryDate,"PAID");
//    }

    public List<InvoiceItemId> getInvoiceExpiredAndPaid() {
       // return invoiceRepo.getInvoiceExpiredAndPaid();
        List<InvoiceItem> invoiceItems = invoiceItemRepository.findAll();

        return invoiceItems.stream().map(invoiceItem  -> new InvoiceItemId(invoiceItem)).collect(Collectors.toList());
    }

    public void deleteByIds(List<Long> invoiceIds) {
        invoiceRepo.deleteInvoicesByIds(invoiceIds);
        //invoiceRepo.deleteAllById(invoiceIds);
    }


    public List<InvoiceDTO> getAllInvoicesByPageNum(Integer pageNo) {
        System.out.println(pageNo);
        Pageable p = PageRequest.of(pageNo,10, Sort.by(Sort.Direction.ASC,"CreatedDate"));
        return invoiceRepo.findAll(p).stream().map(e -> e.convertToDTo()).collect(Collectors.toList());
    }
}



