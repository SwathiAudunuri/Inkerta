package com.tecnics.einvoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.InvoiceAuditRepo;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.InvoiceAudit;

import com.tecnics.einvoice.response.TransactionResponse;
@Component
public class InvoiceAuditService extends BaseService {

	@Autowired
	InvoiceAuditRepo invoiceAuditRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	public List<InvoiceAudit> findByRefId(String refId) {
		return (List<InvoiceAudit>) invoiceAuditRepo.findByRefId(refId);
	}
	
	public InvoiceAudit update(InvoiceAudit invoiceAudit) {
		return invoiceAuditRepo.save(invoiceAudit);
	}

	public TransactionResponse save(InvoiceAudit obj, String user) {
		int failCount =0,successCount=0;
		System.out.println("Inside save of invoiceAudit");
			TransactionResponse invoiceAuditResponse = new TransactionResponse();
		try {
			InvoiceAudit response = invoiceAuditRepo.save(obj);
			invoiceAuditResponse.setRefid(response.getAuditId());			
			successCount++;
		}
		
		catch(Exception ex){
			failCount ++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("InvoiceAudit");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(user);
			errorLogRepo.save(error);
		}
		invoiceAuditResponse.setFailureTransactionsCount(failCount);
		invoiceAuditResponse.setSuccessTransactionsCount(successCount);
		invoiceAuditResponse.setTotalTransactionsCount(failCount+successCount);
		return invoiceAuditResponse;
	}
	
}




