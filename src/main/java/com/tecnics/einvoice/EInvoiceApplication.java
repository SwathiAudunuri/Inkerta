package com.tecnics.einvoice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class EInvoiceApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("lang/res");
		//System.out.println(messageSource.getMessage("hello", null, Locale.ITALIAN));
		SpringApplication.run(EInvoiceApplication.class, args);
	}
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	        return builder.sources(EInvoiceApplication.class);
	    }
}
