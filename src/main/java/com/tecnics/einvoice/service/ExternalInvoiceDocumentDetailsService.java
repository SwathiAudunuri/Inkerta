package com.tecnics.einvoice.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.AttachmentDetailsRepo;
import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceDocumentDetailsRepo;
import com.tecnics.einvoice.Repo.ExternalInvoiceStatusTrackerRepo;
import com.tecnics.einvoice.Repo.FolderObjectsRepo;
import com.tecnics.einvoice.constants.Images;
import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.entity.AttachmentDetails;
import com.tecnics.einvoice.entity.ExternalInvoiceDocumentDetails;
import com.tecnics.einvoice.entity.ExternalInvoiceStatusTracker;
import com.tecnics.einvoice.entity.FolderObjects;
import com.tecnics.einvoice.entity.InvoiceAttachmentDetail;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.exceptions.InternalException;
import com.tecnics.einvoice.model.ExternalInvoiceRequestModel;
import com.tecnics.einvoice.model.InvoiceAction;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.InvoiceResponseModel;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.SendMailUtil;

@Component
public class ExternalInvoiceDocumentDetailsService extends BaseService {
	public static final String CLASSNAME = "ExternalInvoiceDocumentDetailService";
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	@Autowired
	private AttachmentDetailsRepo attachmentDetailsRepo;
	
	@Autowired
	private Environment env;
	

	@Autowired
	private JdbcTemplate jdbcTemplate;
	

	@Autowired
	private FolderObjectsRepo folderObjectsRepo;
	
	@Autowired
	ExternalInvoiceDocumentDetailsRepo externalInvoiceDocumentDetailsRepo;
	@Autowired
	ExternalInvoiceStatusTrackerRepo externalInvoiceStatusTrackerRepo;
	
	
	/***
	 * save
	 * @param userName
	 * @return
	 */
	public ResponseEntity<ResponseMessage> save(ExternalInvoiceRequestModel request, UserLoginDetails userObj) {
		final String methodname = "Invoice TransactionResponse";
		
		InvoiceResponseModel resp = new InvoiceResponseModel();
		log.logMethodEntry(CLASSNAME + methodname);
		System.out.println("UserName in ExternalInvoiceDocumentDetailsService save="+userObj.getUserId());
		String partner_id = getPartnerIdfromUser(userObj.getUserId());


		boolean discardFlag = false;
		boolean draftFLag = false;
		boolean submitFlag = false;
		
	
		// Validating if the requested user is vendor else terminate the transaction
	/*	if (!canUpload(userObj.getUserId())) {
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INVOICE_ROLE_EXCEPTION.getKey(),Ex.formatMessage(Ex.INVOICE_ROLE_EXCEPTION.getKeyMessage()),Ex.INVOICE_ROLE_EXCEPTION.getKeyMessage())));
		} */
		
	try {

	
		if(request!=null) {
			
			//	return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.UNAUTHORIZED_INVOICE_SUBMISSION.getKey(),Ex.formatMessage(Ex.UNAUTHORIZED_INVOICE_SUBMISSION.getKeyMessage(),details.getInvoiceSupplierBuyerDetails().getSupplier_gstin()),Ex.UNAUTHORIZED_INVOICE_SUBMISSION.getKeyMessage())));
			
			String actionStatus =null;
			String actionCode =null;
			String documentRefId = null;
			InvoiceAction action=null;
					if (request.getActionDetails()!= null) {
						action = request.getActionDetails();
						if (action.getAction_code() != null) {
							actionCode= action.getAction_code();
							actionStatus =action.getAction_name();
							switch (actionCode) {
							case "001":	submitFlag = true;  break;
							case "002":	draftFLag = true;   break;
							case "003":	discardFlag = true;	 break;
							default:break;
							}
							request.getInvoiceDetails().setStatus(actionStatus);
						}
					}
		
			
			
				documentRefId = generateDocRefId(request.getInvoiceDetails().getInvoice_type_code());
				request.getInvoiceDetails().setDocument_ref_id(documentRefId);
	
			// generates document reference id for each invoice
			FolderObjects fo= osFolderCreateForInvoice(partner_id,documentRefId);
			String folderid = fo.getFolder_id();
			System.out.println("Complete Folder path created = " + folderid);

			
			
			
				request.getInvoiceDetails().setEcm_folder_id(folderid);
				request.getInvoiceDetails().setPartner_id(partner_id);
				request.getInvoiceDetails().setCreated_by(userObj.getUserId());
				ExternalInvoiceDocumentDetails response = externalInvoiceDocumentDetailsRepo.save(request.getInvoiceDetails());
				System.out.println("invoice details saved");
				saveInvoiceAttachmentsInOS(request,fo,userObj.getUserId());
				System.out.println("attachments saved");
				createExternalInvoiceStatusTracker(request.getActionDetails().getAction_name(),documentRefId,userObj );
				System.out.println("tracke details saved");

				resp.setInvoiceNumber(resp.getInvoiceNumber());
				resp.setDoc_Ref_No(response.getDocument_ref_id());
				resp.setHasError(false);
		}
	
				
				
			} catch (InternalException e) {
				e.printStackTrace();
				resp.setHasError(true);
				resp.setErrorCode(e.getMessage());
			} catch (Exception ex) {
				ex.printStackTrace();
				log.logErrorMessage(ex.getMessage(), ex);				
				resp.setHasError(true);
				resp.setErrorCode(ex.getMessage());

			} 

		
		//Invoice Item Exchange Queue
		try {
			if (!draftFLag && !discardFlag) {
			if(request.getInvoiceDetails().getTransaction_type().equalsIgnoreCase("receivable"))
				eMailInvoices(request,userObj);
				System.out.println("Mail Addresses");

				externalInvoiceDocumentDetailsRepo.setInvoice_statusForInvoiceDocumentDetail("Submitted",resp.getDoc_Ref_No() );
			}

		} catch (Exception e) {
			log.logErrorMessage(e.getMessage(), e);
		}

		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(resp));

	}
	
	public boolean eMailInvoices(ExternalInvoiceRequestModel request,UserLoginDetails userObj)
	{
		
		try {
		//String attachmentIds[]= {"This is Sample Content without base64"};
				/*String attachmentIds[]={"data:text/plain;base64,VGhpcyBpcyBzYW1wbGUgZW1haWw="};
				String base64Flags[]= {"true"};
				String attachmentNames[]={"Invoice.txt"};
				String mimeTypes[]= {"text/plain"}; */
		
		List<String> list_attachmentIds = new ArrayList<String>();
		List<String> list_base64Flags = new ArrayList<String>();
		
		List<String> list_attachmentNames = new ArrayList<String>();
		List<String> list_mimeTypes = new ArrayList<String>();
		String emailAttachmentDetails="";
		
		String bccMailAdresses[]=null;
		String ccMailAdresses[]=null;
		String toMailAdresses[]=null;
		
		
		
		
		
		List<AttachmentDetails> attachmentDetails = request.getAttachmentDetails();
		System.out.println("sending emails attachmentDetails count = " + attachmentDetails.size() );
		for (AttachmentDetails file : attachmentDetails) {	
			System.out.println(" email attachment File Details " );
			
			System.out.println(" Doc Name = " + file.getDoc_name() );
			list_attachmentIds.add("data:"+file.getMime_type()+";base64,"+file.getBase64());
			list_base64Flags.add("true");
			list_attachmentNames.add(file.getDoc_name());
			list_mimeTypes.add(file.getMime_type());
			
			emailAttachmentDetails+="<li>"+file.getDoc_name()+" of size "+ file.getDoc_size()+" kb</li>";
			
			
			System.out.println(" Doc Type = " + file.getDocType());
			System.out.println(" Doc size = " + file.getDoc_size());
			System.out.println(" Doc Mime type  = " + file.getMime_type() );
			System.out.println(" Folde  Id = " + file.getFolder_id() );
			System.out.println(" Ref Id = " + file.getRefId() );
			
		}
		
		
		String mailBody="<table style=\"width:100%;text-align:left;border-collapse:collapse;background-color:gold;\"> "
				+"<tr style=\"background-color:#3C8DBC;color:white;font-family:verdana;font-size:60%;\"> "
				//+"<th align=bottom>A new invoice has been submitted for your review and approval "
				+"<th width='60%' align=center>You have been  sent an invoice. You are receiving this invoice from <strong style=\"color:gold\"> <span>billingservice</span>@<span>inkreta</span>.<span>com</span> </strong>because "+request.getInvoiceDetails().getPartner_company_name()+" is using Inkreta to manage invoices."
				+"</th><th width='40%'><img align=right src=\"cid:inkretalogo\" alt=\"inkreta logo\"> </th>  </tr>"
				+" <tr style=\"height: 70%; background-color:#F5F5F4;color:black;\">"
				+"<td colspan=\"2\"><br><p> <span style=\"font-family:verdana;font-size:160%;color:blue;\" > From:   </span> <span style=\"font-family:verdana;font-size:160%;color:black;\" >   \"Sent on behalf of - Gopala Rao Vanam' - </span> </p>"
				+"<p> Dear Customer, </p> <p> Thanks for your business !!! </p> <p> The invoice "+request.getInvoiceDetails().getInvoicenum()+" is attached with this email.</p>"
	+"<p> Here's an overview of the invoice for your reference. </p> <p> <strong> Invoice Overview: </strong> </p>"
	+"<p> Invoice # : "+request.getInvoiceDetails().getInvoicenum()+" </p> <p> Date : "+request.getInvoiceDetails().getInvoicedate()+" </p> <p> Amount: "+request.getInvoiceDetails().getTotal_invoice_value()+" </p> <p> This submission contained the following files. "
+"<ul>  "+emailAttachmentDetails+"	</ul>	</p>"
+"<p> it was great working with you. Looking forward to working with you again. </p>" 
+"<p>** Please do not reply to this e-mail. For any queries on this submission please contact : " + userObj.getFirstName() + " " + userObj.getLastName() + " ( "+userObj.getEmail()+" ).** </p>"
+"<p> Warm Regards</p> 	<p>Team InKreta </p> 	</td> 	</tr> 	</table>";
		
		
		SendMailUtil emailUtil = new SendMailUtil();
		//String attachmentIds[]= {"This is Sample Content without base64"};
		/*String attachmentIds[]={"data:text/plain;base64,VGhpcyBpcyBzYW1wbGUgZW1haWw="};
		String base64Flags[]= {"true"};
		String attachmentNames[]={"Invoice.txt"};
		String mimeTypes[]= {"text/plain"}; */
		String attachmentIds[]=new String[list_attachmentIds.size()];
		attachmentIds = list_attachmentIds.toArray(attachmentIds);
		String base64Flags[]= new String[list_base64Flags.size()];
		base64Flags = list_base64Flags.toArray(base64Flags);
		String attachmentNames[]=new String[list_attachmentNames.size()];
		attachmentNames = list_attachmentNames.toArray(attachmentNames);
		String mimeTypes[]= new String[list_mimeTypes.size()];
		mimeTypes = list_mimeTypes.toArray(mimeTypes);
		String mailSubject="Invoice from "+request.getInvoiceDetails().getPartner_company_name()+" (Invoice#: "+request.getInvoiceDetails().getInvoicenum()+")";
		if(request.getMailAddresses()!=null)
		{
		if(request.getMailAddresses().getToAddresses()!=null)
		{
			toMailAdresses = new String[request.getMailAddresses().getToAddresses().size()];
		toMailAdresses=request.getMailAddresses().getToAddresses().toArray(toMailAdresses);
		
		
		if(request.getMailAddresses().getCcAddresses()!=null)
		{
			ccMailAdresses = new String[request.getMailAddresses().getCcAddresses().size()];
		ccMailAdresses=request.getMailAddresses().getCcAddresses().toArray(ccMailAdresses);
		}
		
		if(request.getMailAddresses().getBccAddresses()!=null)
		{
		bccMailAdresses = new String[request.getMailAddresses().getBccAddresses().size()];
		bccMailAdresses=request.getMailAddresses().getBccAddresses().toArray(bccMailAdresses);
		}
		System.out.println("Send mail calling *****************");
		//images
		String base64Images[]={Images.inkreta_email_logo};
		String base64ContentIdImagesFileNames[]= {"logo_inkreta.png" };
		String base64ContentIds[]= {"<inkretalogo>"};
		
		
		emailUtil.sendEmail(env.getProperty("com.eInvoice.email.billservice.from"), env.getProperty("com.eInvoice.email.billservice.from.password"), toMailAdresses, ccMailAdresses, bccMailAdresses, mailSubject, mailBody, base64Flags,attachmentIds,attachmentNames,mimeTypes,base64Images,base64ContentIds,base64ContentIdImagesFileNames);	
		return true; 
		
		}
		}
		return false;
		
		
		   
		
		}
		catch(Exception e)
		{
			System.out.println("Error while sending email");
			e.printStackTrace();
			return false;
		}
		
	}
	/**
	 * isVendor
	 * @param userID
	 * @return
	 */
	public boolean canUpload(String userID) {
		String role=getRole(userID);
		if (role!= null && (role.startsWith("vendor") || role.startsWith("businesspartner")))
			return true;
		else
			return false;

	}
	
	/**
	 * generateDocRefId
	 * 
	 * @param subtypeCode
	 * @return
	 */
	public String generateDocRefId(String subtypeCode) {
		String sql = "select einvoicing.generate_document_ref_id(?) ";
		if (subtypeCode == null)
			subtypeCode = "INV";
		String refId = jdbcTemplate.queryForObject(sql, new Object[] { subtypeCode }, String.class);
		return refId;
	}
	
	
	public FolderObjects osFolderCreateForInvoice(String partnerId,String documentRefId) {
		Path directories =null;
		FolderObjects fo=null;
		try {
		
		 	String rootFolder=env.getProperty("os.folder.invoices");
		 	String folderSeperator=env.getProperty("os.file.seperator");
		 
		 	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();        
			String dateToStr = dateFormat.format(date);
			System.out.println("Folder rootFolder is "+ rootFolder);
			System.out.println("Folder Date is "+ dateToStr);
		 
			Path path = Paths.get(rootFolder+folderSeperator+partnerId+folderSeperator+dateToStr+folderSeperator+documentRefId);
			//Path path= Paths.get("d:\\inkretainvoices\\V_10031\\20210922\\I210922000073");
								       
	       
	        if (!Files.exists(path)) {
	        
	        	fo=new FolderObjects();
	        	fo.setFolder_id(UUID.randomUUID().toString());
	        	
	            System.out.println("directories created: " + directories);
	            directories =Files.createDirectories(path);
	            fo.setFolderPath(directories.toString());
	            fo.setFolder_type("Invoice");
	            fo.setCreated_on(new Timestamp(new Date().getTime()));
	            System.out.println("directories created: " + directories);
	            folderObjectsRepo.save(fo);
	        } else {
	        	directories=path;
	        	List<FolderObjects> folderObjects=folderObjectsRepo.findByFolderPath(directories.toString());
	        	fo=folderObjects.get(0);
	            System.out.println("Directory already exists");
	            System.out.println("Folder ID = " + fo.getFolder_id());
	        }
	        
		}
		catch (Exception e) {
			System.out.println("Error in osFolderCreateForInvoice " + e.getMessage());
			e.printStackTrace();
		}

		return 	fo;	

	}
	
private void saveInvoiceAttachmentsInOS(ExternalInvoiceRequestModel request,FolderObjects fo,String userName) {
		
		try {
			if (request.getAttachmentDetails() != null) {
				System.out.println("saveInvoiceAttachmentsInOS in ExternalInvoiceDocumentDetailsService");
				List<AttachmentDetails> attachmentDetails = request.getAttachmentDetails();
				System.out.println("saveInvoiceAttachmentsInOS attachmentDetails count = " + attachmentDetails.size() );
				for (AttachmentDetails file : attachmentDetails) {	
					System.out.println(" File Details " );
					file.setDocId(UUID.randomUUID().toString());
					System.out.println(" Doc Id = " + file.getDocId() );
					file.setDocCategory("externalinvoice");
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

					file.setRefId(request.getInvoiceDetails().getDocument_ref_id());
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

private void createExternalInvoiceStatusTracker(String action, String document_ref_id, UserLoginDetails userObj)
{
	// updating invoice Status Tracker
	
	try {
	System.out.println("Creating ExternalInvoiceStatusTracker object ");
	ExternalInvoiceStatusTracker externalInvoiceStatusTracker=new ExternalInvoiceStatusTracker();
	
	externalInvoiceStatusTracker.setActionBy(userObj.getUserId());
	if (action != null && action.equals("Draft"))
	{
		externalInvoiceStatusTracker.setAction("Draft");
		externalInvoiceStatusTracker.setActionComments("The Invoice has been uploaded as a Draft");
	}
	else if(action!=null && action.equals("Submit"))
	{
		externalInvoiceStatusTracker.setAction("Submit");
		externalInvoiceStatusTracker.setActionComments("The Invoice has been submitted");
	}
	externalInvoiceStatusTracker.setDocumentRefId(document_ref_id);
	
	externalInvoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
	externalInvoiceStatusTracker.setActionType("Invoice Submitted");
	
	externalInvoiceStatusTrackerRepo.save(externalInvoiceStatusTracker);
	} catch (Exception e) {
		System.out.println("Error while saving  externalInvoiceStatusTracker" + e.getMessage());
		e.printStackTrace();
		log.logErrorMessage(e.getMessage(), e);
		
	}
}
	

/**
 * 
 * @param documentRefId
 * @return
 */
public ExternalInvoiceDocumentDetails getInvoiceDetails(String documentRefId) {
	try {
		return (ExternalInvoiceDocumentDetails) jdbcTemplate.queryForObject(SQLQueries.GET_EXTERNAL_INVOICE_DETAILS,
				new Object[] { documentRefId }, new BeanPropertyRowMapper(ExternalInvoiceDocumentDetails.class));
	} catch (EmptyResultDataAccessException e) {
		return null;
	}

}

public List<AttachmentDetails> getSupportingInvoiceDocumentDetails(UserLoginDetails userObj,String documentRefId, String docType) throws IOException {
	
	List<AttachmentDetails> attachmentDetails=attachmentDetailsRepo.findByRefIdAndDocType(documentRefId, docType);
	return attachmentDetails;
}


public AttachmentDetails getPDFInvoiceDocument(UserLoginDetails userObj,String documentRefId) {
	AttachmentDetails attachmentDetails = null;
	try
	{
	String partnerId = userObj.getPartnerId();
	// need to develop access security logic
	
		attachmentDetails = attachmentDetailsRepo.findByRefIdAndDocCategoryAndDocType(documentRefId, "externalinvoice", "Invoice");
		
		
		if(attachmentDetails==null)
			return null;
		
			System.out.println(" search details of external attachmentDetail with docRefId " + documentRefId + " Type = + docType");
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
		System.out.println("Error in externalinvoice  getPDFInvoiceDocument ");
		e.printStackTrace();
	}

	return attachmentDetails;
}


public AttachmentDetails getSupportingInvoiceDocument(UserLoginDetails userObj,String docId) {
	AttachmentDetails attachmentDetails = null;
	try
	{
	String partnerId = userObj.getPartnerId();
	// need to develop access security logic
	
		attachmentDetails = attachmentDetailsRepo.findByDocId(docId);
		
		
		if(attachmentDetails==null)
			return null;
		
			System.out.println(" search details of externalinvoice attachmentDetail with docRefId " + docId + " Type = + docType");
			System.out.println(" DocPath = " + attachmentDetails.getDocPath());
			System.out.println(" DoName = " + attachmentDetails.getDoc_name());
		
			byte[] inFileBytes = Files.readAllBytes(Paths.get(attachmentDetails.getDocPath())); 			
			//byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);
			
			attachmentDetails.setBase64(java.util.Base64.getEncoder().encodeToString(inFileBytes));
	
		

	}
	catch(FileNotFoundException fe)
	{
		System.out.println("There is no Supportng document avaialble in the drive " + attachmentDetails.getDocPath());
		return null;
	}
	catch(Exception e)
	{
		System.out.println("Error in externalinvoice  Supportng document ");
		e.printStackTrace();
	}

	return attachmentDetails;
}

}
