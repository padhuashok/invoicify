package com.galvanize.invoicify.repository;

import com.galvanize.invoicify.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CompanyRepository extends JpaRepository<Company,Long> {
    Company findByName(String name);

//    @Transactional
//    @Modifying
//    @Query("Update Company p set p.name = :name where p.id = :id")
//    void updateCompanyName(@Param("name") String name, @Param("id") Long id);

//    @Transactional
//    @Modifying
//    @Query("Update Company p set p.breed = :breed where p.id = :id")
//    void updateContactName(@Param("contactName") String contactName, @Param("id") Long id);
//    }


//    @Transactional
//    @Modifying
//    @Query("Update Company p set p.breed = :breed, p.name = :name where p.id = :id")
//    void updateContactName(@Param("contactName") String contactName, @Param("name") String name, @Param("id") Long id);
}
