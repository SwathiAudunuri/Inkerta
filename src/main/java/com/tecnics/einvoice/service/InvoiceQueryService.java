package com.tecnics.einvoice.service;


import java.io.File;
import java.io.FileOutputStream;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.AttachmentDetailsRepo;
import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.FolderObjectsRepo;
import com.tecnics.einvoice.Repo.InvoiceQueryRepo;
import com.tecnics.einvoice.entity.AttachmentDetails;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.FolderObjects;
import com.tecnics.einvoice.entity.InvoiceQuery;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.model.InvoiceMetaDataModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.serviceImpl.InvoiceDetailsServiceImpl;
@Component
public class InvoiceQueryService extends BaseService{
	
	
		@Autowired
		private Environment env;
		
	@Autowired
	InvoiceQueryRepo invoiceQueryRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	@Autowired
	private AttachmentDetailsRepo attachmentDetailsRepo;
	
	@Autowired
	InvoiceDetailsServiceImpl invoiceDetailsServiceImpl;
	
	
	@Autowired
	private FolderObjectsRepo folderObjectsRepo;
	

	public List<InvoiceQuery> findAll() {
		return (List<InvoiceQuery>) invoiceQueryRepo.findAll();
	}
	
	public List<InvoiceQuery> findById(int id) {
		List<InvoiceQuery> invoiceQuery=invoiceQueryRepo.findById(id);
		if(invoiceQuery.size()>0)
		{
			invoiceQuery.get(0).setAttachmentDetails(attachmentDetailsRepo.findByRefIdAndDocCategory(String.valueOf(id), "Query"));
		}
		return invoiceQuery;
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

	public TransactionResponse save(InvoiceQuery queryObj, UserLoginDetails userObj) {
		int failCount =0,successCount=0;
		System.out.println("Inside save of invoiceQueries");
			TransactionResponse invoiceQueryResponse = new TransactionResponse();
		try {
			InvoiceQuery response = invoiceQueryRepo.save(queryObj);
			invoiceQueryResponse.setRefid(response.getId());
			saveQueryAttachmentsInOS(queryObj,userObj,String.valueOf(response.getId()));
			successCount++;
		}
		
		catch(Exception ex){
			failCount ++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("InvoiceQueryService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
		}
		invoiceQueryResponse.setFailureTransactionsCount(failCount);
		invoiceQueryResponse.setSuccessTransactionsCount(successCount);
		invoiceQueryResponse.setTotalTransactionsCount(failCount+successCount);
		return invoiceQueryResponse;
	}
	
	
	
	
public void saveQueryAttachmentsInOS(InvoiceQuery details, UserLoginDetails userObj, String queryRefId) {
		
		try {
			System.out.println("inside saveSupportingAttachmentsInOS");
			System.out.println(" attachments size=" + details.getAttachmentDetails().size());
			InvoiceRequestModel irm=null;
			
			if (details.getAttachmentDetails() != null) {
				
				irm=invoiceDetailsServiceImpl.getInvoiceDetails(details.getDocumentRefId());
				InvoiceMetaDataModel imdm=irm.getInvoiceDetails();
				String customerPartnerId=imdm.getCustomer_partner_id();
				
				
				FolderObjects fo=osFolderCreateForOnboarding(customerPartnerId,details.getDocumentRefId(), imdm.getCreated_date());
				
				
				List<AttachmentDetails> attachmentDetails = details.getAttachmentDetails();
				System.out.println("saveQueryAttachmentsInOS attachmentDetails count = " + attachmentDetails.size() );
				for (AttachmentDetails file : attachmentDetails) {	
					System.out.println(" File Details " );
					file.setDocId(UUID.randomUUID().toString());
					file.setDocCategory("Query");
					System.out.println(" Doc Id = " + file.getDocId() );
					
					System.out.println(" Doc Name = " + file.getDoc_name() );
					System.out.println(" Doc Type = " + file.getDocType());
					System.out.println(" Doc size = " + file.getDoc_size());
					System.out.println(" Doc Mime type  = " + file.getMime_type() );
					file.setFolder_id(fo.getFolder_id());
					System.out.println(" Folde  Id = " + file.getFolder_id() );
					file.setRefId(queryRefId);
					System.out.println(" Ref Id = " + file.getRefId() );
				
					
					
					
					System.out.println(" folderid=" + fo.getFolderPath() + " ; DocType = " +  file.getDocType() + " ; MimeType= " +file.getMime_type() + " ; Doc Name = " + file.getDoc_name()  );
					

					String folderSeperator=env.getProperty("os.file.seperator");
					 File document = new File(fo.getFolderPath()+folderSeperator+file.getDoc_name());

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
					
					
				
					file.setCreatedBy(userObj.getUserId());
					System.out.println("attachmentDetailsRepo Repository save is called");
					attachmentDetailsRepo.save(file);
				}
			}
		} catch (Exception ex) {
			log.logErrorMessage(ex.getMessage(), ex);
			ex.printStackTrace();
		}

	}


public FolderObjects osFolderCreateForOnboarding(String partnerId,String documentRefId,Date invoiceSubmittedDate) {
	Path directories =null;
	FolderObjects fo=null;
	try {
	
	 	String rootFolder=env.getProperty("os.folder.queries");
	 	String folderSeperator=env.getProperty("os.file.seperator");
	 
	 	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");		;        
		String dateToStr = dateFormat.format(invoiceSubmittedDate);
		System.out.println("Folder rootFolder is "+ rootFolder);
		System.out.println("Folder Date is "+ dateToStr);
	 
		Path path = Paths.get(rootFolder+folderSeperator+partnerId+folderSeperator+dateToStr+folderSeperator+documentRefId);
							       
       
        if (!Files.exists(path)) {
        
        	fo=new FolderObjects();
        	fo.setFolder_id(UUID.randomUUID().toString());
        	
            System.out.println("directories created: " + directories);
            directories =Files.createDirectories(path);
            fo.setFolderPath(directories.toString());
            fo.setFolder_type("Onboarding");
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


public ResponseEntity<ResponseMessage> getDocument(UserLoginDetails uld, String docId) {
	AttachmentDetails attachmentDetails = null;
	try
	{
		attachmentDetails=attachmentDetailsRepo.findByDocIdAndDocCategory(docId,"Query");

		if(attachmentDetails!=null)
		{
			System.out.println(" search details of attachmentDetails with docId " + docId );
			System.out.println(" DocPath = " + attachmentDetails.getDocPath());
			System.out.println(" DoName = " + attachmentDetails.getDoc_name());
		
			byte[] inFileBytes = Files.readAllBytes(Paths.get(attachmentDetails.getDocPath())); 			
			//byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);				
			attachmentDetails.setBase64(java.util.Base64.getEncoder().encodeToString(inFileBytes));
		}


	}
	catch(Exception ex){
		ex.printStackTrace();
		ErrorLog error = new ErrorLog();
		error.setComponentName("InvoiceQueryService");
		error.setError(ex.getMessage());
		error.setErrorMessage(getStackTrace(ex));
		error.setModule("Save");
		error.setSource("Java");
		//error.setUserId(userObj.getUserId());
		errorLogRepo.save(error);
	}

	return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(attachmentDetails));
}



}
