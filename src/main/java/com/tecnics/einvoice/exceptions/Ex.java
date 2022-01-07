/*
 * Licensed Materials - Property of Tecnics Integration Technologies Pvt Ltd.
 *   (C) Copyright Tecnics Corp. 2021
 */

package com.tecnics.einvoice.exceptions;

import java.util.Locale;

/**
 * <p> This class has all exception codes of the application with messages</p>
 * @author shashi kumar
 * @since  1.0
 * @version  1.0
 */
public class Ex {

   

	// jwt exception codes
    public static Ex JWT_TOKEN_GENERATE;
    public static Ex JWT_TOKEN_VERIFY_FAIL;
    public static Ex JWT_TOKEN_EXPIRED;

    // rest controller exception codes
    public static Ex INVALID_API_REQUEST;
    public static Ex LOGIN_HANDLE_EXCEPTION;
    public static Ex LOGIN_USERID_PASSWORD_EMPTY;
    public static Ex TOKEN_REFRESH_HANDLE_EXCEPTION;
    public static Ex APP_NAVIGATION_HANDLE_EXCEPTION;
    public static Ex APP_INVITATION_HANDLE_EXCEPTION;
    public static Ex WEB_API_RES_CONVERT;

    // db exception codes
    public static Ex LOGIN_FAIL;
    public static Ex PARTNER_INACTIVE;
    public static Ex DS_FETCH_EXCEPTION;
    public static Ex HASH_ENCRYPT_FAIL;
    public static Ex HASH_DECRYPT_FAIL;
    public static Ex USER_SESSION_PERSIST_EXCEPTION;
 
    
    //
    public static Ex  PARTNER_PROFILE_ACTIVITY_UPDATE_FAILED ;
    public static Ex   GET_REGISTRATION_DETAILS_FAILED;
    
    // common Errors for multiple modules
    
    public static Ex UNAUTHORIZED;
    public static Ex UNAUTHORIZED_INVOICE_SUBMISSION;
    //invoice upload Exception codes
    public static Ex INVOICE_ROLE_EXCEPTION;
    public static Ex INVOICE_UPLOAD_EXCEPTION;
	public static Ex RECIEPIENT_NOT_MAPPED ;

    public static Ex ALFRESCO_FOLDER_CREATE;
	public static Ex  INV_LIST_FETCH_ERROR ;
	public static Ex  INV_DTL_FETCH_ERROR;
	public static Ex  DSHBRD_CUSTOMER_FETCH_ERROR;
	public static Ex  INV_STATUS_FETCH_ERROR;
	public static Ex  CUSTOM_ACTIONS_FETCH_ERROR;
	public static Ex   MULTIPLE_RECIPIENTS_MAPPED;

	public static Ex   NO_RECIPIENTS_MAPPED;
	public static Ex EXCEEDING_INV_LIMIT ;
	public static Ex RECIPIENT_CODE_NULL;
	public static Ex RECIPIENT_CODE_INVALID;
	public static Ex INV_DOC_NOT_FOUND;
	public static Ex INV_QRCODE_DECODE_ERROR;
	public static Ex INV_AUTO_POST_HTTP_ERROR;
	public static Ex CUSTOM_ACTION_EXECUTION_HTTP_ERROR;
	public static Ex INV_AUTO_POST_UNRECOGNIZED_RESPONSE;
	public static Ex CUSTOM_ACTION_EXECUTION_UNRECOGNIZED_RESPONSE;
	public static Ex OUTBOUNDCONNECTORS_ERROR;
	public static Ex INV_NOTES_SAVE_ERROR;
	public static Ex CUSTOM_ACTIONS_SAVE_ERROR;
	public static Ex PARTNER_CONTACT_DETAILS_SAVE_ERROR;
	public static Ex PARTNER_ROLES_SAVE_ERROR;
	public static Ex PARTNER_ROLES_FETCH_ERROR;
	public static Ex INV_QUERY_SAVE_ERROR;
	
	public static Ex PARTNER_GSTIN_DETAILS_SAVE_ERROR;
	
	
    static {
    	UNAUTHORIZED = new Ex("UNAUTHORIZED","Access denied. You do not have permission to perform this action or access this resource , message {0}"); 
    	UNAUTHORIZED_INVOICE_SUBMISSION= new Ex("UNAUTHORIZED_INVOICE_SUBMISSION","Access denied. You do not have permission to perform this action or submit invoice , message {0}"); 
    	OUTBOUNDCONNECTORS_ERROR = new Ex("OUTBOUNDCONNECTORS_ERROR","Unable to save the connector information for the tag , message {0}"); 
    	INV_NOTES_SAVE_ERROR = new Ex("INVOICE COMMENTS SAVE ERROR","Unable to save invoice comments for document ref id : {0} ");
    	INV_QUERY_SAVE_ERROR = new Ex("INVOICE QUERY SAVE ERROR","Unable to save invoice query for document ref id : {0} ");
    	CUSTOM_ACTIONS_SAVE_ERROR = new Ex("CUSTOM ACTIONS SAVE ERROR","Unable to save Custom Actions for partnerid : {0} ");
    	PARTNER_CONTACT_DETAILS_SAVE_ERROR = new Ex("PARTNER CONTACT DETAILS SAVE ERROR","Unable to save Contact Details for partnerid : {0} ");
    	PARTNER_ROLES_SAVE_ERROR = new Ex("PARTNER_ROLES_SAVE_ERROR","Unable to save Partner Roles for partnerid : {0} ");
    	PARTNER_GSTIN_DETAILS_SAVE_ERROR = new Ex("PARTNER_GSTIN_DETAILS_SAVE_ERROR","Unable to save Partner GSTIN Details for partnerid : {0} ");
    	INV_DOC_NOT_FOUND = new Ex("INVOICE DOCUMENT NOT FOUND","Unable to fetch the invoice document for document ref id : {0} ");
    	INV_QRCODE_DECODE_ERROR = new Ex("INV_QRCODE_DECODE_ERROR","Unable to decode or parse the QR Code from pdf for the document ref id : {0} ");
    	INV_AUTO_POST_HTTP_ERROR = new Ex("INV_AUTO_POST_HTTP_ERROR","Unable to reach the server for posting the invoice for the document ref id : {0} ");
    	CUSTOM_ACTION_EXECUTION_HTTP_ERROR = new Ex("CUSTOM_ACTION_EXECUTION_HTTP_ERROR","Unable to reach the server while executing the custom action for the document ref id : {0} {1}");
    	INV_AUTO_POST_UNRECOGNIZED_RESPONSE =  new Ex("INV_AUTO_POST_UNRECOGNIZED_RESPONSE","Unparsble response has been received from the service while posting the invoice for the document ref id : {0} ");
    	CUSTOM_ACTION_EXECUTION_UNRECOGNIZED_RESPONSE = new Ex("CUSTOM_ACTION_EXECUTION_UNRECOGNIZED_RESPONSE","Unparsble response has been received from the service while executing the custom action for the document ref id : {0} ");
    	INVALID_API_REQUEST = new Ex("INVALID_API_REQUEST","An Invalid API Request , message {0}");
        JWT_TOKEN_GENERATE = new Ex("JWT_TOKEN_GENERATE","An invalid user details for JWT token creation. user name {0}");
        JWT_TOKEN_VERIFY_FAIL = new Ex("JWT_TOKEN_VERIFY_FAIL","Unable to verify JWT for user. user name {0}");
        JWT_TOKEN_EXPIRED = new Ex("JWT_TOKEN_EXPIRED","JWT Token expired. user name {0}");
        LOGIN_HANDLE_EXCEPTION = new Ex("LOGIN_HANDLE_EXCEPTION","Unable to complete user logon request , userId : {0} ");
        LOGIN_USERID_PASSWORD_EMPTY = new Ex("Empty Credentials", "Unable to log in. Empty credentials are not accepted");
        TOKEN_REFRESH_HANDLE_EXCEPTION = new Ex("TOKEN_REFRESH_HANDLE_EXCEPTION","Unable to refresh token");
        APP_NAVIGATION_HANDLE_EXCEPTION = new Ex("APP_NAVIGATION_HANDLE_EXCEPTION","Unable to get app navigation items");
        APP_INVITATION_HANDLE_EXCEPTION = new Ex("APP_INVITATION_HANDLE_EXCEPTION","Unable to invite partner with details");
        WEB_API_RES_CONVERT = new Ex("WEB_API_RES_CONVERT","Unable to convert api response : API Service Name {0}");
        LOGIN_FAIL = new Ex("LOGIN_FAIL","The user ID or password is not valid for the server. USER_ID : {0}");
        PARTNER_INACTIVE = new Ex("PARTNER_INACTIVE","Your company {0} is tagged as InActive. You can't sign in to Inkreta services ");
        DS_FETCH_EXCEPTION = new Ex("DS_FETCH_EXCEPTION","Unable to access data source connection from postgres DRIVER : {0} , USER : {1} ");
        HASH_ENCRYPT_FAIL = new Ex("HASH_ENCRYPT_FAIL","Unable to perform hashing encryption on text . Text {0}");
        HASH_DECRYPT_FAIL = new Ex("HASH_DECRYPT_FAIL","Unable to perform hashing decryption on text . Text {0}");
        USER_SESSION_PERSIST_EXCEPTION = new Ex("USER_SESSION_PERSIST_EXCEPTION","Unable to save session details in db");
        PARTNER_PROFILE_ACTIVITY_UPDATE_FAILED = new Ex("PARTNER_PROFILE_ACTIVITY_UPDATE_FAILED","Unable to  the updatepartner details");
        GET_REGISTRATION_DETAILS_FAILED= new Ex("GET_REGISTRATION_DETAILS_FAILED","Unable to fetch  partner  registration details");
        INVOICE_ROLE_EXCEPTION=new Ex("INVOICE_ROLE_EXCEPTION","You are not Authorized to upload an invoice");
        INVOICE_UPLOAD_EXCEPTION=new Ex("INVOICE_UPLOAD_EXCEPTION","invoice upload failed");
        ALFRESCO_FOLDER_CREATE=new Ex("ALFRESCO_FOLDER_CREATE","Error creating folder in Alfresco");
        RECIEPIENT_NOT_MAPPED=new Ex("RECIEPIENT_NOT_MAPPED","Recipient not mapped.Vendor is not eligible to upload invoice");
        INV_LIST_FETCH_ERROR=new Ex("INV_LIST_FETCH_ERROR","Error Fetching invoice List for the vendor");
        PARTNER_ROLES_FETCH_ERROR=new Ex("PARTNER_ROLES_FETCH_ERROR","Error while Fetching partner roles for the partnerid : {0}");
        INV_DTL_FETCH_ERROR= new Ex("INV_DTL_FETCH_ERROR","Error while fetching invoice Details");
        CUSTOM_ACTIONS_FETCH_ERROR= new Ex("CUSTOM_ACTIONS_FETCH_ERROR","Error while fetching Custom Action Details method : {0}");
        INV_STATUS_FETCH_ERROR= new Ex("INV_STATUS_FETCH_ERROR","Error while fetching invoice Status Details");
        MULTIPLE_RECIPIENTS_MAPPED = new  Ex("MULTIPLE_RECIPIENTS_MAPPED","Multiple recipients mapped for this partner.Please provide Recipient code, to pull invoices [pullInvoices/{recipientCode}]");
        NO_RECIPIENTS_MAPPED = new  Ex("NO_RECIPIENTS_MAPPED","No recipients mapped for this partner. Please provide Recipient code, to pull invoices [pullInvoices/{recipientCode}]");
        EXCEEDING_INV_LIMIT= new Ex("EXCEEDING_INV_LIMIT","Invoice Request size is exceeding allowed limit");
        RECIPIENT_CODE_NULL = new Ex("RECIPIENT_CODE_NULL","Recipient code is required to post invoice");
        RECIPIENT_CODE_INVALID = new Ex("RECIPIENT_CODE_INVALID","Recipient code is invalid");
        
        DSHBRD_CUSTOMER_FETCH_ERROR=new Ex("DSHBRD_CUSTOMER_FETCH_ERROR", "Error while fetching Dashboard Results : {0} ");

    }

    /**
     *
     * @param key represent exception code
     * @param keyMessage  represent exception message
     */
    public Ex(String key,String keyMessage) {
        this.key = key;
        this.keyMessage =  keyMessage;
    }

    /**
     *
     * @param originalMessage - exception message
     * @param params -
     * @return
     */
    public static String formatMessage(String originalMessage , Object ...params) {
        String message = null;
        try {
            if(params != null && originalMessage != null) {
                message = originalMessage;
                for( int  j = 0 ; j < params.length ; j++) {
                    message = message.replace("{"+j+"}", (String) params[j]);
                }
            }
        } catch (Exception e) {
            ;
        }
        return message;
    }


    private String key;
    private String keyMessage;

    /**
     *
     * @return exception code
     */
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     *
     * @return exception message
     */
    public String getKeyMessage() {
        return keyMessage;
    }

    public void setKeyMessage(String keyMessage) {
        this.keyMessage = keyMessage;
    }
}
