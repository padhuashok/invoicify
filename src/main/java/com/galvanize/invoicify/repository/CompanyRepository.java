package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends JpaRepository<Company,Long> {

    }
