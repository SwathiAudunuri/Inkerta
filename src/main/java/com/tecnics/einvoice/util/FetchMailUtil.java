package com.tecnics.einvoice.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.mail.imap.IMAPFolder;
import com.tecnics.einvoice.config.ConfigFactory;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.model.InvoiceMetaDataModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.InvoiceResponseModel;
import com.tecnics.einvoice.response.InvoiceTransactionResponse;
import com.tecnics.einvoice.service.InvoiceDocumentDetailService;

public class FetchMailUtil {

	private static Logger log = Logger.getLogger(FetchMailUtil.class);
	private static boolean isDebugEnabled = true;
	UserLoginDetails userObj;
	
	@Autowired
	InvoiceDocumentDetailService detailService;
	ResponseEntity<ResponseMessage> response;

	@SuppressWarnings("static-access")
	public ResponseEntity<ResponseMessage> run(String mailID, String password, UserLoginDetails userObj,InvoiceDocumentDetailService detailService) {
		
		if(detailService!=null)
		{
		this.detailService=detailService;
		System.out.println("Detail Service is exists");
		}
		else
		{
			System.out.println("Detail Service is null creating one");
			this.detailService=new InvoiceDocumentDetailService();
		}
		
		if(userObj!=null)
		this.userObj=userObj;
		else
		{
			this.userObj=new UserLoginDetails();
			this.userObj.setUserId("010005");
		}
		
		SendMailUtil emailUtil = new SendMailUtil();
		FetchMailUtil ReadingEmailUtil = new FetchMailUtil();
		try {
			Session session = emailUtil.fetchSession(mailID, password);
			Store store = session.getStore("imaps");
			String host = ConfigFactory.getValueAsString("com.eInvoice.email.imap.host");
			int imapPort = ConfigFactory.getValueAsInt("com.eInvoice.email.imap.port");
			String inboxFolder = ConfigFactory.getValueAsString("com.eInvoice.email.imap.default.gmail.infolder");			
			store.connect(host, imapPort, mailID, password);
			IMAPFolder folderInbox = (IMAPFolder) store.getFolder(inboxFolder);
			folderInbox.open(Folder.READ_WRITE);
			Message messages[] = {};
			if (folderInbox.getUnreadMessageCount() > 0) {
				int newCount = folderInbox.getUnreadMessageCount();
				int messageCount = folderInbox.getMessageCount();
				messages = folderInbox.getMessages(messageCount - newCount + 1, messageCount);
			}
			System.out.println(messages.length);
			for (int i = 0; i < messages.length; i++) {
				try {
					Message msg = messages[i];
					String subject = msg.getSubject();
					System.out.println("Processing Subject = " + subject);
					ArrayList attachmentNames = getAttachmentNames(msg);
					String destinationFolderName= processEmail(subject, msg);
					//String queryDescription = getTextFromMessage(msg);
					
					boolean isFolderMoved = moveFolder(destinationFolderName, msg, folderInbox, store);
				} catch (Exception e) {
					log.info("Error in iterating emails" + e.getMessage());
					e.printStackTrace();
					
				}
			}
		} catch (Exception e) {
			System.out.println("Error in FetchMail Configurations " + e.getMessage());
			e.printStackTrace();
			
		} finally {

		}
		return response;
	}

	private String processEmail(String subject, Message msg) {
		String destinationFolder = null;
	
		
		if (validateSubject(ConfigFactory.getValueAsString("com.eInvoice.email.imap.subject.filter.InvoiceUpload"), subject)) {
			try {
			  ArrayList<String> attachmentList=new ArrayList<String>();
				if (msg.isMimeType("multipart/*")) {
					MimeMultipart mimeMultipart = (MimeMultipart) msg.getContent();

					int count = mimeMultipart.getCount();
					for (int i = 0; i < count; i++) {
						BodyPart bodyPart = mimeMultipart.getBodyPart(i);
						if (bodyPart != null) {
							String fileName = bodyPart.getFileName();
							if (fileName != null) {
								System.out.println("The File Name = " + fileName);
								if(fileName.equals("Invoice.txt"))
								{
									System.out.println("Invoice.txt Content = " + bodyPart.getContent().toString());
									processInvoice(bodyPart.getContent().toString());
								}
							}
						}

					}
				}
			}
			catch(Exception e)
			{
				log.info("Error in processing InvoiceUpload Email" + e.getMessage());
				e.printStackTrace();
			}
			
			
			
			// end of business logic of InvoiceUpload
			destinationFolder = "Processed";
		} else if (validateSubject(ConfigFactory.getValueAsString("com.eInvoice.email.imap.subject.filter.QueryCreation"), subject)) {
			// business logic
			destinationFolder = "Processed";
		} else if (validateSubject(ConfigFactory.getValueAsString("com.eInvoice.email.imap.subject.filter.StatusUpdate"), subject)) {
			// business logic
			destinationFolder = "Processed";
		} else {
			destinationFolder = "UnProcessed";
		}
		return destinationFolder;

	}
	
	private String processInvoice(String invoiceMetadata) 
	{
		try
		{
		ObjectMapper objectMapper = new ObjectMapper();
		List<InvoiceRequestModel> request = objectMapper.readValue(invoiceMetadata, new TypeReference<List<InvoiceRequestModel>>(){});
		System.out.println("Number of Invoices to be processed = " + request.size());
		String toBeProcessedInvoiceNum="";
		toBeProcessedInvoiceNum=request.get(0).getInvoiceDetails().getInvoicenum();
		
		// Iterating multiple Invoices from request
				for (InvoiceRequestModel details : request) {
					InvoiceResponseModel resp = new InvoiceResponseModel();
					InvoiceMetaDataModel invMeta = details.getInvoiceDetails();
					System.out.println("Invoice Num = " + invMeta.getInvoicenum());
					System.out.println("Invoice Date = " +invMeta.getInvoicedate());
					System.out.println("Supplier Note = " +invMeta.getSupplier_note());
					System.out.println("Invoice Value = " +invMeta.getTotal_invoice_value());
				}
				
				System.out.println("Invoking Invoice Save" + userObj.getUserId());
				
				response=detailService.save(request, userObj);
				
				InvoiceResponseModel invoiceResponseModel=null;
				ResponseMessage responseMessage=response.getBody();
				InvoiceTransactionResponse invPersistResponse=(InvoiceTransactionResponse)responseMessage.getResults();
				List<InvoiceResponseModel> invresplist=invPersistResponse.getInvRespone();
				String invoiceResponse = objectMapper.writeValueAsString(responseMessage);
				
				SendMailUtil emailUtil = new SendMailUtil();
				//String attachmentIds[]= {"This is Sample Content without base64"};
				String attachmentIds[]={invoiceResponse};
				String base64Flags[]= {"false"};
				String attachmentNames[]={"results.txt"};
				String mimeTypes[]= {"text/plain"};
				String mailSubject="";
				String mailBody="";
				String invoiceNum="";
				String document_Ref_Id="";
				boolean invoiceError=true;
				String invoiceErrorCode="";
				
			
				
				if(invresplist.size()>0)
				{
				invoiceResponseModel=invresplist.get(0);
				 System.out.println(" invoiceResponseModel = " + invoiceResponseModel.toString());
		            invoiceNum=invoiceResponseModel.getInvoiceNumber();
		            document_Ref_Id=invoiceResponseModel.getDoc_Ref_No();
		            invoiceError=invoiceResponseModel.getHasError();
		            invoiceErrorCode=invoiceResponseModel.getErrorCode();
		            System.out.println(" invoice Number Processed = " + invoiceNum);
				}
				
				
				if(invoiceError)
				{
					mailSubject="The Invoice # " + 	toBeProcessedInvoiceNum + " upload to Inkreta is failed";
					mailBody = "The Invoice # " + 	toBeProcessedInvoiceNum + " upload to Inkreta is failed due to " + invoiceErrorCode ;
				}
				else
				{
					mailSubject="The Invoice # " + 	invoiceNum + " is successfully uploaded into Inkreta";
					mailBody = "The Invoice # " + 	invoiceNum + " is successfully uploaded into Inkreta. The Inkreta Document Ref ID for you invoice is " + document_Ref_Id ;
				}
				emailUtil.sendEmail("inkreta.tecnics@gmail.com", "Welcome@123", new String[] {"noreplyekaarya@gmail.com", ""}, new String[] {}, new String[] {}, mailSubject, mailBody, base64Flags,attachmentIds,attachmentNames,mimeTypes,null,null,null);
				
				
				
				System.out.println("Response from save = " + response.toString() + " : " + response.getBody() + " toJson " + invoiceResponse);
		}
		catch(Exception e)
		{
			System.out.println("Error in processInvoice " + e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	private String getTextFromMessage(Message message) throws MessagingException, IOException {
		String result = "";
		if (message.isMimeType("text/plain")) {
			result = message.getContent().toString();
		}
		return result;
	}

	private ArrayList getAttachmentNames(Message message) throws MessagingException, IOException {
		  ArrayList<String> attachmentList=new ArrayList<String>();
		if (message.isMimeType("multipart/*")) {
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();

			int count = mimeMultipart.getCount();
			for (int i = 0; i < count; i++) {
				BodyPart bodyPart = mimeMultipart.getBodyPart(i);
				if (bodyPart != null) {
					String fileName = bodyPart.getFileName();
					if (fileName != null) {
						attachmentList.add(fileName);
					}
				}

			}
		}
		return attachmentList;
	}

	private boolean moveFolder(String folderName, Message msg, Folder folderInbox, Store store) {
		try {
			msg.setFlag(Flags.Flag.SEEN, true);
			logMessage("Email status set as SEEN.");
			msg.setFlag(Flags.Flag.ANSWERED, true);
			logMessage("Email status set as ANSWER.");
			List<Message> tempList = new ArrayList<>();
			tempList.add(msg);
			Message[] tempMessageArray = tempList.toArray(new Message[tempList.size()]);
			IMAPFolder folderDestination = (IMAPFolder) store.getFolder(folderName);
			folderInbox.copyMessages(tempMessageArray, folderDestination);
			msg.setFlag(Flags.Flag.SEEN, true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean validateSubject(String subjectFilter, String subject) {
		boolean status = true;
		if (!subject.contains(subjectFilter)) {
			status = false;
		}
		return status;
	}

	private void logMessage(String message) {
		if (isDebugEnabled) {
			log.info(message);
		}
	}

	public static void main(String[] args) {
		FetchMailUtil fetchMailUtil = new FetchMailUtil();
		String userName = "inkreta.tecnics@gmail.com";
		String password = "Welcome@123";
		fetchMailUtil.run(userName, password,null,null);
	}
}
