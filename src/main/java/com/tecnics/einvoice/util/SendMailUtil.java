package com.tecnics.einvoice.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.DatatypeConverter;


import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import com.tecnics.einvoice.config.ConfigFactory;
import com.tecnics.einvoice.constants.Images;






public class SendMailUtil {
	
	
	private static final Logger log = LoggerFactory.getLogger(SendMailUtil.class);
	private boolean isDebugEnabled = false;
	private boolean isPerformanceEnabled = false;
	private long currentTimeMilli = -1;
	private static final String EMAIL_PATTERN = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	
	public SendMailUtil() {
		
	}
	
	public long  startPerformanceLog(String methodName) {
		if(isDebugEnabled)
			log.info(" Performance of Method [ " +methodName+ " start !] ");
		return System.currentTimeMillis();
	}
	
	public  void logElapsedTime(String methodName , long startTimeMillis) {
		
		if(methodName != null && startTimeMillis  != -1) {
			long elapsedTime = System.currentTimeMillis() - startTimeMillis ;
			if(isDebugEnabled)
				log.info(" Performance of Method   : [ "+methodName+" ] :: "+elapsedTime + " milli seconds End ! ");
		}
	}
	
	public String sendEmail(String fromMail , String password, String toMails[] , String ccMails[] ,String bccMails[] , String mailSubject, String mailBody , String base64Flags[], String attachmentIds[], String attachmentNames[], String mimeTypes[], String base64ContentIdImages[],String base64ContentIds[],String base64ContentIdImagesFileNames[])  throws Exception {
		String status = "Success";
		if(isDebugEnabled)
			log.info(" START [ sendEmail ]");
		if(isPerformanceEnabled) 
			currentTimeMilli = startPerformanceLog("sendEmail");
		if(isDebugEnabled)
			log.info(" METHOD : sendEmail : PARAMS :: fromMail [ "+fromMail+" , toMails "+ logParam(toMails) +", ccMails "+logParam(ccMails) + " , bccMails "+logParam(bccMails) +" , attachmentIds "+logParam(attachmentIds));

	
	
	
		
		// fetch the email session
		try {
			
			Session session = fetchSession(fromMail, password);
			// fetch all emails 
			Address toAddresses [] = null;
			Address ccAddresses [] = null;
			Address bccAddresses [] = null;
			StringBuffer unableToSendEmails = null;
			
			try {
				toAddresses = fetchEmailAddresses(fetchValidEmails(toMails));
				ccAddresses = fetchEmailAddresses(fetchValidEmails(ccMails));
				bccAddresses = fetchEmailAddresses(fetchValidEmails(bccMails));
				unableToSendEmails = fetchInvalidEmails(fetchValidEmails(toMails),fetchValidEmails(ccMails),fetchValidEmails(bccMails)); // do it later
			} catch (Exception e) {
				status = e.getMessage();
				log.info(e.getMessage());
			}
			
			MimeMessage mimeMessage = new MimeMessage(session);
			mimeMessage.setFrom(new InternetAddress(fromMail));
			if(toAddresses != null)
				mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO,
						toAddresses);
			if(ccAddresses != null)
				mimeMessage.setRecipients(javax.mail.Message.RecipientType.CC,
						ccAddresses);
			if(bccAddresses != null)
				mimeMessage.setRecipients(javax.mail.Message.RecipientType.BCC,
						bccAddresses);
			
			if(mailSubject != null)
				mimeMessage.setSubject(mailSubject);
			
			if(mailBody != null)
				mimeMessage.setContent(fetchMultiPartMessage(base64Flags,attachmentIds,attachmentNames,mimeTypes, mailBody,base64ContentIdImages,base64ContentIds,base64ContentIdImagesFileNames));
			
			
			if(isDebugEnabled)
				log.info(" sending email .......");
			
			try {
				Transport.send(mimeMessage);
				if(isDebugEnabled)
					log.info(" sending email completed .......");
				
				if(unableToSendEmails!=null && unableToSendEmails.length() > 0)
					log.info("Unable to sent email to below users because of invalid emails : "+unableToSendEmails);
			} catch (Exception e) {
				status = e.getMessage();
				log.info(e.getMessage());
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			status = e.getMessage();
			log.info(e.getMessage());
		}
		if(isDebugEnabled)
			log.info(" EXIT [ sendEmail ]");
		
		if(isPerformanceEnabled)
			logElapsedTime("sendEmail", currentTimeMilli);
		
		return status;
	}
	
	
	
	private StringBuffer fetchInvalidEmails(Email toEmails[] , Email ccEmails[] ,Email bccEmails[]) throws Exception {
		if(isDebugEnabled)
			log.info(" ENTER [ fetchInvalidEmails ]");
		StringBuffer emailsBuffer = null;
		
		try {
			emailsBuffer = new StringBuffer();
			if(toEmails != null) {
				for(Email email : toEmails) {
					if(!email.isValidId())
						emailsBuffer.append(email.getEmailId()).append(",");
				}
			}
			if(ccEmails != null) {
				for(Email email : ccEmails) {
					if(!email.isValidId())
						emailsBuffer.append(email.getEmailId()).append(",");
				}
			}
			if(bccEmails != null) {
				for(Email email : bccEmails) {
					if(!email.isValidId())
						emailsBuffer.append(email.getEmailId()).append(",");
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());;
		}
		
		if(isDebugEnabled)
			log.info(" EXIT [ fetchInvalidEmails ]");
		return emailsBuffer;
	}
	
	private Multipart fetchMultiPartMessage(String base64Flags[],String attachmentIds[],String attachmentNames[],String mimeTypes[], String mailBody, String base64ContentIdImages[],String base64ContentIds[],String base64ContentIdImagesFileNames[]) throws Exception {
		if(isDebugEnabled)
			log.info(" ENTER [ fetchMultiPartMessage ]");
		Multipart multiPart = null;
		try {
			multiPart = new MimeMultipart();
			multiPart.addBodyPart(getSimpleBodyPart(mailBody));
			if(attachmentIds != null && attachmentIds.length > 0 ){
				try {
				
					MimeBodyPart attachmentsPart [] = fetchAttachmentsBodyPart(base64Flags,attachmentIds,attachmentNames, mimeTypes);
				
					for(int j = 0 ;  j < attachmentsPart.length ; j++) {
						if(attachmentsPart[j] != null)
							multiPart.addBodyPart(attachmentsPart[j]);
					}
					
					
					
					
				} catch (Exception e) {
					log.info(e.getMessage());;
				}
				
			}
			
			
			// new code for image
			
			// create a new imagePart and add it to multipart so that the image is inline attached in the email
			
			if(base64ContentIdImages!=null && base64ContentIdImages.length > 0)
			{
	       System.out.println("Adding image");
	       for(int j = 0 ;  j < base64ContentIdImages.length ; j++) {
	    	//String inkreta_email_logo="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";
	    	 
			byte[] rawImage = Base64.getDecoder().decode(base64ContentIdImages[j]);
	        BodyPart imagePart = new MimeBodyPart();
	        ByteArrayDataSource imageDataSource = new ByteArrayDataSource(rawImage,"image/png");

	        imagePart.setDataHandler(new DataHandler(imageDataSource));
	        imagePart.setHeader("Content-ID", base64ContentIds[j]);
	        imagePart.setFileName(base64ContentIdImagesFileNames[j]);
	        multiPart.addBodyPart(imagePart);
	       }
			}
			
			//end of new code for image
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
		if(isDebugEnabled)
			log.info(" EXIT [ fetchMultiPartMessage ]");
		return multiPart;
	}
	
	private MimeBodyPart[] fetchAttachmentsBodyPart(String base64Flags[], String attachmentIds[],String attachmentNames[], String mimeTypes[])throws Exception {
		if(isDebugEnabled)
			log.info(" ENTER [ fetchAttachmentsBodyPart ]");
		ArrayList<MimeBodyPart> attachmentMime = null;
		try {
			attachmentMime =  new ArrayList<MimeBodyPart>();
			for(int j = 0 ; j < attachmentIds.length ; j++) {
				//boolean isBase64  = isBase64Content(attachmentIds[j]);
				boolean isBase64=base64Flags[j].equals("true");
				/*if(isDebugEnabled)
					log.info("isBase64 Content"+isBase64);*/
				
					MimeBodyPart mimeBodyPart = new MimeBodyPart();
					String mimeType =null;
					byte  attachmentBase64[]=null;
					if(isBase64) {
						System.out.println("Attachment content is base64");
						
					attachmentBase64  = DatatypeConverter.parseBase64Binary(attachmentIds[j]);
					
					//log.info("parsed Base binary="+new String(attachmentBase64));
					mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(attachmentBase64));
				
					//log.info("mimeType="+mimeType);
					if(mimeType==null)
					{
					
						mimeType=getMimeType(new String(attachmentIds[j]));
					
						if(mimeType!=null)
						{
						    String delims="[,]";
						    String[] tokens = new String(attachmentIds[j]).split(delims);
					        //System.out.println("Tokens [0]"+ tokens[0]);
					        //System.out.println("Tokens [1]"+ tokens[1]);
						  
					        attachmentBase64= DatatypeConverter.parseBase64Binary(tokens[1]);
					       
					       // System.out.println("Decoded string =   " +new String(attachmentBase64));
						}
					}
					//log.info("mimeType from getMimeType ="+mimeType);
					}
					else
					{
						System.out.println("Attachment content is plain and not a base64");
						mimeType=mimeTypes[j];
						attachmentBase64=attachmentIds[j].getBytes();
					}
					
					
					String docName = attachmentNames[j];
					String docMimetype = mimeType;
					mimeBodyPart.setFileName(attachmentNames[j]);
					mimeBodyPart.setDataHandler(new DataHandler(
								new CustomDataSource(new ByteArrayInputStream(attachmentBase64), docName, docMimetype)
							));
					attachmentMime.add(mimeBodyPart);
				
				
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		if(isDebugEnabled)
			log.info(" EXIT [ fetchAttachmentsBodyPart ]");
		return attachmentMime.toArray(new MimeBodyPart[attachmentMime.size()]);
		
	}
	
	public String getMimeType(String base64Data)
	{
		   // log.info("inside getMimeType  " + base64Data );
		
		    final Pattern mime = Pattern.compile("^data:([a-zA-Z0-9]+/[a-zA-Z0-9]+).*,.*");
		    final Matcher matcher = mime.matcher(base64Data);
		    if (!matcher.find())
		        return "";
		    return matcher.group(1).toLowerCase();
		}
	
	
	public boolean isBase64Content(String attachmentId) {
		if(isDebugEnabled)
			log.info(" ENTER [ isBase64Content ]");
		boolean isBase64 = true;
		try {
			if(attachmentId.contains("idd_")) {
				isBase64 = false;
				return isBase64;
			}
			if(attachmentId.charAt(0) == '{' && attachmentId.charAt(attachmentId.length() -1) == '}') {
				isBase64 = false;
				return isBase64;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		if(isDebugEnabled)
			log.info(" EXIT [ isBase64Content ]");
		return isBase64;
	}
	
	private MimeBodyPart getSimpleBodyPart(String body) {
		MimeBodyPart simpleBody = new MimeBodyPart();
		try {
			simpleBody.setContent(body, "text/html");
		} catch (Exception exception) {
			log.info(exception.getMessage());
		}
		return simpleBody;
	}
	
	private String  logParam (String param[]) {
		StringBuffer paramBuffer  = new StringBuffer();
		if(param != null) {
			for(int j = 0 ; j < param.length ; j++) {
				paramBuffer.append(param[j]+" , ");
			}
		} else {
			paramBuffer.append(" NULL ");
		}
		return paramBuffer.toString();
	}
	
	public Session fetchSession(String from, String password) throws Exception {
		if(isDebugEnabled)
			log.info(" ENTER [ fetchSession ]");
		// set up basic properties 
		boolean enableAuthenticSession = Boolean.parseBoolean(ConfigFactory.getValueAsString("com.eInvoice.email.default.authentication.enable"));
		if(isDebugEnabled)
			log.info("  email session enable authentication property :"+enableAuthenticSession);
		
		Properties props = null;
		Session emailSession = null;
		try {
			props  = new Properties();
			if(enableAuthenticSession) 
				props.put("mail.smtp.auth", "true");
			else
				props.put("mail.smtp.auth", "false");
			
			String host = ConfigFactory.getValueAsString("com.eInvoice.email.smtp.host");
			String portNumber = ConfigFactory.getValueAsString("com.eInvoice.email.smtp.port");
			
			System.out.println("SMTP Host = " + host);
			System.out.println("SMTP Port = " + portNumber);
			
			if(isDebugEnabled)
				log.info(" Email host server name$1 :"+host+" , portNumber :"+portNumber);
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", portNumber);
			props.put("mail.smtp.ssl.trust", host);
			props.put("mail.imap.ssl.enable", "true");
			props.put("mail.imap.ssl.trust", "*");
			
//			props.put("mail.imap.port", 587);
			
//			props.put("mail.imap.ssl.enable", "false");
			  
			
//			props.put("mail.smtp.socketFactory.fallback", "true");
//			props.put("mail.store.protocol", "imaps");
//		    Properties props = new Properties();
//		    props.put("mail.host", "outlook.office365.com");
//	        props.put("mail.store.protocol", "pop3s");
//	        props.put("mail.pop3s.auth", "true");
//	        props.put("mail.pop3s.port", "587");
			
		
			
			boolean requireTTL = Boolean.valueOf(ConfigFactory.getValueAsString("com.eInvoice.email.enable.ttl"));
			if(isDebugEnabled)
				log.info(" Email Enable TTL :"+requireTTL);
			if(requireTTL)
				props.put("mail.smtp.starttls.enable", "true");
			else 	
				props.put("mail.smtp.starttls.enable", "false");
				
			
		}  catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
//		enableAuthenticSession = false;
		if(enableAuthenticSession) {
			try {
//				final String  DEFAULT_FROM = ConfigFactory.getConfigProp("org.drl.email.default.from");
//				final String DEFAULT_PASSWORD = ConfigFactory.getConfigProp("org.drl.email.default.from.password") ;
				log.info("TRYING TO GET SESSION WITH [" + from + ", " + password+"]");
				emailSession = Session.getInstance(props,
						new Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
									return new PasswordAuthentication(from,
											password);
							}
						});
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		} else {
			try {
				emailSession = Session.getDefaultInstance(props);
			} catch (Exception e) {
				log.info(e.getMessage());
			}
		}
		
		if(isDebugEnabled)
			log.info(" EXIT [ fetchSession ]");
		
		return emailSession;
	}
	
	private SendMailUtil.Email[]  fetchValidEmails(String emailParams[]) throws Exception {
		
		if(isDebugEnabled)
			log.info(" ENTER [ fetchValidEmails ]");
		
		if(emailParams == null)		
			throw new Exception("Invalid From EmailIDs");
		ArrayList<Email> emails = null;
		try {
			emails =  new ArrayList<SendMailUtil.Email>();
			for(int j = 0 ; j < emailParams.length ; j++) {
				Email email = new Email();
				boolean validEmail = emailParams[j].matches(EMAIL_PATTERN);
				email.setEmailId(emailParams[j]);
				email.setValidId(validEmail);
				emails.add(email);
			}
			
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		if(isDebugEnabled)
			log.info(" EXIT [ fetchValidEmails ]");
		
		return emails.toArray(new Email[emails.size()]);
	} 
	
	private Address[] fetchEmailAddresses(Email[] emails) throws Exception {
		if(isDebugEnabled)
			log.info(" ENTER [ fetchEmailAddresses ]");
		if(emails == null)
			throw new Exception("fetchEmailAddresses");
		
		ArrayList<Address> emailAddress = null;
		
		try {
			emailAddress =  new ArrayList<Address>();
			for(int k = 0 ; k < emails.length ; k++) {
				if(emails[k].isValidId()) {
					try {
						emailAddress.add(new InternetAddress(emails[k].getEmailId()));
					} catch (Exception e) {
						;
					}
					
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		
		if(isDebugEnabled)
			log.info(" EXIT [ fetchEmailAddresses ]");
		return emailAddress.toArray(new Address[emailAddress.size()]);
	} 
	
	public class Email {
		
		private String emailId;
		private boolean isValidId;
		
		
		public String getEmailId() {
			return emailId;
		}
	
		public void setEmailId(String emailId) {
			this.emailId = emailId;
		}
		
		public boolean isValidId() {
			return isValidId;
		}
		
		public void setValidId(boolean isValidId) {
			this.isValidId = isValidId;
		}
	}
	
	public class CustomDataSource  implements DataSource {
		private String name;
		private ByteArrayOutputStream buffer = null; 
		private String contentType = null;
		
		public CustomDataSource(InputStream inputStream,String name,String mimeType){
			this.name = name;
			buffer = new ByteArrayOutputStream();
			byte []readByte=new byte[1024];
			this.contentType = mimeType;
			try {
				int readChar = 0;
				while((readChar=inputStream.read(readByte,0,readByte.length))!=-1){
					buffer.write(readByte,0,readChar);
				}
				inputStream.close();
			} catch (IOException e) {
				log.info(e.getMessage());
			}
		}
		
		@Override
		public String getContentType() {
			return this.contentType;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(buffer.toByteArray());
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			return buffer;
		}
	}
	public static void mainOrig(String[] args) throws Exception {
		SendMailUtil emailUtil = new SendMailUtil();
		//String attachmentIds[]= {"This is Sample Content without base64"};
		String attachmentIds[]={"data:text/plain;base64,VGhpcyBpcyBzYW1wbGUgZW1haWw="};
		String base64Flags[]= {"true"};
		String attachmentNames[]={"Invoice.txt"};
		String mimeTypes[]= {"text/plain"};
		String mailSubject="This is sample Subject with base64";
		String mailBody="This is sample body content with <strong> base64 <strong> attachment <h1> this is <strong> h1 </strong> Image = <img src=\"cid:qrImage\" alt=\"qr code\"> displayed </h1> ";
		
		
		emailUtil.sendEmail("inkreta.tecnics@gmail.com", "Welcome@123", new String[] {"noreplyekaarya@gmail.com", ""}, new String[] {}, new String[] {}, mailSubject, mailBody, base64Flags,attachmentIds,attachmentNames,mimeTypes,null,null,null);
	}
	
	public static void main(String[] args) throws Exception {
		SendMailUtil emailUtil = new SendMailUtil();
		//String attachmentIds[]= {"This is Sample Content without base64"};
		/*String attachmentIds[]={"data:text/plain;base64,VGhpcyBpcyBzYW1wbGUgZW1haWw="};
		String base64Flags[]= {"true"};
		String attachmentNames[]={"Invoice.txt"};
		String mimeTypes[]= {"text/plain"}; */
		String attachmentIds[]=null;
		String base64Flags[]= null;
		String attachmentNames[]=null;
		String mimeTypes[]= null;
		String mailSubject="This is sample Subject with out attachments 1";
		String base64Images[]={Images.inkreta_email_logo};
		String base64ContentIdImagesFileNames[]= {"logo_inkreta.png" };
		String base64ContentIds[]= {"<inkretalogo>"};
		//String mailBody="This is sample body content with <strong> base64 <strong> attachment <h1> this is <strong> h1 </strong> Image = <img src=\"cid:qrImage\" alt=\"qr code\"> displayed </h1> ";
		
		String mailBody="<style type=\"text/css\"> " + 
				"    .emailid a {color: gold !important; text-decoration: underline!important;} </style>"
				+ "<table style=\"width:100%;text-align:left;border-collapse:collapse;background-color:gold;\"> "
				+"<tr style=\"background-color:#3C8DBC;color:white;font-family:verdana;font-size:60%;\"> "
				//+"<th align=bottom>A new invoice has been submitted for your review and approval "
				//style=\"font-family:verdana;font-size:60%;color:gold;\"
				+"<th width='60%' align=center>You have been  sent an invoice. You are receiving this invoice from"
				+ " <span style=\"text-decoration: none; color:gold;\" >  <span>info</span>@<span>inkreta</span>.<span>com</span> </span> because "+"TIPL"+" is using Inkreta to manage invoices."
				+"</th><th width='40%'><img align=right src=\"cid:inkretalogo\" alt=\"inkreta logo\"> </th>  </tr>"
				+" <tr style=\"height: 70%; background-color:#F5F5F4;color:black;\">"
				+"<td colspan=\"2\"><br><p> <span style=\"font-family:verdana;font-size:160%;color:blue;\" > From:   </span> <span style=\"font-family:verdana;font-size:160%;color:black;\" >   \"Sent on behalf of - Gopala Rao Vanam' - </span> </p>"
				+"<p> Dear Customer, </p> <p> Thanks for your business !!! </p> <p> The invoice "+""+" is attached with this email.</p>"
	+"<p> Here's an overview of the invoice for your reference. </p> <p> <strong> Invoice Overview: 1234</strong> </p>"
	+"<p> Invoice # : "+"invoice Nu m"+" </p> <p> Date : "+"invoice date"+" </p> <p> Amount: "+"45000"+" </p> <p> This submission contained the following files. "
+"<ul>  "+"attachment details"+"	</ul>	</p>"
+"<p> it was great working with you. Looking forward to working with you again. </p>" 
+"<p>** Please do not reply to this e-mail. For any queries on this submission please contact : " + " Fname "+ " " + "Lname" + " ( "+"email"+" ).** </p>"
+"<p> Warm Regards</p> 	<p>Team InKreta @4 </p> 	</td> 	</tr> 	</table>";
		
		
		emailUtil.sendEmail("info@inkreta.com", "Melcome@123", new String[] {"vgopalrao@yahoo.com", "gopalarao_vanam@tecnics.com", "gopalarao_vanam@elementblue.in"}, new String[] {}, new String[] {}, mailSubject, mailBody, base64Flags,attachmentIds,attachmentNames,mimeTypes,base64Images,base64ContentIds,base64ContentIdImagesFileNames);
	}
	
	public String test(String name, String[] names) {
		return "Hi, "+name+", " + names[0];
	}
	
	public static String convertAddressesAsString(Address[] addresses, String seperator) {
		String adressesAsString = "";
		System.out.println(">>>>>>>>>>>>convertAddressesAsString<<<<<<<<<<<<");
		if(addresses != null) {
			for(int i=0; i<addresses.length; i++) {
	        	InternetAddress ia = (InternetAddress) addresses[i];
	        	System.out.println(ia.getAddress());
	        	if(i < (addresses.length-1))
	        		adressesAsString += ia.getAddress()+seperator;
	        	else
	        		adressesAsString += ia.getAddress();
	        }
		}
		System.out.println(adressesAsString);
		return adressesAsString;
	}

}