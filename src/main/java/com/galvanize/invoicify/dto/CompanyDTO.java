package com.galvanize.invoicify.dto;


import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @Pattern(regexp = "[0-9]*")
    private String contactPhoneNumber;

}
