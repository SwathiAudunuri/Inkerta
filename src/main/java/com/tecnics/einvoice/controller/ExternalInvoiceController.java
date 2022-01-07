package com.tecnics.einvoice.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.entity.AttachmentDetails;
import com.tecnics.einvoice.entity.ExternalInvoiceDocumentDetails;

import com.tecnics.einvoice.entity.InvoiceStatus;

import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.ExternalInvoiceRequestModel;
import com.tecnics.einvoice.model.InvoiceMetaDataModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.ExternalInvoiceDocumentDetailsService;
import com.tecnics.einvoice.service.InvoiceStatusService;
import com.tecnics.einvoice.util.APIError;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ExternalInvoiceController extends BaseController {
	

	@Autowired
	ExternalInvoiceDocumentDetailsService externalInvoiceDocumentDetailsService;
	
	@Autowired
	InvoiceStatusService invoiceStatusService;
	
	@RequestMapping("/verifyExternalInvoiceController")  
    public String verify()  
    {  
		System.out.println("inside ExternalInvoiceController");
        return "The ExternalInvoiceController is up and running";  
    } 
	
	/***
	 * 
	 * @param ExternalInvoiceRequestModel
	 * @param token
	 * @param requestLength
	 * @return
	 */
	@PostMapping("/externalinvoice/invoiceUpload")
	public ResponseEntity<ResponseMessage> invoiceUpload(@RequestBody ExternalInvoiceRequestModel externalInvoiceRequestModel,
			@RequestHeader("authorization") String token, @RequestHeader("Content-Length") float requestLength) {
		UserLoginDetails userObj=getUserObjFromToken(token);
		if (requestLength > Constants.INV_UPLOAD_LIMIT)
			return ResponseEntity.ok().body(new ResponseMessage(
					new APIError(Ex.EXCEEDING_INV_LIMIT.getKey(), Ex.EXCEEDING_INV_LIMIT.getKeyMessage())));
		return externalInvoiceDocumentDetailsService.save(externalInvoiceRequestModel, userObj);
	}
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("/externalinvoice/getInvoiceDetails/{documentRefId}")
	public ResponseEntity<ResponseMessage> getInvoiceDetails(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		try {
			System.out.println("inside externalinvoice/getInvoiceDetails");
			ExternalInvoiceDocumentDetails response = externalInvoiceDocumentDetailsService.getInvoiceDetails(documentRefId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}

	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("/externalinvoice/getSupportingInvoiceDocumentDetails/{documentRefId}")
	public ResponseEntity<ResponseMessage> getSupportingInvoiceDocumentDetails(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			List<AttachmentDetails> response = externalInvoiceDocumentDetailsService.getSupportingInvoiceDocumentDetails(userObj, documentRefId,"Supporting" );
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}
	
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("/externalinvoice/getPDFInvoiceDocument/{documentRefId}")
	public ResponseEntity<ResponseMessage> getPDFInvoiceDocument(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		System.out.println("DocumentRefID of the attachment to be retrieved ="+ documentRefId);
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			AttachmentDetails response = externalInvoiceDocumentDetailsService.getPDFInvoiceDocument(userObj,documentRefId);
			if(response==null)
			{
				System.out.println("There is no document found for the document ref id " + documentRefId);
				return ResponseEntity.ok().body(new ResponseMessage(
						new APIError(Ex.INV_DOC_NOT_FOUND.getKey(), Ex.formatMessage(Ex.INV_DOC_NOT_FOUND.getKeyMessage(),documentRefId))));
			}
			System.out.println("Response returning getPDFInvoiceDocument");
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}	
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("/externalinvoice/getSupportingInvoiceDocument/{docId}")
	public ResponseEntity<ResponseMessage> getSupportingInvoiceDocument(@PathVariable String docId,
			@RequestHeader("authorization") String token) {
		System.out.println("Document ID of the attachment to be retrieved ="+ docId);
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			AttachmentDetails response = externalInvoiceDocumentDetailsService.getSupportingInvoiceDocument(userObj,docId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}
	
	
	@GetMapping("externalinvoice/externalinvoicestatus/getstatusbydocument_ref_id/{documentRefId}")
	public ResponseEntity<ResponseMessage> getExternalInvoiceStatusByPartnerTypeAndStatusKey(@PathVariable String documentRefId,@RequestHeader("authorization") String token) {
		System.out.println("inside getExternalInvoiceStatusByPartnerTypeAndStatusKey of invoiceStatusController ***");
		

		System.out.println("User Name is : " + getUserName(token));
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + userObj);
		System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
		System.out.println("User Name from Obj = " + userObj.getUserId());
		System.out.println("Partner Type from Obj = " + userObj.getPartnerType());
		
		List<InvoiceStatus> response =null;
	
		
		HashMap<String, String> invoiceHeader = new HashMap<>();
		 ArrayList<Object> responseList = new ArrayList <>();
		 
		 
		 Map<String,Object> responseValues = new HashMap<>();
		 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy"); 
		
		try
		{
			
			ExternalInvoiceDocumentDetails externalInvoiceDocumentDetails = externalInvoiceDocumentDetailsService.getInvoiceDetails(documentRefId);
			
			Date invoiceDate=externalInvoiceDocumentDetails.getInvoicedate();		
			
			invoiceHeader.put("invoicenum", externalInvoiceDocumentDetails.getInvoicenum());
			invoiceHeader.put("total_invoice_value",externalInvoiceDocumentDetails.getTotal_invoice_value().toString());
			invoiceHeader.put("supplier_legal_name", externalInvoiceDocumentDetails.getRecipient_company_name());
			invoiceHeader.put("invoicedate", formatter.format(externalInvoiceDocumentDetails.getInvoicedate()));
			invoiceHeader.put("invoiceduedate","");
			if(invoiceDate!=null && externalInvoiceDocumentDetails.getCreditdays()!=0)
			{
			Calendar cal = Calendar.getInstance();
		        cal.setTime(invoiceDate);
		        cal.add(Calendar.DATE, externalInvoiceDocumentDetails.getCreditdays()); //minus number would decrement the days
		        
			invoiceHeader.put("invoiceduedate", formatter.format(cal.getTime()));
			}
			
			System.out.println("externalinvoice Partner ID from invoiceDetails=" + externalInvoiceDocumentDetails.getPartner_id());
			
			 String partnerType = userObj.getPartnerType();
			    String statusKey = externalInvoiceDocumentDetails.getInvoice_status();
			    System.out.println("Partner Type = " + partnerType);
			    System.out.println("Status Key  = " + statusKey);
		 response =  invoiceStatusService.findByPartnerTypeAndStatusKey(partnerType, statusKey);
		 
		 responseValues.put("invoiceheader", invoiceHeader);
		 responseValues.put("statuskeys", response);
			
			
		}
	 catch (Exception e) {
		System.err.println(e);
		e.printStackTrace();
		log.logErrorMessage(e.getMessage(), e);
		return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
				Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
	}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(responseValues));
	}

}
