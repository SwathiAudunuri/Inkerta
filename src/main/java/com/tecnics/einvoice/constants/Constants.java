package com.tecnics.einvoice.constants;

public class Constants {

    public static String HAS_ERROR = "hasError";
    public static String ERROR = "error";
    public static String ERROR_CODE = "errorCode";
    public static String ERROR_MESSAGE = "errorMessage";
    public static String ERROR_TRACE = "errorTrace";
    public static String API_RESULTS = "results";
    public static String API_ROWS = "rows";
    public static String API_TIMESTAMP = "timestamp";


    //JWT Header Details
    public static String JWT_CLAIM_NAME = "name";
    public static String JWT_TOKEN_ISSUER = "inkreta systems pvt ltd";
    public static int JWT_TOKEN_EXPIRY_MINUTES =60;
    public static int JWT_REFRESH_TOKEN_EXPIRY_MINUTES = 60;



    // user params
    public static String USER_ID = "userId";
    public static String PASSWORD = "password";
    public static String USER_JWT_TOKEN = "securityToken";
    public static String USER_JWT_REFRESH_TOKEN = "refreshToken";
    public static String PARTNER_ID = "partnerId";
    public static String PARTNER_NAME = "partnerName";
    public static String ROLES = "roles";
    public static String EMAIL = "email";

    // response results



    //partner details
    public static String COMPANY_NAME  = "companyName";
    public static String CONTACT_PERSON_NAME  = "contactPersonName";
    public static String CONTACT_MOBILE_NUMBER  = "contactMobileNumber";
    public static String CONTACT_EMAIL  = "contactEmail";
    public static String FIRM_TYPE  = "firmType";
    public static String DESCRIPTION  = "firmType";
    public static String SENT_BY  = "sentBy";
    public static String REQ_RAISED_BY  = "requestRaisedBy";
    
    //Partner Activity Statuses
    public static final String APPROVE="Approve";
    public static  final String SUBMITED="Submit";
    public static final String DISCARD="Discard";
    public static final String NEEDMOREDETAILS="NeedMoreDetails";
	private static final String DRAFT = "Draft";

    //Partner Inv Status for Alfresco folder creation
    public static final String PARTNER_FOLDER_CREATION = "Folder Created";
	public static final String PROCESS_INITITATED = "Initiate";
	//To be removed after User mangagment 
	public static final String TESTUSER = "010001";

	//Delivary Mode - Reciepient Mapping
	public static final String EMAILER = "email";
	public static final String FTP = "ftp";
	public static final String WEBSERVICE = "webservice";

	//Default Password
	public static final String DEFAULT_PASSWORD = "Welcome@123";
	
	//invoices
	
	public static final String ROLE="vendor";
	public static final float INV_UPLOAD_LIMIT = 10000000;
}
