package com.tecnics.einvoice.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnics.einvoice.Repo.FolderObjectsRepo;
import com.tecnics.einvoice.Repo.InvoiceAttachmentDetailsRepo;
import com.tecnics.einvoice.Repo.InvoiceAuditRepo;
import com.tecnics.einvoice.Repo.InvoiceDispatchShiptoDetailRepo;
import com.tecnics.einvoice.Repo.InvoiceDocumentDetailRepo;
import com.tecnics.einvoice.Repo.InvoiceEwayBillDetailRepo;
import com.tecnics.einvoice.Repo.InvoiceItemListRepo;
import com.tecnics.einvoice.Repo.InvoiceLogFailureRepo;
import com.tecnics.einvoice.Repo.InvoiceLogRepo;
import com.tecnics.einvoice.Repo.InvoiceReferenceDetailsRepo;
import com.tecnics.einvoice.Repo.InvoiceSellerPaymentInformationRepo;
import com.tecnics.einvoice.Repo.InvoiceStatusTrackerRepo;
import com.tecnics.einvoice.Repo.InvoiceSupplierBuyerInfoRepo;
import com.tecnics.einvoice.Repo.ItemsExchangeQueueRepo;
import com.tecnics.einvoice.alfresco.AlfrescoService;
import com.tecnics.einvoice.entity.CompanyGstinDetail;
import com.tecnics.einvoice.entity.FolderObjects;
import com.tecnics.einvoice.entity.InvoiceAttachmentDetail;
import com.tecnics.einvoice.entity.InvoiceAudit;
import com.tecnics.einvoice.entity.InvoiceDispatchShiptoDetail;
import com.tecnics.einvoice.entity.InvoiceDocumentDetail;
import com.tecnics.einvoice.entity.InvoiceEwayBillDetail;
import com.tecnics.einvoice.entity.InvoiceItemList;
import com.tecnics.einvoice.entity.InvoicePullResponse;
import com.tecnics.einvoice.entity.InvoiceReferenceDetails;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.entity.InvoiceSellerPaymentInformation;
import com.tecnics.einvoice.entity.InvoiceStatusTracker;
import com.tecnics.einvoice.entity.InvoiceSupplierBuyerInfo;
import com.tecnics.einvoice.entity.ItemsExchangeQueue;
import com.tecnics.einvoice.entity.PartnerGSTINDetails;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.exceptions.InternalException;
import com.tecnics.einvoice.file.manager.DocUploadRequest;
import com.tecnics.einvoice.model.InvoiceAction;
import com.tecnics.einvoice.model.InvoiceGridResponse;
import com.tecnics.einvoice.model.InvoiceMetaDataModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.InvoiceResponseModel;
import com.tecnics.einvoice.response.InvoiceTransactionResponse;
import com.tecnics.einvoice.serviceImpl.InvoiceDetailsServiceImpl;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.model.InvoiceQRCodeData;
import com.tecnics.einvoice.util.PDFUtilForQRCode;
import com.tecnics.einvoicejson.model.InvoiceJsonModel;
import com.tecnics.einvoicejson.model.ReqModel;


@Component
public class InvoiceDocumentDetailService extends BaseService {

	public static final String CLASSNAME = "InvoiceDocumentDetailService";

	@Autowired
	InvoiceSupplierBuyerInfoRepo supplierbuyerInfoRepo;

	@Autowired
	InvoiceSellerPaymentInformationRepo sellerPaymentinformationRepo;

	@Autowired
	InvoiceDispatchShiptoDetailRepo dispatchShipdetailRepo;

	@Autowired
	InvoiceDocumentDetailRepo invoicedetailRepo;

	@Autowired
	InvoiceEwayBillDetailRepo invoiceEwaybillDetailRepo;

	@Autowired
	InvoiceReferenceDetailsRepo invoiceReferenceDetailsRepo;
	
	@Autowired
	InvoiceLogRepo invoiceLogRepo;

	@Autowired
	private InvoiceAttachmentDetailsRepo attachmentDetailsRepo;
	
	

	@Autowired
	private ItemsExchangeQueueRepo queueRepo;

	@Autowired
	private InvoiceItemListRepo lineItemRepo;

	@Autowired
	InvoiceDetailsServiceImpl serviceImpl;
	
	@Autowired
	PDFUtilForQRCode pdfUtilForQRCode;

	@Autowired
	AlfrescoService alfrescoService;

	@Autowired
	private InvoiceLogFailureRepo failure;

	@Autowired
	private InvoiceAuditRepo auditRepo;
	
	@Autowired
	CompanyGstinDetailService companyGstinDetailService;
	
	@Autowired
	PartnerGSTINDetailsService partnerGSTINDetailsService;
	
	@Autowired
	InvoiceStatusTrackerRepo invoiceStatusTrackerRepo;
	
	@Autowired
	private Environment env;
	
	

	/***
	 * findAll
	 * 
	 * @param username
	 * @return
	 */
	public List<InvoiceGridResponse> findAll(String username) {
		List<InvoiceGridResponse> invoices = new ArrayList<>();

		String partnerId = getPartnerIdfromUserId(username);
		if (isVendor(username)) {
			invoices = serviceImpl.findUnpaidInvoicesByVendorPartnerId(partnerId);
		}

		return invoices;
	}
	
	
	public List<InvoiceGridResponse> findCustomerInvoices(UserLoginDetails userObj, String param) {
		List<InvoiceGridResponse> invoices = new ArrayList<>();

		String partnerId = getPartnerIdfromUserId(userObj.getUserId());
	//	if (isCustomer(userObj.getUserId())) {
			if(param.equals("Unpaid"))
			invoices = serviceImpl.findUnpaidInvoicesByCustomerPartnerId(userObj);
			else if(param.equals("Paid"))
				invoices = serviceImpl.findPaidInvoicesByCustomerPartnerId(userObj);
			else if(param.equals("Exceptions"))
				invoices = serviceImpl.findExceptionInvoicesByCustomerPartnerId(userObj);
			else if(param.equals("MyInbox"))
				invoices = serviceImpl.findAssignedToMeInvoicesByCustomerPartnerId(partnerId,userObj.getUserId());
			else if(param.equals("Queried"))
				invoices = serviceImpl.findQueriedInvoicesByCustomerPartnerId(userObj);
	//	}

		return invoices;
	}
	
	public List<InvoiceGridResponse> findVendorInvoices(String username,String param) {
		List<InvoiceGridResponse> invoices = new ArrayList<>();

		String partnerId = getPartnerIdfromUserId(username);
		System.out.println("Partner id of the vendor = " + partnerId);
		//if (isVendor(username)) {
			if(param.equals("Unpaid"))
			invoices = serviceImpl.findUnpaidInvoicesByVendorPartnerId(partnerId);
			else if(param.equals("Paid"))
			invoices = serviceImpl.findPaidInvoicesByVendorPartnerId(partnerId);
			else if(param.equals("Queried"))
				invoices = serviceImpl.findQueriedInvoicesByVendorPartnerId(partnerId);
		//}

		return invoices;
	}
	
/*	public InvoiceQRCodeData verifyQRCode(String username,String documentRefId) {
		
		InvoiceQRCodeData invoiceQRCodeData=null;
		InvoiceAttachmentDetail invoiceAttachmentDetail = null;
		//PDFUtilForQRCode pdfUtilForQRCode=new PDFUtilForQRCode();
		
		try
		{
		String partnerId = getPartnerIdfromUserId(username);
		// need to develop access security logic
		if (isVendor(username) || isCustomer(username)) {
			invoiceAttachmentDetail = serviceImpl.findPDFInvoiceDocument(documentRefId,"Invoice");
			if(invoiceAttachmentDetail!=null)
			{
				System.out.println("PDF Document path for QR Verification = " + invoiceAttachmentDetail.getDocPath() );
				//invoiceQRCodeData=pdfUtilForQRCode.verifyQRCode(invoiceAttachmentDetail.getDocPath());
				//invoiceQRCodeData=pdfUtilForQRCode.verifyQRCodeUsingSpire(invoiceAttachmentDetail.getDocPath());
				invoiceQRCodeData=pdfUtilForQRCode.fetchQRCodeDetails(invoiceAttachmentDetail.getDocPath(),documentRefId);
				
			}
			//get the default invoice document for timebeing..this logic will change later
			else
			{
				invoiceQRCodeData=new InvoiceQRCodeData();
				invoiceQRCodeData.setIsqrveified(false);
				invoiceQRCodeData.setQrverifiedstatus("failed");
				invoiceQRCodeData.setReasonforfailure("There is no Invoice.pdf document avaialble for docRefId : " + documentRefId);
				
			}
			return invoiceQRCodeData;
			}
	
		}
		catch(Exception e)
		{
			System.out.println("Error in InvoiceDocumentDetailService getPDFInvoiceDocument ");
			e.printStackTrace();
		}
		return null;
	} */
	
	
public InvoiceQRCodeData verifyQRCode(String username,String documentRefId) {
		
		InvoiceQRCodeData invoiceQRCodeData=null;
		InvoiceAttachmentDetail invoiceAttachmentDetail = null;
		//PDFUtilForQRCode pdfUtilForQRCode=new PDFUtilForQRCode();
		
		try
		{
		String partnerId = getPartnerIdfromUserId(username);
		// need to develop access security logic
		//if (isVendor(username) || isCustomer(username)) {
			invoiceAttachmentDetail = serviceImpl.findPDFInvoiceDocument(documentRefId,"Invoice");
			if(invoiceAttachmentDetail!=null)
			{
				System.out.println("PDF Document path for QR Verification = " + invoiceAttachmentDetail.getDocPath() );
				//invoiceQRCodeData=pdfUtilForQRCode.verifyQRCode(invoiceAttachmentDetail.getDocPath());
				//invoiceQRCodeData=pdfUtilForQRCode.verifyQRCodeUsingSpire(invoiceAttachmentDetail.getDocPath());
				invoiceQRCodeData=pdfUtilForQRCode.fetchQRCodeDetails(invoiceAttachmentDetail.getDocPath(),documentRefId);
				
			}
			//get the default invoice document for timebeing..this logic will change later
			else
			{
				invoiceQRCodeData=new InvoiceQRCodeData();
				invoiceQRCodeData.setIsqrveified(false);
				invoiceQRCodeData.setQrverifiedstatus("failed");
				invoiceQRCodeData.setReasonforfailure("There is no Invoice.pdf document avaialble for docRefId : " + documentRefId);
				
			}
			return invoiceQRCodeData;
		//	}
	
		}
		catch(Exception e)
		{
			System.out.println("Error in InvoiceDocumentDetailService getPDFInvoiceDocument ");
			e.printStackTrace();
		}
		return null;
	}
	
	public List<InvoiceAttachmentDetail> getSupportingInvoiceDocumentDetails(String username,String documentRefId) {
		
		
		List<InvoiceAttachmentDetail> invoiceAttachmentDetails = null;
		//PDFUtilForQRCode pdfUtilForQRCode=new PDFUtilForQRCode();
		
		try
		{
		String partnerId = getPartnerIdfromUserId(username);
		// need to develop access security logic
		//if (isVendor(username) || isCustomer(username)) {
			invoiceAttachmentDetails = serviceImpl.getSupportingInvoiceDocumentDetails(documentRefId,"Supporting");			
			return invoiceAttachmentDetails;
		//	}
	
		}
		catch(Exception e)
		{
			System.out.println("Error in InvoiceDocumentDetailService getSupportingInvoiceDocumentDetails ");
			e.printStackTrace();
		}
		return null;
	}
	
	public InvoiceAttachmentDetail getSupportingInvoiceDocument(String username,String docId) {
		InvoiceAttachmentDetail invoiceAttachmentDetail = null;
		try
		{
		String partnerId = getPartnerIdfromUserId(username);
		// need to develop access security logic
		//if (isVendor(username) || isCustomer(username)) {
			invoiceAttachmentDetail = serviceImpl.findSupportingInvoiceDocument(docId);
			if(invoiceAttachmentDetail!=null)
			{
				System.out.println(" search details of invoiceattachmentDetail with docId " + docId );
				System.out.println(" DocPath = " + invoiceAttachmentDetail.getDocPath());
				System.out.println(" DoName = " + invoiceAttachmentDetail.getDoc_name());
			
				byte[] inFileBytes = Files.readAllBytes(Paths.get(invoiceAttachmentDetail.getDocPath())); 			
				//byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);				
				invoiceAttachmentDetail.setBase64(java.util.Base64.getEncoder().encodeToString(inFileBytes));
			}
			
		//	}
	
		}
		catch(Exception e)
		{
			System.out.println("Error in InvoiceDocumentDetailService getPDFInvoiceDocument ");
			e.printStackTrace();
		}

		return invoiceAttachmentDetail;
	}
	
	public InvoiceAttachmentDetail getPDFInvoiceDocument(String username,String documentRefId) {
		InvoiceAttachmentDetail invoiceAttachmentDetail = null;
		try
		{
		String partnerId = getPartnerIdfromUserId(username);
		// need to develop access security logic
		//if (isVendor(username) || isCustomer(username)) {
			invoiceAttachmentDetail = serviceImpl.findPDFInvoiceDocument(documentRefId,"Invoice");
			if(invoiceAttachmentDetail!=null)
			{
				System.out.println(" search details of invoiceattachmentDetail with docRefId " + documentRefId + " Type = + docType");
				System.out.println(" DocPath = " + invoiceAttachmentDetail.getDocPath());
				System.out.println(" DoName = " + invoiceAttachmentDetail.getDoc_name());
			
				byte[] inFileBytes = Files.readAllBytes(Paths.get(invoiceAttachmentDetail.getDocPath())); 			
				//byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);
				
				invoiceAttachmentDetail.setBase64(java.util.Base64.getEncoder().encodeToString(inFileBytes));
			}
			//get the default invoice document for timebeing..this logic will change later
			else
			{
				
				return null;				
				
				/*invoiceAttachmentDetail=new InvoiceAttachmentDetail();
				invoiceAttachmentDetail.setDocId("sample");
				invoiceAttachmentDetail.setDoc_name("Invoice.pdf");
				invoiceAttachmentDetail.setMime_type("application/pdf");
				byte[] inFileBytes = Files.readAllBytes(Paths.get(env.getProperty("os.folder.invoices") + env.getProperty("os.file.seperator") + "Invoice.pdf")); 			
			
				
				invoiceAttachmentDetail.setBase64(java.util.Base64.getEncoder().encodeToString(inFileBytes));*/
			}
			//}
	
		}
		catch(FileNotFoundException fe)
		{
			System.out.println("There is no PDF document avaialble in the drive " + invoiceAttachmentDetail.getDocPath());
			return null;
		}
		catch(Exception e)
		{
			System.out.println("Error in InvoiceDocumentDetailService getPDFInvoiceDocument ");
			e.printStackTrace();
		}

		return invoiceAttachmentDetail;
	}

	/***
	 * save
	 * @param userName
	 * @return
	 */
	public ResponseEntity<ResponseMessage> save(List<InvoiceRequestModel> request, UserLoginDetails userObj) {
		final String methodname = "TransactionResponse";
		log.logMethodEntry(CLASSNAME + methodname);
		System.out.println("UserName in InvoiceDocumentDetailService save="+userObj.getUserId());
		String vpid = getPartnerIdfromUser(userObj.getUserId());

		int failCount = 0;
		int successCount = 0;
		String batchid = "";
		boolean discardFlag = false;
		boolean draftFLag = false;
		
		
		
		String docSrcRefId = null;
		InvoiceTransactionResponse invPersistResponse = new InvoiceTransactionResponse();
		List<InvoiceResponseModel> invresplist = new ArrayList<InvoiceResponseModel>();

		// Validating if the requested user is vendor else terminate the transaction
		/*if (!isVendor(userObj.getUserId())) { 
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INVOICE_ROLE_EXCEPTION.getKey(),Ex.formatMessage(Ex.INVOICE_ROLE_EXCEPTION.getKeyMessage()),Ex.INVOICE_ROLE_EXCEPTION.getKeyMessage())));
		}*/
		
	
		
		
		//String folderid = serviceImpl.invoiceFolderCreate(vpid);
		

		// generating document source reference id if the list size is greater than 1
		// else document reference id will be the document source reference id
		if (request.size() > 1) {
			docSrcRefId = serviceImpl.generateDocSourceRefId();
		}

		
		//grouping invoices on recipient codes and getting count here
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
		for (int i = 0; i < request.size(); i++) {
			String recpcode = request.get(i).getInvoiceDetails().getCustomer_recipient_code();
			if (!hashMap.containsKey(recpcode)) {
				hashMap.put(recpcode, 1);
			} else {
				int counter = hashMap.get(recpcode);
				hashMap.replace(recpcode, counter + 1);
			}
		}

		// Iterating multiple Invoices from request
		for (InvoiceRequestModel details : request) {
			InvoiceResponseModel resp = new InvoiceResponseModel();
			InvoiceMetaDataModel invMeta = details.getInvoiceDetails();
		
			
			//check Authorization
			
			PartnerGSTINDetails supplierGSTINDetails=partnerGSTINDetailsService.findByGstinAndPartnerId(details.getInvoiceSupplierBuyerDetails().getSupplier_gstin(),userObj.getPartnerId());
			// Validating if the requested user is sent the mapped gstin else terminate the transaction
			if (supplierGSTINDetails==null) {
				return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.UNAUTHORIZED_INVOICE_SUBMISSION.getKey(),Ex.formatMessage(Ex.UNAUTHORIZED_INVOICE_SUBMISSION.getKeyMessage(),details.getInvoiceSupplierBuyerDetails().getSupplier_gstin()),Ex.UNAUTHORIZED_INVOICE_SUBMISSION.getKeyMessage())));
			}
			
			System.out.println("**&& supplierGSTINDetails =  " + supplierGSTINDetails.getGstin() + " : " + supplierGSTINDetails.getTradename());
			
			
			String actionStatus =null;
			String actionCode =null;
			String documentRefId = null;
			InvoiceAction action=null;
			if (details != null && details.getActionDetails() != null) {
				action = details.getActionDetails();
				if (action.getAction_code() != null) {
					actionCode= action.getAction_code();
					actionStatus =action.getAction_name();
					switch (actionCode) {
					case "002":	draftFLag = true;  break;
					case "003":	discardFlag = true;	break;
					default:break;
					}
					invMeta.setStatus(actionStatus);
				}
			}
		
			
			if (!draftFLag || (draftFLag && invMeta.getDocument_ref_id() == null)) {
				documentRefId = serviceImpl.generateDocRefId(invMeta.getInvoice_type_code());
				invMeta.setDocument_ref_id(documentRefId);
			}
			// generates document reference id for each invoice
			FolderObjects fo= serviceImpl.osFolderCreateForInvoice(vpid,documentRefId);
			String folderid = fo.getFolder_id();
			System.out.println("Complete Folder path created = " + folderid);
			System.out.println("Attachment Saving");
			//saveInvoiceAttachmentsInOS(details,fo,userName);
			System.out.println("Attachment Saving code completed");
			
			// generating document source reference id if the list size is greater than 1
			// else document reference id will be the document source reference id
			if (request.size() > 1) {
				invMeta.setDoc_source_ref_id(docSrcRefId);
				batchid = docSrcRefId;
			} else {
				invMeta.setDoc_source_ref_id(documentRefId);
				batchid = documentRefId;
			}

			try {
				invMeta.setEcm_folder_id(folderid);
				invMeta.setVendor_partner_id(vpid);
				invMeta.setCreated_by(userObj.getUserId());
				//commenting the below block by Gopal for logic change
				/*
				if (invMeta.getCustomer_recipient_code() != null) {
					String cpid = null;
					cpid = serviceImpl.fetchpartnerIdByRecipientCode(invMeta.getCustomer_recipient_code());
					System.out.println("GSTIN of Billing = " + details.getInvoiceSupplierBuyerDetails().getBilling_gstin());
					CompanyGstinDetail companyGstinDetail=companyGstinDetailService.findByGstin(details.getInvoiceSupplierBuyerDetails().getBilling_gstin());
					System.out.println("companyGstinDetail =  " + companyGstinDetail.getGstin() + " : " + companyGstinDetail.getBusinessName());
					String cust_partner_id=companyGstinDetail.getPartnerId();
							System.out.println("Customer Partner ID from CompanyGstinDetail = " +cust_partner_id );
							if (cpid != null) {
						// Validating if the requested vendor is mapped to requested Recipient , else terminate the transaction
						if (!serviceImpl.recipeintMappedToVendor(vpid,cpid)) 
							return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.RECIEPIENT_NOT_MAPPED.getKey(),	Ex.formatMessage(Ex.RECIEPIENT_NOT_MAPPED.getKeyMessage()),Ex.RECIEPIENT_NOT_MAPPED.getKeyMessage())));
						invMeta.setCustomer_partner_id(cpid);
					} else
						return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.RECIPIENT_CODE_INVALID.getKey(),Ex.formatMessage(Ex.RECIPIENT_CODE_INVALID.getKeyMessage()),Ex.RECIPIENT_CODE_INVALID.getKeyMessage())));
				} else {
					return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.RECIPIENT_CODE_NULL.getKey(),Ex.formatMessage(Ex.RECIPIENT_CODE_NULL.getKeyMessage()),Ex.RECIPIENT_CODE_NULL.getKeyMessage())));
				}*/
				
				//below logic added by removing recipient code logic
				//Commneting below few lines disconnecting Company GSTIN details and introduced partner GSTIN details
			/*	CompanyGstinDetail companyGstinDetail=companyGstinDetailService.findByGstin(details.getInvoiceSupplierBuyerDetails().getBilling_gstin());
				System.out.println("companyGstinDetail =  " + companyGstinDetail.getGstin() + " : " + companyGstinDetail.getBusinessName());
				String cust_partner_id=companyGstinDetail.getPartnerId(); */
				
				PartnerGSTINDetails partnerGSTINDetails=partnerGSTINDetailsService.findById(details.getInvoiceSupplierBuyerDetails().getBilling_gstin());
				System.out.println("**&& partnerGSTINDetails =  " + partnerGSTINDetails.getGstin() + " : " + partnerGSTINDetails.getTradename());
				String cust_partner_id=partnerGSTINDetails.getPartnerId();
				
				
				System.out.println("Customer Partner ID from CompanyGstinDetail = " +cust_partner_id );
				invMeta.setCustomer_partner_id(cust_partner_id);
				if (details.getActionDetails() != null && action.getAction_name().equals("Draft"))
					invMeta.setInvoice_status("Draft");
				else
				invMeta.setInvoice_status("New");
				//end of removal of recipient logic
				
				
				details.setInvoiceDetails(invMeta);
				
				ModelMapper modelMapper = new ModelMapper();
				InvoiceDocumentDetail dto = modelMapper.map(details.getInvoiceDetails(), InvoiceDocumentDetail.class);
				InvoiceDocumentDetail response = invoicedetailRepo.save(dto);
				insertChildTableData(details);
				saveInvoiceAttachmentsInOS(details,fo,userObj.getUserId());
				
			//	saveInvoiceAttachments(details,folderid);
				//saveInvoiceAttachmentsInOS(details,folderid);
				
				
				resp.setInvoiceNumber(invMeta.getInvoicenum());
				resp.setDoc_Ref_No(response.getDocument_ref_id());
				resp.setHasError(false);

				successCount++;
				
				//updating invoice audit
				try {
					InvoiceAudit audit = new InvoiceAudit();
					audit.setAuditOrigination(userObj.getUserId());
					audit.setAuditType(actionStatus);
					auditRepo.save(audit);
				} catch (Exception e) {
					System.out.println("Error while saing Audit" + e.getMessage());
					e.printStackTrace();
					log.logErrorMessage(e.getMessage(), e);
					
				}
				
				// updating invoice Status Tracker
				
				try {
				System.out.println("Creating invoicestatustracker object ");
				InvoiceStatusTracker invoiceStatusTracker=new InvoiceStatusTracker();
				
				invoiceStatusTracker.setActionBy(userObj.getUserId());
				if (details.getActionDetails() != null && action.getAction_name().equals("Draft"))
				{
					invoiceStatusTracker.setAction("Draft");
					invoiceStatusTracker.setActionComments("The Invoice has been uploaded by the Vendor as a Draft");
				}
				else
				{
					invoiceStatusTracker.setAction("New");
					invoiceStatusTracker.setActionComments("The Invoice has been uploaded by the Vendor");
				}
				invoiceStatusTracker.setDocumentRefId(response.getDocument_ref_id());
				
				invoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
				invoiceStatusTracker.setActionType("Invoice Upload");
				invoiceStatusTracker.setSource("Vendor");
				invoiceStatusTrackerRepo.save(invoiceStatusTracker);
				} catch (Exception e) {
					System.out.println("Error while saving  invoicestatustracker" + e.getMessage());
					e.printStackTrace();
					log.logErrorMessage(e.getMessage(), e);
					
				}
				
				
				
				
				
			} catch (InternalException e) {
				resp.setHasError(true);
				resp.setErrorCode(e.getMessage());
			} catch (Exception ex) {
				log.logErrorMessage(ex.getMessage(), ex);
				failCount++;
				resp.setHasError(true);
				resp.setErrorCode(ex.getMessage());

			} finally {
				invresplist.add(resp);
				invPersistResponse.setInvRespone(invresplist);
			}

		}
		//Invoice Item Exchange Queue
		try {
			if (!draftFLag && !discardFlag) {
				for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
					ItemsExchangeQueue queueObj = new ItemsExchangeQueue();
					queueObj.setDocSourceRefId(batchid);
					queueObj.setQueueName("INVOICE");
					queueObj.setDeliveryMechanism(getDeliveryMechanism(entry.getKey()));
					queueObj.setNoOfAttempts(0);
					queueObj.setNoOfItems(entry.getValue());
					queueObj.setStatus("Queued");
					queueObj.setRecipient_id(entry.getKey());
					queueRepo.save(queueObj);

				}
			}

		} catch (Exception e) {
			log.logErrorMessage(e.getMessage(), e);
		}
		
	

		invPersistResponse.setNoOfInvoicesFailed(failCount);
		invPersistResponse.setNoOfInvoicesProcessed(successCount);
		invPersistResponse.setNoOfInvoiceSubmitted(failCount + successCount);

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(invPersistResponse));

	}

	/***
	 * insertChildTableData
	 * 
	 * @param details
	 */
	private void insertChildTableData(InvoiceRequestModel details) {
		try {
			saveInvoiceSupplierBuyerInfos(details);
			
			saveInvoiceDispatchShiptoDetails(details);
			saveInvoiceEwayBillDetails(details);
			log.logInfoMessage("Saving Reference Details of the Invoice");
			saveInvoiceReferenceDetails(details);
			System.out.println("Saving saveInvoiceSellerPaymentInformations Before");
			saveInvoiceSellerPaymentInformations(details);
			System.out.println("Saving LineItems Before");
			saveInvoiceLineItems(details);
			System.out.println("Saving LineItems After");
		} catch (Exception e) {
			System.out.println("Error in the insert ChildTableData method");
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
		}

	}

	private String getDeliveryMechanism(String value) {
		return null;
	}

	/**
	 * 
	 * @param details
	 */
	private void saveInvoiceSellerPaymentInformations(InvoiceRequestModel details) {
		try {
			if (details.getInvoiceSellerPaymentDetails() != null) {
				System.out.println("inside saveInvoiceSellerPaymentInformations method");
				InvoiceSellerPaymentInformation obj = details.getInvoiceSellerPaymentDetails();
				obj.setDocument_ref_id(details.getInvoiceDetails().getDocument_ref_id());
				sellerPaymentinformationRepo.save(obj);

			}
		} catch (Exception ex) {
			System.out.println("Error in saveInvoiceSellerPaymentInformations ");
			ex.printStackTrace();
			log.logErrorMessage(ex.getMessage(), ex);
		}

	}

	/***
	 * saveInvoiceEwayBillDetails
	 * 
	 * @param details
	 */
	private void saveInvoiceEwayBillDetails(InvoiceRequestModel details) {
		try {
			if (details.getInvoiceEwayBillDetails() != null) {
				InvoiceEwayBillDetail obj = details.getInvoiceEwayBillDetails();
				obj.setDocument_ref_id(details.getInvoiceDetails().getDocument_ref_id());
				invoiceEwaybillDetailRepo.save(obj);

			}
		} catch (Exception ex) {
			log.logErrorMessage(ex.getMessage(), ex);
		}

	}
	
	/***
	 * saveInvoiceEwayBillDetails
	 * 
	 * @param details
	 */
	private void saveInvoiceReferenceDetails(InvoiceRequestModel details) {
		try {
			if (details.getInvoiceReferenceDetails() != null) {
				InvoiceReferenceDetails obj = details.getInvoiceReferenceDetails();
				obj.setDocument_ref_id(details.getInvoiceDetails().getDocument_ref_id());
				invoiceReferenceDetailsRepo.save(obj);

			}
		} catch (Exception ex) {
			log.logErrorMessage(ex.getMessage(), ex);
		}

	}

	/***
	 * saveInvoiceSupplierBuyerInfos
	 * 
	 * @param details
	 */
	private void saveInvoiceSupplierBuyerInfos(InvoiceRequestModel details) {
		try {
			if (details.getInvoiceSupplierBuyerDetails() != null) {
				System.out.println("details.getInvoiceSupplierBuyerDetails() != null");
				InvoiceSupplierBuyerInfo supplierBuyerInfoRes = details.getInvoiceSupplierBuyerDetails();
				supplierBuyerInfoRes.setDocument_ref_id(details.getInvoiceDetails().getDocument_ref_id());
				supplierbuyerInfoRepo.save(supplierBuyerInfoRes);

			}
		} catch (Exception ex) {
			System.out.println("Error while saving in saveInvoiceSupplierBuyerInfos " + ex.getMessage());
			ex.printStackTrace();
			log.logErrorMessage(ex.getMessage(), ex);
		}
	}

	/***
	 * saveInvoiceDispatchShiptoDetails
	 * 
	 * @param details
	 */
	private void saveInvoiceDispatchShiptoDetails(InvoiceRequestModel details) {
		try {
			if (details.getInvoiceDispatchShiptoDetails() != null) {
				InvoiceDispatchShiptoDetail obj = details.getInvoiceDispatchShiptoDetails();
				obj.setDocument_ref_id(details.getInvoiceDetails().getDocument_ref_id());
				dispatchShipdetailRepo.save(obj);

			}
		} catch (Exception ex) {
			log.logErrorMessage(ex.getMessage(), ex);
		}
	}

	/***
	 * saveInvoiceAttachments
	 * 
	 * @param details
	 */
	private void saveInvoiceAttachments(InvoiceRequestModel details,String folderid) {
		
		try {
			if (details.getInvoiceAttachmentDetails() != null) {
				List<InvoiceAttachmentDetail> attachmentDetails = details.getInvoiceAttachmentDetails();
				for (InvoiceAttachmentDetail file : attachmentDetails) {
					
					DocUploadRequest docreq = new DocUploadRequest();
					docreq.setFolderId(folderid);
					docreq.setDocType(file.getDocType());
					docreq.setDocumentBase64(new String(file.getBase64()));
					docreq.setMimeType(file.getMime_type());
					docreq.setDocumentName(file.getDoc_name());
					
					//File manager webservice call to fetch file id after uploading file
					String fileid = serviceImpl.fileUpload(docreq);
					
					file.setFolder_id(folderid);
					file.setDocId(fileid);
					
					file.setRefId(details.getInvoiceDetails().getDocument_ref_id());
					
					attachmentDetailsRepo.save(file);
				}
			}
		} catch (Exception ex) {
			log.logErrorMessage(ex.getMessage(), ex);
		}

	}
	
	private void saveInvoiceAttachmentsInOS(InvoiceRequestModel details,FolderObjects fo,String userName) {
		
		try {
			if (details.getInvoiceAttachmentDetails() != null) {
				System.out.println("saveInvoiceAttachmentsInOS getInvoiceAttachmentDetails is not null");
				List<InvoiceAttachmentDetail> attachmentDetails = details.getInvoiceAttachmentDetails();
				System.out.println("saveInvoiceAttachmentsInOS attachmentDetails count = " + attachmentDetails.size() );
				for (InvoiceAttachmentDetail file : attachmentDetails) {	
					System.out.println(" File Details " );
					file.setDocId(UUID.randomUUID().toString());
					System.out.println(" Doc Id = " + file.getDocId() );
					file.setDocCategory("invoice");
					System.out.println(" Doc Name = " + file.getDoc_name() );
					System.out.println(" Doc Type = " + file.getDocType());
					System.out.println(" Doc size = " + file.getDoc_size());
					System.out.println(" Doc Mime type  = " + file.getMime_type() );
					System.out.println(" Folde  Id = " + file.getFolder_id() );
					System.out.println(" Ref Id = " + file.getRefId() );
				
					
					
					
					System.out.println(" folderid=" + fo.getFolderPath() + " ; DocType = " +  file.getDocType() + " ; MimeType= " +file.getMime_type() + " ; Doc Name = " + file.getDoc_name()  );
					
					//System.out.println(" base64 str =" + file.getBase64());
					//File manager webservice call to fetch file id after uploading file
					//String fileid = serviceImpl.fileUpload(docreq);
					String folderSeperator=env.getProperty("os.file.seperator");
					 File document = new File(fo.getFolderPath()+folderSeperator+file.getDoc_name());
					 /* File document1 = new File(folderPath+folderSeperator+"encoded.txt");
					 FileOutputStream fos1 = new FileOutputStream(document1);
					 fos1.write(file.getBase64().replaceAll("\n","").getBytes(StandardCharsets.UTF_8));
					 fos1.flush();
					 fos1.close();*/
					    try ( FileOutputStream fos = new FileOutputStream(document); ) {
					     
					      byte[] decoder = Base64.getDecoder().decode(file.getBase64().replaceAll("\n","").getBytes(StandardCharsets.UTF_8));

					      fos.write(decoder);
					      System.out.println("PDF File Saved");
					      
					    }
					    catch(Exception e)
					    {
					    	log.logErrorMessage(e.getMessage(), e);
					    	e.printStackTrace();
					    }
									
					
					file.setFolder_id(fo.getFolder_id());
					file.setDocPath(document.getAbsolutePath());
					
					
					file.setRefId(details.getInvoiceDetails().getDocument_ref_id());
					file.setCreatedBy(userName);
					System.out.println("attachmentDetailsRepo Repository save is called");
					attachmentDetailsRepo.save(file);
				}
			}
		} catch (Exception ex) {
			log.logErrorMessage(ex.getMessage(), ex);
			ex.printStackTrace();
		}

	}

	/***
	 * saveInvoiceLineItems
	 * @param details
	 */
	private void saveInvoiceLineItems(InvoiceRequestModel details) {
		try {
			if (details.getLineItemDetails() != null) {
				List<InvoiceItemList> invoiceItemList = (List<InvoiceItemList>) details.getLineItemDetails();
				for (InvoiceItemList e : invoiceItemList) {
					e.setDocument_ref_id(details.getInvoiceDetails().getDocument_ref_id());
					lineItemRepo.save(e);
				}
			}
		} catch (Exception ex) {
			log.logErrorMessage(ex.getMessage(), ex);
		}

	}

	/**
	 * delete
	 * 
	 * @param id
	 */
	public void delete(String id) {
		serviceImpl.rollbackFailureRecord(id);
	}

	/***
	 * getInvoiceDetails
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public InvoiceRequestModel getInvoiceDetails(String documentRefId) throws MalformedURLException, IOException {
		return serviceImpl.getInvoiceDetails(documentRefId);
	}

	/**
	 * getInvoiceInfoPull
	 * 
	 * @param token
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public InvoicePullResponse getInvoiceInfoPull(String username, String recipientId) throws MalformedURLException, IOException {
		return serviceImpl.getInvoiceInfoPull(recipientId);
	}

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public String getRecipientId(String userName)
			throws IncorrectResultSizeDataAccessException, EmptyResultDataAccessException {
		return getRecipientIdByPartnerId(getPartnerIdfromUser(userName));
	}
	

	/**
	 * 
	 * @param status
	 * @param docSourceRefId
	 * @return
	 */
	public int acknowlegeInkreta(String status,String docSourceRefId) {
		return serviceImpl.acknowlegeInkreta(status,docSourceRefId);
	}

	public String JsonUpload(List<InvoiceJsonModel> obj) {
		ObjectMapper mapper = new ObjectMapper();
		ReqModel model = new ReqModel();
		System.out.println("=================="+obj.toString());
		String b64 = obj.get(0).invoiceJson.get(0).base64;
		Base64.Decoder decoder1 = Base64.getDecoder();
		byte[] decodedByteArray = decoder1.decode(b64.substring(b64.indexOf(",") + 1));
		String json = new String(decodedByteArray);
		Object transformedOutput = null;
		Object inputJSON = null;
		
		try {
			List chainrSpecJSON = JsonUtils.classpathToList("/spec.json");
			Chainr chainr = Chainr.fromSpec(chainrSpecJSON);
			inputJSON = JsonUtils.jsonToObject(json);
			transformedOutput = chainr.transform(inputJSON);
			System.err.println(JsonUtils.toJsonString(transformedOutput));
//			model=(ReqModel) transformedOutput;
			model = mapper.convertValue(transformedOutput, ReqModel.class);
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
//		model.setInvoiceAttachmentDetails(obj.get(0).getInvoiceAttachmentDetails());
//		model.invoiceDetails.setRecipientCode("3213");
		return JsonUtils.toJsonString(transformedOutput);
	}

}
