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
import java.util.Optional;

@RestController
@Validated
public class CompanyController {


    CompanyService companyService;

    public CompanyController(CompanyService companyService)

    {
        this.companyService = companyService;
    }

    @GetMapping("/company")
    public ResponseEntity<GeneralResponse<List<Company>>> getCompany()
    {
        return RestUtils.buildResponse(companyService.getCompany());
    }

    @GetMapping("/company/{id}")
    public Optional<Company> getById(@PathVariable(value = "id") long id) {
        return companyService.get(id);
    }

    @PostMapping("/company")
    public ResponseEntity<GeneralResponse<Company>> createCompany(@Valid @RequestBody CompanyDTO companyDTO)
    {

       if(companyDTO.getContactPhoneNumber().matches("\\d{10}"))
        return RestUtils.buildResponse(companyService.save(companyDTO));
        else
          return RestUtils.buildResponse(HttpStatus.BAD_REQUEST, "ContactPhoneNumber should be 10 digit with numeric values", null);
    }

    @PutMapping("/company")
    public ResponseEntity<GeneralResponse<Company>> getCompany(@RequestBody CompanyDTO companyDTO)
    {
        return RestUtils.buildResponse(companyService.save(companyDTO));
    }
    @PatchMapping("/company")
    @ResponseStatus(HttpStatus.OK)
    public void updateCompany(@RequestBody CompanyDTO companyDTO){
        companyService.updateCompany(companyDTO);
    }

/*@PatchMapping("/company/{id}")
public ResponseEntity<Company> updateCompanies(@RequestBody Company company, @PathVariable long id) {
   Company result = companyService.updateCompanies(company, id).orElse(null);
    if(result != null)
        return new ResponseEntity(result, HttpStatus.OK);
    return new ResponseEntity(HttpStatus.NOT_FOUND);
}*/
}
