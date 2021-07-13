package com.galvanize.invoicify.controller;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.dto.CompanyDTO;
import com.galvanize.invoicify.response.GeneralResponse;
import com.galvanize.invoicify.service.CompanyService;
import com.galvanize.invoicify.utils.RestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class CompanyController {


    CompanyService companyService;

    public CompanyController(CompanyService companyService)

    {
        this.companyService = companyService;
    }

//    @GetMapping("/company")
//    public List<CompanyDTO> getCompany()
//    {
//        return this.companyService.getCompany();
//    }

    @PostMapping("/company")
    public ResponseEntity<GeneralResponse<Company>> createCompany(@RequestBody @Valid CompanyDTO companyDTO)
    {

       if(companyDTO.getContactPhoneNumber().matches("\\d{10}"))
        return RestUtils.buildResponse(companyService.save(companyDTO));
        else
          return RestUtils.buildResponse(HttpStatus.BAD_REQUEST, "ContactPhoneNumber should be 10 digit with numeric values", null);
    }

    @PutMapping("/company")
    public Company getCompany(@RequestBody CompanyDTO companyDTO)
    {
        return companyService.save(companyDTO);
    }
}
