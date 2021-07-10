package com.galvanize.invoicify.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.List;
@Entity
@Getter
@Setter
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    public String name;
    @NotNull
    public String address;
    @NotNull
    public String contactName;
    @NotNull
    public String contactTitle;
    @NotNull
    public String contactPhoneNumber;

    @OneToMany(mappedBy = "company")
    private List<Invoice> invoices;
}
