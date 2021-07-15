package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.dto.CompanyDTO;
import com.galvanize.invoicify.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository)
    {
        this.companyRepository = companyRepository;
    }
    public List<Company> getCompany(){
        return companyRepository.findAll();
    }

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
