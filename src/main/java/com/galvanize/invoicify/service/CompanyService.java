package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.dto.CompanyDTO;
import com.galvanize.invoicify.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }
//    public List<CompanyDTO> getCompany(){
//        return companyRepository
//                .findAll()
//                .stream()
//                .map(entity->(new CompanyDTO(entity
//                        .getId(),entity.getName(),entity
//                        .getAddress(),entity.getContactName(),entity.getContactTitle()
//                        ,entity.getContactPhoneNumber()))).collect(Collectors.toList());
//    }


    public Company save(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setContactName(companyDTO.getContactName());
        company.setContactTitle(companyDTO.getContactTitle());
        company.setContactPhoneNumber(companyDTO.getContactPhoneNumber());
        return companyRepository.save(company);
    }

    public Company add(Company company) {
        return companyRepository.save(company);
    }
}
