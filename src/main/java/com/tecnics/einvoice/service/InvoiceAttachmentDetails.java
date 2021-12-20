/**
 * 
 */
package com.tecnics.einvoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.InvoiceAttachmentDetailsRepo;

@Component
public class InvoiceAttachmentDetails {

	@Autowired
	private InvoiceAttachmentDetailsRepo attachmentDetailsRepo;
	
	
	
}
