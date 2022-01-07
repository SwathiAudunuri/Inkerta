package com.tecnics.einvoice.service;


import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tecnics.einvoice.entity.AttachmentDetails;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.ExternalInvoiceDocumentDetails;
import com.tecnics.einvoice.entity.ExternalInvoiceStatusTracker;
import com.tecnics.einvoice.exceptions.Ex;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.tecnics.einvoice.Repo.InvoiceReminderTemplatesRepo;
import com.tecnics.einvoice.Repo.InvoiceStatusTrackerRepo;
import com.tecnics.einvoice.Repo.InvoiceReminderTemplatesRepo.InvoiceReminderTemplateNames;
import com.tecnics.einvoice.constants.Images;
import com.tecnics.einvoice.entity.InvoiceReminderTemplates;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.entity.InvoiceStatusTracker;
import com.tecnics.einvoice.Repo.AttachmentDetailsRepo;
import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceStatusTrackerRepo;
import com.tecnics.einvoice.model.CustomActionsModel;
import com.tecnics.einvoice.model.ExternalInvoiceRequestModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.SendReminderModel;
import com.tecnics.einvoice.model.UserLoginDetails;

import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.SendMailUtil;
@Component
public class InvoiceReminderService extends BaseService {
	
	@Autowired
	InvoiceReminderTemplatesRepo invoiceReminderTemplatesRepo;
	
	@Autowired
	ExternalInvoiceDocumentDetailsService externalInvoiceDocumentDetailsService;
	
	@Autowired
	InvoiceDocumentDetailService detailService;
	
	@Autowired
	InvoiceStatusTrackerRepo invoiceStatusTrackerRepo;
	
	@Autowired
	ExternalInvoiceStatusTrackerRepo externalInvoiceStatusTrackerRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	@Autowired
	private AttachmentDetailsRepo attachmentDetailsRepo;
	
	
	@Autowired
	private Environment env;
	

//fetchInvoiceActivitiesForInternal
	
	public ResponseEntity<ResponseMessage> fetchInvoiceReminderTemplateNames(UserLoginDetails userObj)
	{
		List<InvoiceReminderTemplateNames> invoiceReminderTemplateNames=null;
		String partnerId= userObj.getPartnerId();
		
		try
		{			
				invoiceReminderTemplateNames=invoiceReminderTemplatesRepo.fetchInvoiceReminderTemplateNames(partnerId);
			if(invoiceReminderTemplateNames==null || (invoiceReminderTemplateNames!=null && invoiceReminderTemplateNames.size()<=0))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There are no templates setup in the system", "There are no templates setup in the system:","fetchInvoiceReminderTemplateNames" )));
			
			
			
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("InvoiceReminderService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchInvoiceReminderTemplateNames");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("fetchInvoiceReminderTemplateNames Error", "Error while fetching the Reminder Template Names :","fetchInvoiceReminderTemplateNames" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoiceReminderTemplateNames));
		
	
	}	
	
	public ResponseEntity<ResponseMessage> fetchInvoiceReminderTemplate(UserLoginDetails userObj, Integer templateId, String document_ref_id, String type_of_invoice)
	{
		Optional<InvoiceReminderTemplates> invoiceReminderTemplates=null;
		InvoiceReminderTemplates invoiceReminderTemplate=null;
		String partnerId= userObj.getPartnerId();
		
		try
		{			
			invoiceReminderTemplates=invoiceReminderTemplatesRepo.fetchInvoiceReminderTemplate(templateId, partnerId);
			
			if (invoiceReminderTemplates!=null && invoiceReminderTemplates.isPresent())
				invoiceReminderTemplate=invoiceReminderTemplates.get();
		
			if(invoiceReminderTemplate==null )
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("There is no template exists with templateId : " + templateId, "There is no template exists with templateId : " + templateId,"fetchInvoiceReminderTemplate" )));
			
			System.out.println("Content from template = " +invoiceReminderTemplate.getTemplateContent() );
			
			
			//parsing inputRequest
			String template_content=invoiceReminderTemplate.getTemplateContent();
			String emailSubject=invoiceReminderTemplate.getEmailSubject();
			HashMap<String, String> input_variables = new HashMap<>();
			//System.out.println("Finding $ variables");
			Pattern p = Pattern.compile("\\[\\$(.*?)\\$\\]");
			Matcher m = p.matcher(template_content+emailSubject);
			while(m.find())
			{
				//System.out.println(m.group(1)); //is your string. do what you want
				input_variables.put(m.group(1), m.group(1));
			}
			
			if(type_of_invoice.equalsIgnoreCase("external"))
					{
					ExternalInvoiceDocumentDetails externalInvoiceDocumentDetails=externalInvoiceDocumentDetailsService.getInvoiceDetails(document_ref_id);
					input_variables=processExternalInvoiceInputVariables(userObj,input_variables,externalInvoiceDocumentDetails);
					}
			else if	(type_of_invoice.equalsIgnoreCase("internal"))
					{
					InvoiceRequestModel irm = detailService.getInvoiceDetails(document_ref_id);
					input_variables=processInternalInvoiceInputVariables(userObj,input_variables,irm);
					}
			 for (Map.Entry<String, String> e : input_variables.entrySet())
			 {
				           // System.out.println("Key: " + e.getKey()  + " Value: " + e.getValue());
				 template_content=template_content.replaceAll("\\[\\$"+e.getKey()+"\\$\\]", e.getValue());
				 emailSubject=emailSubject.replaceAll("\\[\\$"+e.getKey()+"\\$\\]", e.getValue());
			 }
			 
			 template_content=template_content.replaceAll("\\\n","");
			 template_content=template_content.replaceAll("\\\t","");
			 invoiceReminderTemplate.setTemplateContent(template_content);
			 invoiceReminderTemplate.setEmailSubject(emailSubject);
			
			//end of parsing
			
			
			//return null;
		
		}
		catch(Exception ex){
					ex.printStackTrace();
					ErrorLog error = new ErrorLog();
					error.setComponentName("InvoiceReminderService");
					error.setError(ex.getMessage());
					error.setErrorMessage(getStackTrace(ex));
					error.setModule("fetchInvoiceReminderTemplate");
					error.setSource("Java");
					error.setUserId(userObj.getUserId());
					errorLogRepo.save(error);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("fetchInvoiceReminderTemplateNames Error", "Error while fetching the Reminder Template Names :","fetchInvoiceReminderTemplateNames" )));
					
				}
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invoiceReminderTemplates));
		
	
	}		
	
	
	
	
	private HashMap<String, String> processInternalInvoiceInputVariables(UserLoginDetails userObj,HashMap<String, String> input_variables, InvoiceRequestModel irm)
	{
		 for (Map.Entry<String, String> e : input_variables.entrySet())
		 {
			 
	           // System.out.println("Key: " + e.getKey()  + " Value: " + e.getValue());
	           
	            switch (e.getKey()) {
	            case "total_invoice_value":
	            	input_variables.put(e.getKey(), String.valueOf(irm.getInvoiceDetails().getTotal_invoice_value()));
	                break;
	            case "customer_name":
	            	input_variables.put(e.getKey(), String.valueOf(irm.getInvoiceSupplierBuyerDetails().getBilling_legal_name()));
	                break;
	            case "vendor_name":
	            	input_variables.put(e.getKey(), String.valueOf(irm.getInvoiceSupplierBuyerDetails().getSupplier_legal_name()));
	                break; 
	            case "user_name":
	            	input_variables.put(e.getKey(), userObj.getFirstName() + " " + userObj.getLastName());
	                break;  
	            case "user_email":
	            	input_variables.put(e.getKey(), userObj.getEmail());
	                break;
	            
	            case "invoice_date":
	            	input_variables.put(e.getKey(), String.valueOf(irm.getInvoiceDetails().getInvoicedate()));
	                break;
	            case "invoicenum":
	            	input_variables.put(e.getKey(), String.valueOf(irm.getInvoiceDetails().getInvoicenum()));
	                break;
	            case "invoice_duedate":
	            	java.util.Date invoice_date=irm.getInvoiceDetails().getInvoicedate();
	            	LocalDate invoice_duedate=convert(invoice_date);
	            	invoice_duedate=invoice_duedate.plusDays(irm.getInvoiceDetails().getCreditdays());
	            	input_variables.put(e.getKey(), String.valueOf(invoice_duedate));
	            	 break;
	            case "supplier_note":
	            	input_variables.put(e.getKey(), irm.getInvoiceDetails().getSupplier_note());
	                break;
	            case "po_ref_num":
	            	input_variables.put(e.getKey(), irm.getInvoiceReferenceDetails().getPo_ref_num());
	                break;	           
	            default:
	            	input_variables.put(e.getKey(), e.getValue()+"Default");
	            }
	            
	     }
		
		return input_variables;
	}
	
	private HashMap<String, String> processExternalInvoiceInputVariables(UserLoginDetails userObj, HashMap<String, String> input_variables, ExternalInvoiceDocumentDetails eidd)
	{
		 for (Map.Entry<String, String> e : input_variables.entrySet())
		 {
			 
	           // System.out.println("Key: " + e.getKey()  + " Value: " + e.getValue());
	            
	            switch (e.getKey()) {
	            case "total_invoice_value":
	            	input_variables.put(e.getKey(), String.valueOf(eidd.getTotal_invoice_value()));
	                break;
	            case "customer_name":
	            	input_variables.put(e.getKey(), String.valueOf(eidd.getRecipient_company_name()));
	                break;
	            case "vendor_name":
	            	input_variables.put(e.getKey(), String.valueOf(eidd.getPartner_company_name()));
	                break; 
	            case "user_name":
	            	input_variables.put(e.getKey(), userObj.getFirstName() + " " + userObj.getLastName());
	                break;  
	            case "user_email":
	            	input_variables.put(e.getKey(), userObj.getEmail());
	                break;	                
	                
	            case "invoice_date":
	            	input_variables.put(e.getKey(), String.valueOf(eidd.getInvoicedate()));
	                break;
	            case "invoicenum":
	            	input_variables.put(e.getKey(), String.valueOf(eidd.getInvoicenum()));
	                break;
	            case "invoice_duedate":
	            	java.util.Date invoice_date=eidd.getInvoicedate();
	            	LocalDate invoice_duedate=convert(invoice_date);
	            	invoice_duedate=invoice_duedate.plusDays(eidd.getCreditdays());
	            	input_variables.put(e.getKey(), String.valueOf(invoice_duedate));
	            	 break;
	            case "supplier_note":
	            	input_variables.put(e.getKey(), eidd.getSupplier_note());
	                break;
	            case "po_ref_num":
	            	input_variables.put(e.getKey(), eidd.getInvoice_reference_no());
	                break;	           
	            default:
	            	input_variables.put(e.getKey(), e.getValue()+"Default");
	            }
	            
	     }
		
		return input_variables;
	}
	
	
	public static LocalDate convert (Date date) {
	    return date.toInstant()
	      .atZone(ZoneId.of("UTC"))
	      .toLocalDate();
	}
	
	
	public ResponseEntity<ResponseMessage> sendReminder(SendReminderModel request,UserLoginDetails userObj)
	{
		
		try {
	
		
		List<String> list_attachmentIds = new ArrayList<String>();
		List<String> list_base64Flags = new ArrayList<String>();
		
		List<String> list_attachmentNames = new ArrayList<String>();
		List<String> list_mimeTypes = new ArrayList<String>();
	
		
		String bccMailAdresses[]=null;
		String ccMailAdresses[]=null;
		String toMailAdresses[]=null;
		
		System.out.println("Content to be sent = " + request.getMailBody());
		
		if(request.isIncludeAttachment())
		{
		
		AttachmentDetails attachmentDetails = getPDFInvoiceDocument(userObj,request.getDocument_ref_id(),request.isExternal() );
	
			System.out.println(" email attachment File Details " );
			
			System.out.println(" Doc Name = " + attachmentDetails.getDoc_name() );
			list_attachmentIds.add("data:"+attachmentDetails.getMime_type()+";base64,"+attachmentDetails.getBase64());
			list_base64Flags.add("true");
			list_attachmentNames.add(attachmentDetails.getDoc_name());
			list_mimeTypes.add(attachmentDetails.getMime_type());
			
			
			System.out.println(" Doc Type = " + attachmentDetails.getDocType());
			System.out.println(" Doc size = " + attachmentDetails.getDoc_size());
			System.out.println(" Doc Mime type  = " + attachmentDetails.getMime_type() );
			System.out.println(" Folde  Id = " + attachmentDetails.getFolder_id() );
			System.out.println(" Ref Id = " + attachmentDetails.getRefId() );
		}

		
		
		String mailBody=request.getMailBody();
		
		
		SendMailUtil emailUtil = new SendMailUtil();
		
		String attachmentIds[]=new String[list_attachmentIds.size()];
		attachmentIds = list_attachmentIds.toArray(attachmentIds);
		String base64Flags[]= new String[list_base64Flags.size()];
		base64Flags = list_base64Flags.toArray(base64Flags);
		String attachmentNames[]=new String[list_attachmentNames.size()];
		attachmentNames = list_attachmentNames.toArray(attachmentNames);
		String mimeTypes[]= new String[list_mimeTypes.size()];
		mimeTypes = list_mimeTypes.toArray(mimeTypes);
		String mailSubject="Invoice from "+request.getEmailSubject();
	
		if(request.getToAddresses()!=null)
		{
			toMailAdresses = new String[request.getToAddresses().size()];
		toMailAdresses=request.getToAddresses().toArray(toMailAdresses);
		
		
		if(request.getCcAddresses()!=null)
		{
			ccMailAdresses = new String[request.getCcAddresses().size()];
		ccMailAdresses=request.getCcAddresses().toArray(ccMailAdresses);
		}
		
		if(request.getBccAddresses()!=null)
		{
		bccMailAdresses = new String[request.getBccAddresses().size()];
		bccMailAdresses=request.getBccAddresses().toArray(bccMailAdresses);
		}
		System.out.println("Send mail calling *****************");
		//images
		String base64Images[]={Images.inkreta_email_logo};
		String base64ContentIdImagesFileNames[]= {"logo_inkreta.png" };
		String base64ContentIds[]= {"<inkretalogo>"};
		
		
		emailUtil.sendEmail(env.getProperty("com.eInvoice.email.billservice.from"), env.getProperty("com.eInvoice.email.billservice.from.password"), toMailAdresses, ccMailAdresses, bccMailAdresses, mailSubject, mailBody, base64Flags,attachmentIds,attachmentNames,mimeTypes,base64Images,base64ContentIds,base64ContentIdImagesFileNames);	
		System.out.println("Check if external or internal" +request.isExternal() );
		if(request.isExternal())
		createExternalInvoiceStatusTracker("Payment Reminder",request.getDocument_ref_id(),userObj);
		else if(!request.isExternal())
			createInternalInvoiceStatusTracker("Payment Reminder",request.getDocument_ref_id(),userObj);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("reminder sent Successfuuly"));
		
		}
		
		return ResponseEntity.ok().body(new ResponseMessage(new APIError("To Addresses can not be blank", "Error : To Addresses can not be blank  :","sendReminder" )));


		}
		catch(Exception e)
		{
			System.out.println("Error while sending email");
			e.printStackTrace();
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Invoice Reminder sending Error", "Error while Invoice Reminder sending  :","sendReminder" )));

		}
		
	}
	
	

	public AttachmentDetails getPDFInvoiceDocument(UserLoginDetails userObj,String documentRefId,boolean external) {
		AttachmentDetails attachmentDetails = null;
		try
		{
		String partnerId = userObj.getPartnerId();
		// need to develop access security logic
			if(external)
			attachmentDetails = attachmentDetailsRepo.findByRefIdAndDocCategoryAndDocType(documentRefId, "externalinvoice", "Invoice");
			else if(!external)
			attachmentDetails = attachmentDetailsRepo.findByRefIdAndDocCategoryAndDocType(documentRefId, "invoice", "Invoice");
			
			
			if(attachmentDetails==null)
				return null;
			
				System.out.println(" search details of  attachmentDetail with docRefId " + documentRefId + " Type = + docType");
				System.out.println(" DocPath = " + attachmentDetails.getDocPath());
				System.out.println(" DoName = " + attachmentDetails.getDoc_name());
			
				byte[] inFileBytes = Files.readAllBytes(Paths.get(attachmentDetails.getDocPath())); 			
				//byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);
				
				attachmentDetails.setBase64(java.util.Base64.getEncoder().encodeToString(inFileBytes));
		
			

		}
		catch(FileNotFoundException fe)
		{
			System.out.println("There is no PDF document avaialble in the drive " + attachmentDetails.getDocPath());
			return null;
		}
		catch(Exception e)
		{
			System.out.println("Error in InvoiceReminderService  getPDFInvoiceDocument ");
			e.printStackTrace();
		}

		return attachmentDetails;
	}
	
	
	private void createExternalInvoiceStatusTracker(String action, String document_ref_id, UserLoginDetails userObj)
	{
		// updating invoice Status Tracker
		
		try {
		System.out.println("Creating ExternalInvoiceStatusTracker object ");
		ExternalInvoiceStatusTracker externalInvoiceStatusTracker=new ExternalInvoiceStatusTracker();
		
		externalInvoiceStatusTracker.setActionBy(userObj.getUserId());
		externalInvoiceStatusTracker.setAction(action);
		externalInvoiceStatusTracker.setActionComments("The Payment reminder has been sent");		
		externalInvoiceStatusTracker.setDocumentRefId(document_ref_id);
		
		externalInvoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
		externalInvoiceStatusTracker.setActionType("Invoice Payment Reminder");
		
		externalInvoiceStatusTrackerRepo.save(externalInvoiceStatusTracker);
		} catch (Exception e) {
			System.out.println("Error while saving  externalInvoiceStatusTracker" + e.getMessage());
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			
		}
	}
	
	private void createInternalInvoiceStatusTracker(String action, String document_ref_id, UserLoginDetails userObj)
		{
		try {
			InvoiceStatusTracker invoiceStatusTracker=new InvoiceStatusTracker();			
			invoiceStatusTracker.setActionBy(userObj.getUserId());
			invoiceStatusTracker.setAction(action);
			invoiceStatusTracker.setActionComments("The Payment reminder has been sent");
			invoiceStatusTracker.setDocumentRefId(document_ref_id);			
			invoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
			invoiceStatusTracker.setActionType("Invoice Payment Reminder");
			invoiceStatusTracker.setSource("Vendor");	
			invoiceStatusTracker.setVisibleToPartnerid(userObj.getPartnerId());
			invoiceStatusTracker = invoiceStatusTrackerRepo.save(invoiceStatusTracker);
		} catch (Exception e) {
			System.out.println("Error while saving  InternalInvoiceStatusTracker" + e.getMessage());
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			
		}
		}
	

}

