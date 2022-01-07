
package com.tecnics.einvoice.service;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.CompanyRatingRepo;
import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceDocumentDetailsRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceStatusTrackerRepo;
import com.tecnics.einvoice.Repo.InvoiceDocumentDetailRepo;
import com.tecnics.einvoice.Repo.InvoicePaidDetailsRepo;
import com.tecnics.einvoice.Repo.InvoiceStatusTrackerRepo;
import com.tecnics.einvoice.Repo.CompanyRatingRepo.PaidInvoicesForCompanyRatingResults;
import com.tecnics.einvoice.entity.CompanyRating;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.ExternalInvoiceDocumentDetails;
import com.tecnics.einvoice.entity.ExternalInvoiceStatusTracker;
import com.tecnics.einvoice.entity.InvoicePaidDetails;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.entity.InvoiceStatusTracker;
import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.util.APIError;

@Component
public class InvoicePaidDetailsService extends BaseService {


		@Autowired
		InvoicePaidDetailsRepo invoicePaidDetailsRepo;
		
		@Autowired
		InvoiceStatusTrackerRepo invoiceStatusTrackerRepo;
		
		@Autowired
		ExternalInvoiceStatusTrackerRepo externalInvoiceStatusTrackerRepo;	
		
		
		@Autowired
		ExternalInvoiceDocumentDetailsRepo externalInvoiceDocumentDetailsRepo;
		
		@Autowired
		ExternalInvoiceDocumentDetailsService externalInvoiceDocumentDetailsService;
		
		@Autowired
		InvoiceDocumentDetailService detailService;
		
		

		@Autowired
		InvoiceDocumentDetailRepo invoicedetailRepo;
		
		@Autowired
		CompanyRatingService companyRatingService;
		
		@Autowired
		CompanyRatingRepo companyRatingRepo;

		
		@Autowired
		ErrorLogRepo errorLogRepo;
		
		public ResponseEntity<ResponseMessage> fetchPaidDetailsByPaidToPartnerIdAndDocumentRefId(String paidto_partner_id,String documentRefId) {
			List<InvoicePaidDetails> invoicePaidDetails=invoicePaidDetailsRepo.findByPaidToPartnerIdAndDocumentRefId(paidto_partner_id, documentRefId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoicePaidDetails));
			
		} 
		
		public ResponseEntity<ResponseMessage> fetchPaidDetailsByDocumentRefId(String documentRefId) {
			List<InvoicePaidDetails> invoicePaidDetails=invoicePaidDetailsRepo.findByDocumentRefId(documentRefId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoicePaidDetails));
			
		} 

		public ResponseEntity<ResponseMessage> save(InvoicePaidDetails obj, UserLoginDetails userObj) {
			
			System.out.println("Inside save of InvoicePaidDetailsService");
			InvoicePaidDetails invoicePaidDetails =null;
				
			try {
				InvoiceStatusTracker invoiceStatusTracker=null;
				ExternalInvoiceStatusTracker externalInvoiceStatusTracker=null;
				
				obj.setPaidToPartnerId(userObj.getPartnerId());
			
				
				if(obj.isExternalInvoice())
				{
				ExternalInvoiceDocumentDetails externalInvoiceDocumentDetails=externalInvoiceDocumentDetailsService.getInvoiceDetails(obj.getDocumentRefId());
				obj.setPaidByPartnerId(externalInvoiceDocumentDetails.getRecipient_partner_id());
				}
				else if	(!obj.isExternalInvoice())
				{
				InvoiceRequestModel irm = detailService.getInvoiceDetails(obj.getDocumentRefId());
				obj.setPaidByPartnerId(irm.getInvoiceDetails().getCustomer_partner_id());
				}
				
				invoicePaidDetails=invoicePaidDetailsRepo.save(obj);
				
					if(obj.isExternalInvoice())
						externalInvoiceStatusTracker=createExternalInvoiceStatusTracker(obj,userObj);				
					else
						invoiceStatusTracker=createInternalInvoiceStatusTracker(obj,userObj); 
			
				
				// set the company rating
				
				System.out.println("Paid By Partner Id = " + obj.getPaidByPartnerId());
				System.out.println("Paid To Partner Id = " + obj.getPaidToPartnerId());
				 
				
				List<PaidInvoicesForCompanyRatingResults> paidInvoicesForCompanyRatingResults=companyRatingRepo.fetchPaidInvoicesForExternalCompanyRating(userObj.getPartnerId(),obj.getPaidByPartnerId() );
				System.out.println(" PaidInvoicesForCompanyRatingResults size = " + paidInvoicesForCompanyRatingResults.size());
				String rating=companyRatingService.getCompanyRating(userObj,paidInvoicesForCompanyRatingResults,new BigDecimal(3));
				
				
				System.out.println("Rating of the company =" + rating);
				
				CompanyRating companyRating=companyRatingRepo.findByToPartnerIdAndFromPartnerId(obj.getPaidByPartnerId(),userObj.getPartnerId());
				
				//updating the Compnay rating
				
				if(companyRating==null)
				{
					companyRating=new CompanyRating();
					companyRating.setFromPartnerId(userObj.getPartnerId());
					companyRating.setToPartnerId(obj.getPaidByPartnerId());
								
				}
				
				companyRating.setLast_updated_on(new Timestamp(System.currentTimeMillis()));
				companyRating.setUpdated_by("system");
				companyRating.setSystemRating(rating);
				
				companyRatingRepo.save(companyRating);
			
					
			}
			
			catch(Exception ex){
				ex.printStackTrace();
				ErrorLog error = new ErrorLog();
				error.setComponentName("InvoicePaidDetailsService");
				error.setError(ex.getMessage());
				error.setErrorMessage(getStackTrace(ex));
				error.setModule("Save");
				error.setSource("Java");
				error.setUserId(userObj.getUserId());
				errorLogRepo.save(error);
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("InvoicePaidDetailsService Save Error", "InvoicePaidDetailsService save Error :","InvoicePaidDetailsService Save" )));
				
			}

			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoicePaidDetails));
		}
		
		private InvoiceStatusTracker createInternalInvoiceStatusTracker(InvoicePaidDetails obj,UserLoginDetails userObj)
		{
			// updating invoice Status Tracker
			
			InvoiceStatusTracker invoiceStatusTracker=new InvoiceStatusTracker();
			try {
			System.out.println("Creating internal invoicestatustracker object for the docRefid =  "+obj.getDocumentRefId());
			
			
			invoiceStatusTracker.setActionBy(userObj.getUserId());
			
				invoiceStatusTracker.setAction(obj.getStatus());
				invoiceStatusTracker.setActionComments(obj.getAction_comments());			
			invoiceStatusTracker.setDocumentRefId(obj.getDocumentRefId());
			
			invoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
			invoiceStatusTracker.setActionType("STATUS CHANGE");
			//invoiceStatusTracker.setSource("Vendor");
			invoiceStatusTracker=invoiceStatusTrackerRepo.save(invoiceStatusTracker);
			System.out.println("internal invoicestatustracker status set on the docRefid =  "+invoiceStatusTracker.getDocumentRefId());
			
			int cnt=invoicedetailRepo.setInvoice_statusForInvoiceDocumentDetail(invoiceStatusTracker.getAction(), invoiceStatusTracker.getDocumentRefId());
			
			} catch (Exception e) {
				System.out.println("Error while saving  internal invoicestatustracker" + e.getMessage());
				e.printStackTrace();
				log.logErrorMessage(e.getMessage(), e);
				return null;
				
			}
			return invoiceStatusTracker;
		}
		
		
		private ExternalInvoiceStatusTracker createExternalInvoiceStatusTracker(InvoicePaidDetails obj, UserLoginDetails userObj)
		{
			// updating external invoice Status Tracker
			ExternalInvoiceStatusTracker externalInvoiceStatusTracker=new ExternalInvoiceStatusTracker();
			
			try {
			System.out.println("Creating ExternalInvoiceStatusTracker object ");
			
			externalInvoiceStatusTracker.setActionBy(userObj.getUserId());
			externalInvoiceStatusTracker.setAction(obj.getStatus());
				externalInvoiceStatusTracker.setActionComments(obj.getAction_comments());
			
			externalInvoiceStatusTracker.setDocumentRefId(obj.getDocumentRefId());
			
			externalInvoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
			externalInvoiceStatusTracker.setActionType("STATUS CHANGE");
			
			externalInvoiceStatusTracker=externalInvoiceStatusTrackerRepo.save(externalInvoiceStatusTracker);
			externalInvoiceDocumentDetailsRepo.setInvoice_statusForInvoiceDocumentDetail(obj.getStatus(),obj.getDocumentRefId() );
			
			} catch (Exception e) {
				System.out.println("Error while saving  externalInvoiceStatusTracker" + e.getMessage());
				e.printStackTrace();
				log.logErrorMessage(e.getMessage(), e);
				return null;
				
			}
			return externalInvoiceStatusTracker;
		}

}

