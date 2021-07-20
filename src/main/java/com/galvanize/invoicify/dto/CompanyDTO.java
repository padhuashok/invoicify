package com.galvanize.invoicify.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.galvanize.invoicify.domain.Company;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDTO {
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private String contactName;
    @NotNull
    private String contactTitle;
    @NotNull
    @Pattern(regexp = "[0-9]*", message = "ContactPhoneNumber should be 10 digit with numeric values")
    private String contactPhoneNumber;

    public CompanyDTO(Company company){
        this.id = company.getId();
        this.address = company.getAddress();
        this.contactName = company.getContactName();
        this.contactTitle = company.getContactTitle();
        this.name = company.getName();
        this.contactPhoneNumber = company.getContactPhoneNumber();
    }
}