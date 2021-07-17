package com.galvanize.invoicify.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.domain.Invoice;
import com.galvanize.invoicify.dto.CompanyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.StatusAssertions;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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
    @Autowired
    ObjectMapper objectMapper;

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
                .andExpect(status().isOk());
    }

    @Test
    public void testPostCompany() throws Exception {
        CompanyDTO companyDTO = new CompanyDTO(
                1, "Company1", "NewYork", "Achyut", "CEO", "3124445555"
        );
        mvc.perform(post("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(companyDTO)))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Company1"))
                .andExpect(jsonPath("$.data.address").value("NewYork"))
                .andExpect(jsonPath("$.data.contactName").value("Achyut"))
                .andExpect(jsonPath("$.data.contactTitle").value("CEO"))
                .andExpect(jsonPath("$.data.contactPhoneNumber").value("3124445555"));
    }
}
