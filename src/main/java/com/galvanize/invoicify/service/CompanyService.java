package com.galvanize.invoicify.service;

import com.galvanize.invoicify.domain.Company;
import com.galvanize.invoicify.dto.CompanyDTO;
import com.galvanize.invoicify.repository.CompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository)
    {
        this.companyRepository = companyRepository;
    }
    public List<Company> getCompany(){
        return (List<Company>) companyRepository.findAll();
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

    public Company add(Company company) {return companyRepository.save(company);
    }


    public Company updateCompany(CompanyDTO companyDTO, Company companyEntity) {


        companyEntity.setName(companyDTO.getName());
        companyEntity.setAddress(companyDTO.getAddress());
        companyEntity.setContactName(companyDTO.getContactName());
        companyEntity.setContactTitle(companyDTO.getContactTitle());
        companyEntity.setContactPhoneNumber(companyDTO.getContactPhoneNumber());

        return companyRepository.save(companyEntity);
    }

    public Company findByName(String name) {
        return companyRepository.findByName(name).orElse(null);

    }

    public Company findById(long id) {
        return companyRepository.findById(id).orElse(null);
    }
}
