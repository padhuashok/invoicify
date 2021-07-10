package com.galvanize.invoicify.servicetest;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.repository.CompanyRepository;
import com.galvanize.invoicify.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyService companyService;

    @Test
    void testAddCompany(){
        Company company= new Company();
        company.name = "Alpha Corp.";
        company.address="Mason, OH ";
        company.contactName ="Keiu";
        company.contactPhoneNumber ="123456789";
        company.contactTitle="ItemPayment";

        when(companyRepository.save(company)).thenReturn(company);

        Company currentResult=companyService.add(company);
        assertEquals(currentResult,company);
    }
}
