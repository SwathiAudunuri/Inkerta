package com.tecnics.einvoice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceNotesRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceNotesRepo.InvoiceNotesResults;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.ExternalInvoiceNotes;
import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.util.APIError;

@Component
public class ExternalInvoiceNotesService extends BaseService {
	


		@Autowired
		ExternalInvoiceNotesRepo externalInvoiceNotesRepo;

		
		@Autowired
		ErrorLogRepo errorLogRepo;
		
		public ResponseEntity<ResponseMessage> fetchNotesByPartnerIdAndDocumenrRefId(String partnerId,String documentRefId) {
			List<InvoiceNotesResults> invoiceNotesResults=externalInvoiceNotesRepo.fetchNotesByPartnerIdAndDocumenrRefId(partnerId, documentRefId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoiceNotesResults));
			
		} 

		public ResponseEntity<ResponseMessage> save(ExternalInvoiceNotes obj, UserLoginDetails userObj) {
			int failCount =0,successCount=0;
			System.out.println("Inside save of InvoiceNotes");
				TransactionResponse invoiceNotesResponse = new TransactionResponse();
			try {
				ExternalInvoiceNotes response = externalInvoiceNotesRepo.save(obj);
				invoiceNotesResponse.setRefid(response.getNotesId());			
				successCount++;
			}
			
			catch(Exception ex){
				failCount ++;
				ErrorLog error = new ErrorLog();
				error.setComponentName("ExternalInvoiceNotes");
				error.setError(ex.getMessage());
				error.setErrorMessage(getStackTrace(ex));
				error.setModule("Save");
				error.setSource("Java");
				error.setUserId(userObj.getUserId());
				errorLogRepo.save(error);
				return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_NOTES_SAVE_ERROR.getKey(),Ex.formatMessage(Ex.INV_NOTES_SAVE_ERROR.getKeyMessage()),Ex.INV_NOTES_SAVE_ERROR.getKeyMessage())));
				
			}
			invoiceNotesResponse.setFailureTransactionsCount(failCount);
			invoiceNotesResponse.setSuccessTransactionsCount(successCount);
			invoiceNotesResponse.setTotalTransactionsCount(failCount+successCount);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoiceNotesResponse));
		}

}

