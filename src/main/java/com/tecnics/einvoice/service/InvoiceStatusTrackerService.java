package com.tecnics.einvoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.InvoiceStatusTrackerRepo;
import com.tecnics.einvoice.Repo.InvoiceStatusTrackerRepo.InvoiceStatusTrackerResults;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.InvoiceStatusTracker;

import com.tecnics.einvoice.response.TransactionResponse;

@Component
public class InvoiceStatusTrackerService extends BaseService {

	@Autowired
	InvoiceStatusTrackerRepo invoiceStatusTrackerRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	public List<InvoiceStatusTracker> findByDocumentRefId(String documentRefId) {
		return (List<InvoiceStatusTracker>) invoiceStatusTrackerRepo.findByDocumentRefId(documentRefId);
	}
	
	public List<InvoiceStatusTrackerResults> fetchAccessibleStatus(String partner_id, String documentRefId) {
		return invoiceStatusTrackerRepo.findByVisibleToPartneridAndDocumentRefId(partner_id, documentRefId);
	}
	
	
	public InvoiceStatusTracker update(InvoiceStatusTracker invoiceStatusTracker) {
		return invoiceStatusTrackerRepo.save(invoiceStatusTracker);
	}

	public TransactionResponse save(InvoiceStatusTracker obj, String user) {
		int failCount =0,successCount=0;
		System.out.println("Inside save of InvoiceStatusTracker Service");
			TransactionResponse invoiceStatusTrackerResponse = new TransactionResponse();
		try {
			InvoiceStatusTracker response = invoiceStatusTrackerRepo.save(obj);
			invoiceStatusTrackerResponse.setRefid(response.getInvoiceStatusTrackerId());			
			successCount++;
		}
		
		catch(Exception ex){
			failCount ++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("InvoiceStatusTracker");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(user);
			errorLogRepo.save(error);
		}
		invoiceStatusTrackerResponse.setFailureTransactionsCount(failCount);
		invoiceStatusTrackerResponse.setSuccessTransactionsCount(successCount);
		invoiceStatusTrackerResponse.setTotalTransactionsCount(failCount+successCount);
		return invoiceStatusTrackerResponse;
	}
}










