package com.galvanize.invoicify.apitest;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.dto.CompanyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void addCompany(){
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(1);
        companyDTO.setAddress("Texas");
        companyDTO.setContactName("Kieu");
        companyDTO.setContactTitle("ItemsPayable");
        companyDTO.setName("AlphaCompany");
        companyDTO.setContactPhoneNumber("9142362814");
    }
}
