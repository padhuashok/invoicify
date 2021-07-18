package com.galvanize.invoicify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class InvoicifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoicifyApplication.class, args);
	}

}
