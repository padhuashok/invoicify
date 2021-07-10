package com.galvanize.invoicify.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Entity
@Getter
@Setter
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String address;
    private String contactName;
    private String contactTitle;
    private String contactPhoneNumber;

    @OneToMany(mappedBy = "company")
    private List<Invoice> invoices;
}
