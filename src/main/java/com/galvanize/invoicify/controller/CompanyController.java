package com.galvanize.invoicify.controller;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.dto.CompanyDTO;
import com.galvanize.invoicify.service.CompanyService;
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
    public Company createCompany(@RequestBody @Valid CompanyDTO companyDTO)
    {

        return companyService.save(companyDTO);
    }


    @PutMapping("/company")
    public Company getCompany(@RequestBody CompanyDTO companyDTO)
    {
        return companyService.save(companyDTO);
    }


}
