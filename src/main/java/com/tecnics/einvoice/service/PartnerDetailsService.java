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
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.AttachmentDetailsRepo;
import com.tecnics.einvoice.Repo.CompanyGstinDetailRepo;
import com.tecnics.einvoice.Repo.ContactDetailRepo;
import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.FolderObjectsRepo;
import com.tecnics.einvoice.Repo.InvoiceAttachmentDetailsRepo;
import com.tecnics.einvoice.Repo.PartnerContactDetailsRepo;
import com.tecnics.einvoice.Repo.PartnerDetailsRepo;
import com.tecnics.einvoice.Repo.PartnerDetailSignupRepo;
import com.tecnics.einvoice.Repo.PartnerGSTINDetailsRepo;
import com.tecnics.einvoice.Repo.UserAuthorizationKeyRepo;
import com.tecnics.einvoice.Repo.UserManagementRepo;
import com.tecnics.einvoice.Repo.UserRolesRepo;
import com.tecnics.einvoice.Repo.PartnerDetailsRepo.PartnersListResults;
import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.entity.AttachmentDetails;
import com.tecnics.einvoice.entity.CompanyGstinDetail;
import com.tecnics.einvoice.entity.PartnerGSTINDetails;
import com.tecnics.einvoice.entity.ContactDetail;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.FolderObjects;

import com.tecnics.einvoice.entity.OnboardingRegistrationDetails;
import com.tecnics.einvoice.entity.PartnerContactDetails;
import com.tecnics.einvoice.entity.PartnerDetails;
import com.tecnics.einvoice.entity.PartnerDetailSignup;
import com.tecnics.einvoice.entity.PasswordReset;
import com.tecnics.einvoice.entity.SignupRequestModel;
import com.tecnics.einvoice.entity.UserAuthorizationKey;
import com.tecnics.einvoice.entity.UserManagement;
import com.tecnics.einvoice.entity.UserRoles;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.PartnerProfileModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.SignupResponse;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.HashingUtil;

@Component
public class PartnerDetailsService extends BaseService {
	@Autowired
	private Environment env;
	

	@Autowired
	PartnerDetailsRepo partnerDetailRepo;
	@Autowired
	PartnerGSTINDetailsRepo partnerGSTINDetailsRepo;
	
	@Autowired
	PartnerContactDetailsRepo partnerContactDetailsRepo;

	@Autowired
	PartnerDetailSignupRepo signupRepo;
	
	@Autowired
	private FolderObjectsRepo folderObjectsRepo;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	UserManagementRepo userManagementRepo;

	@Autowired
	UserAuthorizationKeyRepo userAuthorizationKeyRepo;

	@Autowired
	UserRolesRepo userRoleRepo;

	@Autowired
	private HashingUtil hashingUtil;

	@Autowired
	ContactDetailRepo contactDetailRepo;

	@Autowired
	CompanyGstinDetailRepo companyGstinDetailRepo;
	
	
	@Autowired
	PasswordResetService passwordResetService;
	
	@Autowired
	OnboardingRegistrationDetailsService onboardingRegistrationDetailsService;
	
	@Autowired
	private AttachmentDetailsRepo attachmentDetailsRepo;
	

	@Autowired
	ErrorLogRepo errorLogRepo;

	public List<PartnerDetails> findAll() {
		return (List<PartnerDetails>) partnerDetailRepo.findAll();
	}
	
	public ResponseEntity<ResponseMessage> fetchAllPartners(UserLoginDetails userObj)
	{
		List<PartnersListResults> partnersListResults=null;
		try
		{
			partnersListResults=partnerDetailRepo.fetchPartnersList();
		}
		
		catch(Exception ex){			
			ErrorLog error = new ErrorLog();
			error.setComponentName("PartnerDetailsService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
	return ResponseEntity.ok().body(new ResponseMessage(new APIError("Fetch All Partners List Error", "Error while fetching Partners List :","fetchAllPartners" )));
			
		}
return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(partnersListResults));
	}

	/**
	 * to save partner details on onboarding
	 * 
	 * @param request
	 * @return
	 */
	public SignupResponse save(SignupRequestModel request) {
		SignupResponse response = null  ;
		String partnerId = "";
		try {
			String partnerType = request.getPartnerDetails().getPartnerType();
			
			
			partnerId = generatePartnerId(partnerType);
			System.out.println("partnerID"+partnerId);

			PartnerDetailSignup partnerDetail = request.getPartnerDetails();
			partnerDetail.setPartnerId(partnerId);
			PartnerDetailSignup objres = signupRepo.save(partnerDetail);

			ContactDetail contDetailObj = request.getContactDetails();
			contDetailObj.setPartnerId(partnerId);
			contactDetailRepo.save(contDetailObj);

			CompanyGstinDetail gstnDetails = request.getGstnDetails();
			gstnDetails.setPartnerId(partnerId);
			companyGstinDetailRepo.save(gstnDetails);

			// response= createUser(partnerId, request, partnerType);
			 
			 
			 return response;
		} catch (Exception e) {
			System.out.println(e );
			throw e;
		}

		
	}
	
	/**
	 * to save partner details on onboarding
	 * 
	 * @param request
	 * @return
	 */
	public ResponseEntity<ResponseMessage> submitSignUp(OnboardingRegistrationDetails request) {
		
		 System.out.println(" attachments size before saveSupportingAttachmentsInOS =" + request.getAttachmentDetails().size());
			
		
		SignupResponse response = null  ;
		String partnerId = "";
		try {
			
			String partnerType = request.getPartnertype();
			System.out.println("partnerType ="+partnerType);
			
			partnerId = generatePartnerId(partnerType);
			System.out.println("partnerID"+partnerId);
			
			try
			{
			if (validateUserAlias(request.getAdminusername())>0) {
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("Username already exists","Someone already has this username : " + request.getAdminusername(),"Someone already has this username : " + request.getAdminusername())));
			}
			}
			catch(Exception e)
			{
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("SIGN UP SYSTEM ERROR","SIGN UP ERROR , Please contact support team",e.getMessage())));
					
			}
			
			PartnerDetailSignup partnerDetail = populatePartnerDetails(request);
			partnerDetail.setPartnerId(partnerId);
			
			PartnerDetailSignup objres = signupRepo.save(partnerDetail);

			
			PartnerGSTINDetails gstnDetails = populatePartnerGSTINDetails(request);
			gstnDetails.setPartnerId(partnerId);
			partnerGSTINDetailsRepo.save(gstnDetails);
			 response= createUser(partnerId, request, partnerType);
			 PartnerContactDetails partnerContactDetails= populatePartnerContactDetails(partnerId,request);
			 partnerContactDetailsRepo.save(partnerContactDetails);
			 System.out.println("Calling saveSupporting attachments");
			 System.out.println(" attachments size before saveSupportingAttachmentsInOS =" + request.getAttachmentDetails().size());
			 saveSupportingAttachmentsInOS(request,partnerId,response.getUserId());
			 request.setOnboardingstatus("Onboarded");
			 onboardingRegistrationDetailsService.update(request);
			 
			 return ResponseEntity.ok().body(new ResponseMessage(response));
		} catch (Exception e) {
			System.out.println(e );
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("SIGN UP SYSTEM ERROR","SIGN UP ERROR , Please contact support team",e.getMessage())));
			
		}

		
	}
	
	
public void saveSupportingAttachmentsInOS(OnboardingRegistrationDetails details,String partnerId,String userName) {
		
		try {
			System.out.println("inside saveSupportingAttachmentsInOS");
			System.out.println(" attachments size=" + details.getAttachmentDetails().size());
			if (details.getAttachmentDetails() != null) {
				FolderObjects fo=osFolderCreateForOnboarding(partnerId,details.getPartnertype());
				
				System.out.println("saveInvoiceAttachmentsInOS getInvoiceAttachmentDetails is not null");
				List<AttachmentDetails> attachmentDetails = details.getAttachmentDetails();
				System.out.println("saveInvoiceAttachmentsInOS attachmentDetails count = " + attachmentDetails.size() );
				for (AttachmentDetails file : attachmentDetails) {	
					System.out.println(" File Details " );
					file.setDocId(UUID.randomUUID().toString());
					file.setDocCategory("company");
					System.out.println(" Doc Id = " + file.getDocId() );
					
					System.out.println(" Doc Name = " + file.getDoc_name() );
					System.out.println(" Doc Type = " + file.getDocType());
					System.out.println(" Doc size = " + file.getDoc_size());
					System.out.println(" Doc Mime type  = " + file.getMime_type() );
					file.setFolder_id(fo.getFolder_id());
					System.out.println(" Folde  Id = " + file.getFolder_id() );
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
					
					
					file.setRefId(partnerId);
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


public FolderObjects osFolderCreateForOnboarding(String partnerId,String partnerType) {
	Path directories =null;
	FolderObjects fo=null;
	try {
	
	 	String rootFolder=env.getProperty("os.folder.onboarding");
	 	String folderSeperator=env.getProperty("os.file.seperator");
	 
	 	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();        
		String dateToStr = dateFormat.format(date);
		System.out.println("Folder rootFolder is "+ rootFolder);
		System.out.println("Folder Date is "+ dateToStr);
	 
		Path path = Paths.get(rootFolder+folderSeperator+partnerType+folderSeperator+partnerId);
							       
       
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
	
	
	public PartnerDetailSignup populatePartnerDetails(OnboardingRegistrationDetails request)
	{
		String partnerId = null;
		PartnerDetailSignup partnerDetails=null;
		try {
		partnerDetails=new PartnerDetailSignup();
		String partnerType = request.getPartnertype();		
		
		partnerId = generatePartnerId(partnerType);
		System.out.println("partnerID"+partnerId);
		partnerDetails.setPartnerId(partnerId);
		partnerDetails.setAnnualturnover(request.getAnnualturnover());
		partnerDetails.setIndustry(request.getLineofbusiness());;
		partnerDetails.setCompanyName(request.getCompanyname());
		partnerDetails.setCountry("India");
		//partnerDetails.setEstablishmentYear(new Date(request.getRgdt()));
		partnerDetails.setIncorporationyear(request.getIncorporationyear());
		partnerDetails.setCompanysize(request.getCompanysize());
		partnerDetails.setCompanydetails(request.getCompanydetails());
		partnerDetails.setReportingCurrency(request.getReportingcurrency());
		partnerDetails.setWebSite(request.getCompanywebsite());
		partnerDetails.setSalesEnquiryEmailId(request.getEnquiryemailid());
		partnerDetails.setExposeToAllVendors(true);
		partnerDetails.setFirmType(request.getCtb());
		partnerDetails.setKeyProductCategories(request.getKeyproductcategories());
		partnerDetails.setNatureOfBusiness(request.getNatureofbusiness());
		partnerDetails.setNoOfInvoicesExpected(request.getAveragedailyinvoices());
		partnerDetails.setOfferedServices(request.getIndustriesserviced());
		partnerDetails.setPanNo(request.getPan());
		partnerDetails.setPartnerType(request.getPartnertype());
		partnerDetails.setPaymentTerms(request.getStandardpaymentterms());
		partnerDetails.setStatus("InActive");
		partnerDetails.setOnboardedOn(new Timestamp(System.currentTimeMillis()));
		
		
		//partnerDetails.setNoOfPortalUsersAllowed(request.get);
		
		}
		catch (Exception e) {
			System.out.println(e );
			throw e;
		}
		
		return partnerDetails;
				
	}
	
	public PartnerGSTINDetails populatePartnerGSTINDetails(OnboardingRegistrationDetails request)
	{
		PartnerGSTINDetails partnerGSTINDetails=null;
		
		try {
			partnerGSTINDetails=new PartnerGSTINDetails();
		
		System.out.println("insde populatePartnerGSTINDetails");
		
		partnerGSTINDetails.setBnm(request.getBlockname());
		partnerGSTINDetails.setBno(request.getBuildingnumber());
		partnerGSTINDetails.setCity(request.getCity());
		partnerGSTINDetails.setCtb(request.getCtb());
		partnerGSTINDetails.setCtj(request.getCtj());
		partnerGSTINDetails.setCtjcd(request.getCtjcd());
		partnerGSTINDetails.setCxdt(request.getCxdt());
		partnerGSTINDetails.setDst(request.getDistrict());
		partnerGSTINDetails.setDty(request.getDty());
		partnerGSTINDetails.setFilingfrequency(request.getFrequencytype());
		partnerGSTINDetails.setFlno(request.getFloornumber());
		partnerGSTINDetails.setGstin(request.getGstin());
		partnerGSTINDetails.setLg(request.getLg());
		partnerGSTINDetails.setLgnm(request.getCompanyname());
		partnerGSTINDetails.setLoc(request.getLocation());
		partnerGSTINDetails.setLstupdt(request.getLstupdt());
		partnerGSTINDetails.setLt(request.getLt());
		partnerGSTINDetails.setNba(request.getNatureofbusiness());
		partnerGSTINDetails.setPanno(request.getPan());	
		partnerGSTINDetails.setPncd(request.getPincode());
		partnerGSTINDetails.setRgdt(request.getRgdt());
		partnerGSTINDetails.setSt(request.getStreet());
		partnerGSTINDetails.setStcd(request.getStatecode());
		partnerGSTINDetails.setStj(request.getStj());
		partnerGSTINDetails.setStjcd(request.getStjcd());
		partnerGSTINDetails.setSts(request.getSts());
		partnerGSTINDetails.setTradename(request.getTradename());		
		
		
		}
		catch (Exception e) {
			System.out.println(e );
			throw e;
		}
		
		return partnerGSTINDetails;
				
	}

	/**
	 * createUser
	 * 
	 * @param partnerID
	 * @param obj
	 */
	public SignupResponse createUser(String partnerID, OnboardingRegistrationDetails obj, String partnerType) {
		String userid= getUserId();
		SignupResponse response = new SignupResponse();

		System.out.println("createUser");
		try {

			UserManagement user = new UserManagement();
			user.setPartnerId(partnerID);
			
			user.setUserId(userid);
			
			String username =obj.getAdminusername();			
			user.setFirstName(obj.getFirstname());
			user.setLastName(obj.getLastname());		
			
			user.setEmail(obj.getEmail());
			user.setPrimaryPhoneNumber(obj.getMobilenumber());
			
			user.setUser_alias(obj.getAdminusername());	
			user.setCreatedOn(new Timestamp(System.currentTimeMillis()));
			user = userManagementRepo.save(user);
			String password = obj.getAdminpassword();
			userAuthorisation(user,password);

			String userRole = UserRoleCreation(user, partnerType);
			
			response.setPartnerId(partnerID);
			response.setUserRole(userRole);
			response.setUserId(userid);
			response.setEMail(obj.getEmail());
			response.setAlias(obj.getAdminusername());
			response.setUserDisplayName(obj.getFirstname() + " " + obj.getLastname());
			
		} catch (DataIntegrityViolationException e) {
			String message = e.getMostSpecificCause().getMessage();
			System.err.println(message);
		}

		catch (Exception e) {
			System.err.println(e);
		}
		return response;

	}
	
	/**
	 * createUser
	 * 
	 * @param partnerID
	 * @param obj
	 */
	public PartnerContactDetails populatePartnerContactDetails(String partnerId,OnboardingRegistrationDetails obj)
	{
		PartnerContactDetails partnerContactDetails=null;
		try {
		partnerContactDetails=new PartnerContactDetails();
		partnerContactDetails.setEmail(obj.getEmail());
		partnerContactDetails.setFirstName(obj.getFirstname());
		partnerContactDetails.setLastName(obj.getLastname());
		partnerContactDetails.setPartnerId(partnerId);
		partnerContactDetails.setPrimaryPhoneNumber(obj.getMobilenumber());
		partnerContactDetails.setTypeOfContact("Primary");
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
		return partnerContactDetails;
	}

	private void userAuthorisation(UserManagement user, String password) {
		UserAuthorizationKey auth = new UserAuthorizationKey();
		auth.setUserManagement(user);
		auth.setAuthorizationKey(hashingUtil.encryptKey(password));
		userAuthorizationKeyRepo.save(auth);
	}

	
	
	private String UserRoleCreation(UserManagement user, String partnerType) {

		UserRoles role = new UserRoles();
		String userRole ="";

		role.setUserId(user.getUserId());
		if (partnerType.equalsIgnoreCase("vendor")) {
			role.setUserRole("vendor_manager");
			userRole = "vendor_manager";
			role.setRoleId(7);
		}

		else if (partnerType.equalsIgnoreCase("customer")) {
			role.setUserRole("customer_manager");
			userRole="customer_manager";
			role.setRoleId(4);
		}
		
		else if (partnerType.equalsIgnoreCase("businesspartner")) {
			role.setUserRole("businesspartner_manager");
			userRole="businesspartner_manager";
			role.setRoleId(10);
		}

		userRoleRepo.save(role);
		return userRole;
	}
	
	
	
	

	public List<PartnerDetails> findByPartnerId(String partnerId) {
		return partnerDetailRepo.findByPartnerId(partnerId);
	}

	public String generatePartnerId(String type) {
		String sql = "select einvoicing.generate_partner_id(?) ";
		return jdbcTemplate.queryForObject(sql, new Object[] { type }, String.class);
	}

	public String getUserId() {
		String sql = "select einvoicing.generate_user_id()";
		String count = jdbcTemplate.queryForObject(sql, new Object[] {}, String.class);
		return count;
	}

	/**
	 * validategstn - returns true if the gstn as parameter is already exists in the
	 * contacts
	 * 
	 * @param email
	 * @return
	 */
	public int validategstn(String gstn) {
		try {
			String theQuery = "select count(0) from einvoicing.partner_gstin_details pgd where pgd.gstin =?";
			return jdbcTemplate.queryForObject(theQuery, new Object[] { gstn }, Integer.class);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
		
	}
	
	/**
	 * validatePrimaryPhone - returns true if the primaryPhone as parameter is
	 * already exists in the contacts
	 * 
	 * @param email
	 * @return
	 */
	public int validateUserAlias(String userAlias) throws Exception {
		try {
			String theQuery = "select count(0) from einvoicing.user_management um where um.user_alias =?";
			return jdbcTemplate.queryForObject(theQuery, new Object[] { userAlias }, Integer.class);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
		
		}

	/**
	 * validatePrimaryPhone - returns true if the primaryPhone as parameter is
	 * already exists in the contacts
	 * 
	 * @param email
	 * @return
	 */
	public int validatePrimaryPhone(String primaryPhone) {
		try {
			String theQuery = "select count(*) from einvoicing.contact_details cgd where cgd.primary_phone =?";
			return jdbcTemplate.queryForObject(theQuery, new Object[] { primaryPhone }, Integer.class);
		} catch (Exception e) {
			System.err.println(e);
		}
		return 0;
	}

	/**
	 * validateEmail - returns true if the email as parameter is already exists in
	 * the contacts
	 * 
	 * @param email
	 * @return
	 */
	public int validateEmail(String email) {
		try {
			String theQuery = "select count(*) from einvoicing.contact_details cgd where cgd.email =?";
			return jdbcTemplate.queryForObject(theQuery, new Object[] { email }, Integer.class);
		} catch (Exception e) {
			System.err.println(e);
		}
		return 0;
	}
	
	
	
	public ResponseEntity<ResponseMessage> setPartnerStatus(UserLoginDetails uld, String partner_id,String status)
	{
		int response;
		System.out.println("Inside setPartnerStatus of PartnerDetailsService");
			
		try {
			System.out.println("status set for the Partner ID = " + partner_id);
			if(status.equals("Active") || status.equals("InActive"))				
				response=partnerDetailRepo.setPartnerStatus(status,partner_id);
			
		}
		
		catch(Exception ex){
			ex.printStackTrace();
			ErrorLog error = new ErrorLog();
			error.setComponentName("PartnerDetailsService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			//error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(""));
		
	}
	
	public ResponseEntity<ResponseMessage> getPartnerDetails(UserLoginDetails uld, String partner_id)
	{
		PartnerProfileModel response=null;
		PartnerDetails partnerDetails=null;
		List<PartnerContactDetails> partnerContactDetails=null;
		List<PartnerGSTINDetails> partnerGSTINDetails=null;
		List<AttachmentDetails> attachmentDetails;
		System.out.println("Inside getPartnerDetails of PartnerDetailsService");
			
		try {
			System.out.println("Partner ID to be fetched = " + partner_id);
			
			partnerDetails=findPartnerDetailsByPartnerId(partner_id).get(0);
			System.out.println("company name  = " + partnerDetails.getCompanyName());
			partnerContactDetails=findContactDetailsByPartnerId(partner_id);
			partnerGSTINDetails=findGSTINDetailsByPartnerId(partner_id);
			attachmentDetails=attachmentDetailsRepo.findByRefIdAndDocCategory(partner_id,"company");
			response=new PartnerProfileModel();
			response.setPartnerDetails(partnerDetails);
			response.setPartnerContactDetails(partnerContactDetails);
			response.setPartnerGSTINDetails(partnerGSTINDetails);	
			response.setAttachmentDetails(attachmentDetails);
		}
		
		catch(Exception ex){
			ex.printStackTrace();
			ErrorLog error = new ErrorLog();
			error.setComponentName("PartnerDetailsService");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			//error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
		
	}
	
	public List<PartnerContactDetails> findContactDetailsByPartnerId(String partnerId) {
		return (List<PartnerContactDetails>) partnerContactDetailsRepo.findByPartnerId(partnerId);
	}
	
public List<PartnerGSTINDetails> findGSTINDetailsByPartnerId(String partnerId) {
		
		List<PartnerGSTINDetails>  partnerGSTINDetails=partnerGSTINDetailsRepo.findByPartnerId(partnerId);
		return partnerGSTINDetails;
	}

public List<PartnerDetails> findPartnerDetailsByPartnerId(String partnerId) {
	return (List<PartnerDetails>) partnerDetailRepo.findByPartnerId(partnerId);
}


public ResponseEntity<ResponseMessage> getDocument(UserLoginDetails uld, String partner_id,String docId) {
	AttachmentDetails attachmentDetails = null;
	try
	{
		attachmentDetails=attachmentDetailsRepo.findByDocIdAndDocCategory(docId,"company");

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
		error.setComponentName("PartnerDetailsService");
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
