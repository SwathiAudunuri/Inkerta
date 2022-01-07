package com.tecnics.einvoice.service;

import java.sql.Timestamp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceDocumentDetailsRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceStatusTrackerRepo;
import com.tecnics.einvoice.Repo.InvoiceDocumentDetailRepo;
import com.tecnics.einvoice.Repo.InvoiceStatusTrackerRepo;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.ExternalInvoiceStatusTracker;
import com.tecnics.einvoice.entity.InvoiceStatusTracker;
import com.tecnics.einvoice.model.InvoiceForwardModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;

@Component
public class InvoiceForwardService extends BaseService {

	@Autowired
	InvoiceStatusTrackerRepo invoiceStatusTrackerRepo;
	
	@Autowired
	InvoiceDocumentDetailRepo invoicedetailRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	@Autowired
	ExternalInvoiceStatusTrackerRepo externalInvoiceStatusTrackerRepo;
	
	@Autowired
	ExternalInvoiceDocumentDetailsRepo externalInvoiceDocumentDetailsRepo;
	
	public ResponseEntity<ResponseMessage> save(InvoiceForwardModel invoiceForwardModelObj, UserLoginDetails userObj) {
		int failCount =0,successCount=0;
		System.out.println("Inside save of InvoiceForwardService Service");
			TransactionResponse invoiceStatusTrackerResponse = new TransactionResponse();
			
		try {
			
			int cnt=invoicedetailRepo.setAssigned_To_ForInvoiceDocumentDetail(invoiceForwardModelObj.getActionTo(), invoiceForwardModelObj.getDocumentRefId());
			System.out.println("No of Rows Updated in InvoiceDocumentDetail AssignedTo Update= " + cnt);
			
			InvoiceStatusTracker invoiceStatusTracker=new InvoiceStatusTracker();			
			invoiceStatusTracker.setActionBy(userObj.getUserId());
			invoiceStatusTracker.setActionTo(invoiceForwardModelObj.getActionTo());
			invoiceStatusTracker.setAction(invoiceForwardModelObj.getAction());
			invoiceStatusTracker.setActionComments(invoiceForwardModelObj.getActionComments());
			invoiceStatusTracker.setDocumentRefId(invoiceForwardModelObj.getDocumentRefId());			
			invoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
			invoiceStatusTracker.setActionType("Workflow Forward");
			invoiceStatusTracker.setSource("Customer");	
			invoiceStatusTracker.setVisibleToPartnerid(userObj.getPartnerId());
			InvoiceStatusTracker response = invoiceStatusTrackerRepo.save(invoiceStatusTracker);
			invoiceStatusTrackerResponse.setRefid(response.getInvoiceStatusTrackerId());			
			successCount++;
		}
		
		catch(Exception ex){
			failCount ++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("InvoiceForwardService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
		}
		invoiceStatusTrackerResponse.setFailureTransactionsCount(failCount);
		invoiceStatusTrackerResponse.setSuccessTransactionsCount(successCount);
		invoiceStatusTrackerResponse.setTotalTransactionsCount(failCount+successCount);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoiceStatusTrackerResponse));
	
	}
	
	
	
	
	public ResponseEntity<ResponseMessage> release(InvoiceForwardModel invoiceForwardModelObj, UserLoginDetails userObj) {
		int failCount =0,successCount=0;
		System.out.println("Inside release of InvoiceForwardService Service");
			TransactionResponse invoiceStatusTrackerResponse = new TransactionResponse();
			
		try {
			
			int cnt=invoicedetailRepo.setAssigned_To_Null_ForInvoiceDocumentDetail(invoiceForwardModelObj.getDocumentRefId());
			System.out.println("No of Rows Updated in InvoiceDocumentDetail release Update= " + cnt);
			
			InvoiceStatusTracker invoiceStatusTracker=new InvoiceStatusTracker();			
			invoiceStatusTracker.setActionBy(userObj.getUserId());
			invoiceStatusTracker.setAction("Release to Queue");
			invoiceStatusTracker.setActionComments(invoiceForwardModelObj.getActionComments());
			invoiceStatusTracker.setDocumentRefId(invoiceForwardModelObj.getDocumentRefId());			
			invoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
			invoiceStatusTracker.setActionType("Workflow Release");
			invoiceStatusTracker.setSource("Customer");	
			invoiceStatusTracker.setVisibleToPartnerid(userObj.getPartnerId());
			InvoiceStatusTracker response = invoiceStatusTrackerRepo.save(invoiceStatusTracker);
			invoiceStatusTrackerResponse.setRefid(response.getInvoiceStatusTrackerId());			
			successCount++;
		}
		
		catch(Exception ex){
			failCount ++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("InvoiceForwardService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
		}
		invoiceStatusTrackerResponse.setFailureTransactionsCount(failCount);
		invoiceStatusTrackerResponse.setSuccessTransactionsCount(successCount);
		invoiceStatusTrackerResponse.setTotalTransactionsCount(failCount+successCount);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoiceStatusTrackerResponse));
	
	}
	
	
	public ResponseEntity<ResponseMessage> externalinvoiceAssign(InvoiceForwardModel invoiceForwardModelObj, UserLoginDetails userObj) {
		int failCount =0,successCount=0;
		System.out.println("Inside externalinvoiceAssign of InvoiceForwardService Service");
			TransactionResponse invoiceStatusTrackerResponse = new TransactionResponse();
			
		try {
			
			int cnt=externalInvoiceDocumentDetailsRepo.setAssigned_To_ForInvoiceDocumentDetail(invoiceForwardModelObj.getActionTo(), invoiceForwardModelObj.getDocumentRefId());
			System.out.println("No of Rows Updated in External InvoiceDocumentDetail AssignedTo Update= " + cnt);
			
			ExternalInvoiceStatusTracker invoiceStatusTracker=new ExternalInvoiceStatusTracker();			
			invoiceStatusTracker.setActionBy(userObj.getUserId());
			invoiceStatusTracker.setActionTo(invoiceForwardModelObj.getActionTo());
			invoiceStatusTracker.setAction(invoiceForwardModelObj.getAction());
			invoiceStatusTracker.setActionComments(invoiceForwardModelObj.getActionComments());
			invoiceStatusTracker.setDocumentRefId(invoiceForwardModelObj.getDocumentRefId());			
			invoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
			invoiceStatusTracker.setActionType("Workflow Forward");
			ExternalInvoiceStatusTracker response = externalInvoiceStatusTrackerRepo.save(invoiceStatusTracker);
			invoiceStatusTrackerResponse.setRefid(response.getInvoiceStatusTrackerId());			
			successCount++;
		}
		
		catch(Exception ex){
			failCount ++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("InvoiceForwardService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("externalinvoiceAssign");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
		}
		invoiceStatusTrackerResponse.setFailureTransactionsCount(failCount);
		invoiceStatusTrackerResponse.setSuccessTransactionsCount(successCount);
		invoiceStatusTrackerResponse.setTotalTransactionsCount(failCount+successCount);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoiceStatusTrackerResponse));
	
	}
	
	public ResponseEntity<ResponseMessage> externalInvoiceRelease(InvoiceForwardModel invoiceForwardModelObj, UserLoginDetails userObj) {
		int failCount =0,successCount=0;
		System.out.println("Inside externalInvoiceRelease of InvoiceForwardService Service");
			TransactionResponse invoiceStatusTrackerResponse = new TransactionResponse();
			
		try {
			
			int cnt=externalInvoiceDocumentDetailsRepo.setAssigned_To_Null_ForInvoiceDocumentDetail(invoiceForwardModelObj.getDocumentRefId());
			System.out.println("No of Rows Updated in InvoiceDocumentDetail release Update= " + cnt);
			
			ExternalInvoiceStatusTracker invoiceStatusTracker=new ExternalInvoiceStatusTracker();			
			invoiceStatusTracker.setActionBy(userObj.getUserId());
			invoiceStatusTracker.setAction("Release to Queue");
			invoiceStatusTracker.setActionComments(invoiceForwardModelObj.getActionComments());
			invoiceStatusTracker.setDocumentRefId(invoiceForwardModelObj.getDocumentRefId());			
			invoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
			invoiceStatusTracker.setActionType("Workflow Release");	
			ExternalInvoiceStatusTracker response = externalInvoiceStatusTrackerRepo.save(invoiceStatusTracker);
			invoiceStatusTrackerResponse.setRefid(response.getInvoiceStatusTrackerId());			
			successCount++;
		}
		
		catch(Exception ex){
			failCount ++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("InvoiceForwardService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("externalInvoiceRelease");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
		}
		invoiceStatusTrackerResponse.setFailureTransactionsCount(failCount);
		invoiceStatusTrackerResponse.setSuccessTransactionsCount(successCount);
		invoiceStatusTrackerResponse.setTotalTransactionsCount(failCount+successCount);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoiceStatusTrackerResponse));
	
	}

}
