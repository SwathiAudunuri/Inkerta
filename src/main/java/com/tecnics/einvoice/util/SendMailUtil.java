package com.tecnics.einvoice.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.ArrayList;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.bind.DatatypeConverter;


import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import com.tecnics.einvoice.config.ConfigFactory;






public class SendMailUtil {
	
	
	private static final Logger log = LoggerFactory.getLogger(SendMailUtil.class);
	private boolean isDebugEnabled = true;
	private boolean isPerformanceEnabled = true;
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
	
	public String sendEmail(String fromMail , String password, String toMails[] , String ccMails[] ,String bccMails[] , String mailSubject, String mailBody , String base64Flags[], String attachmentIds[], String attachmentNames[], String mimeTypes[])  throws Exception {
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
				mimeMessage.setContent(fetchMultiPartMessage(base64Flags,attachmentIds,attachmentNames,mimeTypes, mailBody));
			
			if(isDebugEnabled)
				log.info(" sending email .......");
			
			try {
				Transport.send(mimeMessage);
				if(isDebugEnabled)
					log.info(" sending email completed .......");
				
				if(unableToSendEmails.length() > 0)
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
	
	private Multipart fetchMultiPartMessage(String base64Flags[],String attachmentIds[],String attachmentNames[],String mimeTypes[], String mailBody) throws Exception {
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
		} catch (Exception e) {
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
				if(isDebugEnabled)
					log.info("isBase64 Content"+isBase64);
				
					MimeBodyPart mimeBodyPart = new MimeBodyPart();
					String mimeType =null;
					byte  attachmentBase64[]=null;
					if(isBase64) {
						System.out.println("Attachment content is base64");
					attachmentBase64  = DatatypeConverter.parseBase64Binary(attachmentIds[j]);
					log.info("parsed Base binary="+new String(attachmentBase64));
					mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(attachmentBase64));
					log.info("mimeType="+mimeType);
					if(mimeType==null)
					{
						mimeType=getMimeType(new String(attachmentIds[j]));
						if(mimeType!=null)
						{
						    String delims="[,]";
						    String[] tokens = new String(attachmentIds[j]).split(delims);
					        System.out.println("Tokens [0]"+ tokens[0]);
					        System.out.println("Tokens [1]"+ tokens[1]);
					        attachmentBase64= DatatypeConverter.parseBase64Binary(tokens[1]);
					        System.out.println("Decoded string =   " +new String(attachmentBase64));
						}
					}
					log.info("mimeType from getMimeType ="+mimeType);
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
		    log.info("inside getMimeType  " + base64Data );
		
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
	public static void main(String[] args) throws Exception {
		SendMailUtil emailUtil = new SendMailUtil();
		//String attachmentIds[]= {"This is Sample Content without base64"};
		String attachmentIds[]={"data:text/plain;base64,VGhpcyBpcyBzYW1wbGUgZW1haWw="};
		String base64Flags[]= {"true"};
		String attachmentNames[]={"Invoice.txt"};
		String mimeTypes[]= {"text/plain"};
		String mailSubject="This is sample Subject with base64";
		String mailBody="This is sample body content with base64 attachment";
		
		
		emailUtil.sendEmail("inkreta.tecnics@gmail.com", "Welcome@123", new String[] {"noreplyekaarya@gmail.com", ""}, new String[] {}, new String[] {}, mailSubject, mailBody, base64Flags,attachmentIds,attachmentNames,mimeTypes);
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