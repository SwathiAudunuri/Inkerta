package com.tecnics.einvoice.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.InvoiceQueryRepo;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.InvoiceQuery;

import com.tecnics.einvoice.response.TransactionResponse;
@Component
public class InvoiceQueryService extends BaseService{

	@Autowired
	InvoiceQueryRepo invoiceQueryRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	

	public List<InvoiceQuery> findAll() {
		return (List<InvoiceQuery>) invoiceQueryRepo.findAll();
	}
	
	public List<InvoiceQuery> findById(int id) {
		return (List<InvoiceQuery>) invoiceQueryRepo.findById(id);
	}
	
	public List<InvoiceQuery> findByParentQueryRefId(String parentQueryRefId) {
		return (List<InvoiceQuery>) invoiceQueryRepo.findByParentQueryRefId(parentQueryRefId);
	}
	
	public List<InvoiceQuery> findByDocumentRefId(String documentRefId) {
		return (List<InvoiceQuery>) invoiceQueryRepo.findByDocumentRefId(documentRefId);
	}
	
	
	public List<InvoiceQuery> findByDocumentRefIdAndQueryRefId(String documentRefId,String queryRefId) {
		return (List<InvoiceQuery>) invoiceQueryRepo.findByDocumentRefIdAndQueryRefId(documentRefId,queryRefId);
	}
	
	public List<InvoiceQuery> findByDocumentRefIdAndParentQueryRefId(String documentRefId,String parentQueryRefId) {
		return (List<InvoiceQuery>) invoiceQueryRepo.findByDocumentRefIdAndParentQueryRefId(documentRefId,parentQueryRefId);
	}
	
	public long findCountByDocumentRefId(String documentRefId)
	{
	
		return invoiceQueryRepo.count(documentRefId);
	}

	public InvoiceQuery update(InvoiceQuery invoiceQuery) {
		return invoiceQueryRepo.save(invoiceQuery);
	}

	public TransactionResponse save(InvoiceQuery obj, String user) {
		int failCount =0,successCount=0;
		System.out.println("Inside save of invoiceQueries");
			TransactionResponse invoiceQueryResponse = new TransactionResponse();
		try {
			InvoiceQuery response = invoiceQueryRepo.save(obj);
			invoiceQueryResponse.setRefid(response.getId());			
			successCount++;
		}
		
		catch(Exception ex){
			failCount ++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("TODO");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(user);
			errorLogRepo.save(error);
		}
		invoiceQueryResponse.setFailureTransactionsCount(failCount);
		invoiceQueryResponse.setSuccessTransactionsCount(successCount);
		invoiceQueryResponse.setTotalTransactionsCount(failCount+successCount);
		return invoiceQueryResponse;
	}

	


}
