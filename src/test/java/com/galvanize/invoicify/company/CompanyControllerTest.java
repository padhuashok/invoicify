package com.galvanize.invoicify.company;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.dto.CompanyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private MockMvc mvc;

//    @Test
//    public void addCompany(){
//        CompanyDTO companyDTO = new CompanyDTO();
//        companyDTO.setId(1);
//        companyDTO.setAddress("Texas");
//        companyDTO.setContactName("Kieu");
//        companyDTO.setContactTitle("ItemsPayable");
//        companyDTO.setName("AlphaCompany");
//        companyDTO.setContactPhoneNumber("9142362814");
//    }

    @Test
    public void testGetCompany() throws Exception {
        mvc.perform(get("/company"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(0));
    }
}
