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
import java.util.Locale;
@RestController
@Validated
public class CompanyController {
    CompanyService companyService;
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    //    @GetMapping("/company")
//    public ResponseEntity<GeneralResponse<Company>> getCompany()
//    {
//        return RestUtils.buildResponse(companyService.getCompany());
//    }
    @GetMapping("/company")
    public ResponseEntity<Iterable<Company>> getCompany() {
        return new ResponseEntity(companyService.getCompany(), HttpStatus.OK);
    }
    @PostMapping("/company")
    public ResponseEntity<GeneralResponse<Company>> createCompany(@Valid @RequestBody CompanyDTO companyDTO) {
        if (companyDTO.getContactPhoneNumber().matches("\\d{10}")) {
            Company companyEntity = companyService.findByName(companyDTO.getName().trim().toLowerCase());
            if (companyEntity == null)
                return RestUtils.buildResponse(companyService.save(companyDTO));
            else return RestUtils.buildResponse(HttpStatus.BAD_REQUEST, "Duplicate company name", null);
        } else
            return RestUtils.buildResponse(HttpStatus.BAD_REQUEST, "ContactPhoneNumber should be 10 digit with numeric values", null);
    }
    @PutMapping("/company/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GeneralResponse<Company>> updateCompany(@RequestBody CompanyDTO companyDTO, @PathVariable Long id) {
        Company c =  companyService.findById(id);
        if (c != null) {
            Company companyEntity = companyService.findByName(companyDTO.getName().trim().toLowerCase());
            if ( companyEntity== null) {
                return RestUtils.buildResponse(companyService.updateCompany(companyDTO, c));
            }
            return RestUtils.buildResponse(HttpStatus.BAD_REQUEST, "Duplicate company name", null);
        }
        return RestUtils.buildResponse(HttpStatus.BAD_REQUEST, "company id does not exist", null);
    }
}