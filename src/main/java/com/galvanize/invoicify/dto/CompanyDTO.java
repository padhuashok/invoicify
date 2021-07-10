package com.galvanize.invoicify.dto;


import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private long id;
    private String name;
    private String address;
    private String contactName;
    private String contactTitle;
    private String contactPhoneNumber;

}
