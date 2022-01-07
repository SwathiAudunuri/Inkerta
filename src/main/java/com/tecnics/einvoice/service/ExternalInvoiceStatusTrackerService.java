package com.tecnics.einvoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceStatusTrackerRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceStatusTrackerRepo.ExternalInvoiceStatusTrackerResults;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.ExternalInvoiceStatusTracker;

import com.tecnics.einvoice.response.TransactionResponse;

@Component
public class ExternalInvoiceStatusTrackerService extends BaseService {

	@Autowired
	ExternalInvoiceStatusTrackerRepo externalInvoiceStatusTrackerRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	public List<ExternalInvoiceStatusTracker> findByDocumentRefId(String documentRefId) {
		return (List<ExternalInvoiceStatusTracker>) externalInvoiceStatusTrackerRepo.findByDocumentRefId(documentRefId);
	}
	
	public List<ExternalInvoiceStatusTrackerResults> fetchByDocumentRefId(String documentRefId) {
		return (List<ExternalInvoiceStatusTrackerResults>) externalInvoiceStatusTrackerRepo.fetchByDocumentRefId(documentRefId);
	}
	
	
	
	public ExternalInvoiceStatusTracker update(ExternalInvoiceStatusTracker externalInvoiceStatusTracker) {
		return externalInvoiceStatusTrackerRepo.save(externalInvoiceStatusTracker);
	}

	public TransactionResponse save(ExternalInvoiceStatusTracker obj, String user) {
		int failCount =0,successCount=0;
		System.out.println("Inside save of InvoiceStatusTracker Service");
			TransactionResponse invoiceStatusTrackerResponse = new TransactionResponse();
		try {
			ExternalInvoiceStatusTracker response = externalInvoiceStatusTrackerRepo.save(obj);
			invoiceStatusTrackerResponse.setRefid(response.getInvoiceStatusTrackerId());			
			successCount++;
		}
		
		catch(Exception ex){
			failCount ++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("ExternalInvoiceStatusTrackerService");
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

