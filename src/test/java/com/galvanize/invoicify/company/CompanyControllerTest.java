package com.galvanize.invoicify.company;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.invoicify.dto.CompanyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class CompanyControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;
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
                .andExpect(jsonPath("$.data.contactPhoneNumber").value("3124445555"))
                .andDo((document("Add Companies to POST", responseFields(
                        fieldWithPath("status").description("HTTP Status"),
                        fieldWithPath("message").description("Message"),
                        fieldWithPath("data.id").description("Company ID"),
                        fieldWithPath("data.name").description("Name of the Company"),
                        fieldWithPath("data.address").description("Name of the Company Address"),
                        fieldWithPath("data.contactName").description("Name of the Contact"),
                        fieldWithPath("data.contactTitle").description("Title of the Contact"),
                        fieldWithPath("data.contactPhoneNumber").description("Contact PhoneNumber"),
                        fieldWithPath("data.invoices").description("Company Invoices")))));
    }
}