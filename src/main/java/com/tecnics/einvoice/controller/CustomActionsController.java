package com.tecnics.einvoice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.Repo.InvoiceDocumentDetailRepo;
import com.tecnics.einvoice.Repo.InvoiceStatusTrackerRepo;
import com.tecnics.einvoice.Repo.CustomActionsRepo.CustomActionsResults;
import com.tecnics.einvoice.entity.CustomActions;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.entity.InvoiceStatusTracker;
import com.tecnics.einvoice.entity.PartnerRoles;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.CustomActionResponse;
import com.tecnics.einvoice.model.CustomActionsModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.CustomActionsService;
import com.tecnics.einvoice.service.InvoiceDocumentDetailService;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.SOAPWebServiceClient;
import com.tecnics.einvoice.util.XMLParser;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CustomActionsController  extends BaseController {
	
	@Autowired
	CustomActionsService customActionsService;
	
	
	@Autowired
	InvoiceDocumentDetailService detailService;
	
	@Autowired
	InvoiceStatusTrackerRepo invoiceStatusTrackerRepo;
	
	@Autowired
	InvoiceDocumentDetailRepo invoicedetailRepo;
	
	
	@RequestMapping("/customactions/verifyCustomActionsController")  
    public String verify()  
    {  
        return "The CustomActionsController is up and running";  
    } 
	
	
	/***
	 * 
	 * @param actionId
	 * @param token
	 * @return
	 */
	@GetMapping("/customactions/actiondetails/{actionId}")
	public ResponseEntity<ResponseMessage> getActionDetails(@PathVariable Integer actionId,
			@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside customActionsController actiondetails");
			
		
			System.out.println("Partner ID from Obj = " + userObj.getPartnerId());	
			CustomActionsModel customActionsModel=customActionsService.findByPartnerIdAndActionId(userObj.getPartnerId(),actionId);
		
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(customActionsModel));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKeyMessage(),"actiondetails()"), getStackTrace(e))));
		}
	}
	
	/***
	 * 
	 * @param actionId
	 * @param token
	 * @return
	 */
	@GetMapping("/customactions/getuniquerolesandinvoicestatuses")
	public ResponseEntity<ResponseMessage> getUniqueRolesAndInvoiceStatuses(@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside customActionsController actiondetails");			
		
			System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
			return customActionsService.fetchPartnerRolesAndInvoicetatuses(userObj);
			

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKeyMessage(),"getuniquerolesandinvoicestatuses()"), getStackTrace(e))));
		}
	}
	
	
	/***
	 * 
	 * @param actionId
	 * @param token
	 * @return
	 */
	@GetMapping("/customactions/fetchforinvoice/{documentRefId}")
	public ResponseEntity<ResponseMessage> getActionsByInvoiceStatusAndPartnerRoles(@PathVariable String documentRefId,@RequestHeader("authorization") String token) {
		UserLoginDetails userObj=null;
			try {
				userObj=getUserObjFromToken(token);				
			
			} catch (Exception e) {
				System.err.println(e);
				log.logErrorMessage(e.getMessage(), e);
				return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKey(),
						Ex.formatMessage(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKeyMessage(),"getuniquerolesandinvoicestatuses()"), getStackTrace(e))));
			}
			
			try {
				InvoiceRequestModel response = detailService.getInvoiceDetails(documentRefId);
				String invoice_status=response.getInvoiceDetails().getInvoice_status();
				List<String> partnerRoles=userObj.getPartnerRoles();
				
				List<CustomActionsResults> customActionsResults=customActionsService.getActionsByInvoiceStatusAndPartnerRoles(userObj, invoice_status, partnerRoles);
				
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(customActionsResults));
			

			} catch (Exception e) {
				System.err.println(e);
				log.logErrorMessage(e.getMessage(), e);
				return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
						Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
			}
			
		
			

		
	}
	
	
	/***
	 * 

	 * @param token
	 * @return
	 */
	@GetMapping("/customactions/getactions")
	public ResponseEntity<ResponseMessage> getActions(@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside customActionsController actiondetails");
			
		
			System.out.println("Partner ID from Obj = " + userObj.getPartnerId());	
			List<CustomActionsResults> customActionsResults=customActionsService.fetchCustomActionsByPartnerId(userObj.getPartnerId());
		
			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(customActionsResults));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKeyMessage(),"getactions()"), getStackTrace(e))));
		}
	}
	
	/***
	 * 
	 * @param customActionsModel
	 * @param token 
	
	 * @return
	 */
	@PostMapping("/customactions/createaction")

	public ResponseEntity<ResponseMessage> createAction(@RequestBody CustomActionsModel customActionsModel,@RequestHeader("authorization") String token) 
		{
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("Inside customActionsController save");		
		
		String partnerId= userObj.getPartnerId();		
			
			//check if  action exists with the same action name in the system
		CustomActions existingCustomActions=null;			
				try {
					existingCustomActions=customActionsService.findByPartnerIdAndActionName(partnerId,customActionsModel.getCustomActions().getActionName());
					
					if (existingCustomActions!=null) 
						return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseMessage(new APIError("Duplicate Action", "The record exists with the same Action name :",customActionsModel.getCustomActions().getActionName() )));
					ResponseEntity<ResponseMessage> response =customActionsService.save(customActionsModel, userObj);
					return response;
					
				} catch (Exception e) {
					e.printStackTrace();
					return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseMessage(new APIError("onboarding saveDetails Error", e.getMessage())));
				}
					
			
		}	
		
	
	/***
	 * 
	 * @param actionId
	 * @param token
	 * @return
	 */
	@GetMapping("/customactions/executeaction/{actionId}/{documentRefId}")
	public ResponseEntity<ResponseMessage> executeAction(@PathVariable Integer actionId,@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		ResponseEntity<ResponseMessage>  response=null;
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside customActionsController executeAction");
			
			InvoiceRequestModel invoiceDetails = detailService.getInvoiceDetails(documentRefId);
			System.out.println("Processing customaction for Invoice # :" + invoiceDetails.getInvoiceDetails().getInvoicenum());
		
			System.out.println("Partner ID from Obj = " + userObj.getPartnerId());	
			CustomActionsModel customActionsModel=customActionsService.findByPartnerIdAndActionId(userObj.getPartnerId(),actionId);
		
			// return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(customActionsModel));
			
			if(customActionsModel!=null && customActionsModel.getCustomActions().getActionConnectorType().equals("WebService-SOAP"))
				response=processSOAPAction(invoiceDetails,customActionsModel.getCustomActions(), userObj);
					
						
			
			if(response==null)
			{
				System.out.println("There is no document found for the document ref id " + documentRefId);
				return ResponseEntity.ok().body(new ResponseMessage(
						new APIError(Ex.INV_DOC_NOT_FOUND.getKey(), Ex.formatMessage(Ex.INV_DOC_NOT_FOUND.getKeyMessage(),documentRefId))));
			}
			System.out.println("Response returning invoicePost");
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.CUSTOM_ACTIONS_FETCH_ERROR.getKeyMessage(),"actiondetails()"), getStackTrace(e))));
		}
	}	
	
	
	public ResponseEntity<ResponseMessage> processSOAPAction(InvoiceRequestModel invoiceDetails,CustomActions customAction, UserLoginDetails uld )
	{
		String response=null;
		CustomActionResponse customActionResponse=new CustomActionResponse();
		try
		{
		String inputRequest=customAction.getInputRequest();
		String service_username=customAction.getUserName();
		String service_password=customAction.getUserPassword();
		String url=customAction.getUrl();
		String endpointAction=customAction.getActionOperation();
		//System.out.println("Auto post input request info 1 = " + outboundConnectorsAutopost.getInputRequest());
		
		SOAPWebServiceClient soapWebServiceClientObject = new SOAPWebServiceClient();
		
		//parsing inputRequest
		
		HashMap<String, String> input_variables = new HashMap<>();
		//System.out.println("Finding $ variables");
		Pattern p = Pattern.compile("\\[\\$(.*?)\\$\\]");
		Matcher m = p.matcher(inputRequest);
		while(m.find())
		{
			//System.out.println(m.group(1)); //is your string. do what you want
			input_variables.put(m.group(1), m.group(1));
		}
		
		input_variables=processInputVariables(input_variables,invoiceDetails);
		 for (Map.Entry<String, String> e : input_variables.entrySet())
		 {
			           // System.out.println("Key: " + e.getKey()  + " Value: " + e.getValue());
			            inputRequest=inputRequest.replaceAll("\\[\\$"+e.getKey()+"\\$\\]", e.getValue());
		 }
		
		//end of parsing
		 //System.out.println("input Request after vriable replcamement " + inputRequest);
		try {
		response=soapWebServiceClientObject.postSOAPXML(inputRequest,url,endpointAction,service_username,service_password);
		}
		catch(RuntimeException re)
		{
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(re.getMessage(),
					Ex.formatMessage(re.getMessage(), invoiceDetails.getInvoiceDetails().getDocument_ref_id() ))));
		}
		catch(Exception e)
		{
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(e.getMessage(),
					Ex.formatMessage(e.getMessage(), invoiceDetails.getInvoiceDetails().getDocument_ref_id() ))));
		}
		if(response==null)
		{			
			System.out.println("There is no response received from the soapWebClient");
			return ResponseEntity.ok().body(new ResponseMessage(
					new APIError(Ex.INV_DOC_NOT_FOUND.getKey(), Ex.formatMessage(Ex.INV_DOC_NOT_FOUND.getKeyMessage()))));
		}
		
		try {
			

			System.out.println("Creating invoicestatustracker object ");
			InvoiceStatusTracker invoiceStatusTracker=new InvoiceStatusTracker();
			invoiceStatusTracker.setActionBy(uld.getUserId());

			
			invoiceStatusTracker.setDocumentRefId(invoiceDetails.getInvoiceDetails().getDocument_ref_id());
			
			invoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
			invoiceStatusTracker.setActionType("Custom Action");
			invoiceStatusTracker.setSource("Customer");
		
		response="<?xml version=\"1.0\" ?>\n" + response;
		customActionResponse.setResponse_body(response);
	//	System.out.println("Response after XML = " + response);
		
		//XML Parsing
		String resp_doc_number_onsuccessful="";
		String resp_message="";
		XMLParser parser = new XMLParser(); 
		
		Document document = parser.parseXmlFile(response);
		System.out.println("Prsing completed"); 
		try
		{
		if(customAction.getTransferStylesheet()!=null && !customAction.getTransferStylesheet().equals(""))
		{
			String htmlContent=XMLParser.transform(response, customAction.getTransferStylesheet());
			customActionResponse.setTransformed_body(htmlContent);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Custom Action XSLT transformation error for DocRefid =: " + invoiceDetails.getInvoiceDetails().getDocument_ref_id() + " :  " + e.getMessage());
		}
		
		NodeList nodeLst_resp_doc_number_onsuccessful = document.getElementsByTagName(customAction.getRespAttributeOnsuccessful());
		
		
		if(nodeLst_resp_doc_number_onsuccessful.getLength()>0 && nodeLst_resp_doc_number_onsuccessful.item(0).getTextContent()!="" )
		{
			resp_doc_number_onsuccessful=nodeLst_resp_doc_number_onsuccessful.item(0).getTextContent();
			System.out.println("Doc Number =: " + resp_doc_number_onsuccessful);
			customActionResponse.setResp_doc_number( customAction.getRespAttributeOnsuccessful() + " : " + resp_doc_number_onsuccessful);
			invoiceStatusTracker.setAction(customAction.getActionName());
			invoiceStatusTracker.setActionComments("Custom Action Results : " + customAction.getRespAttributeOnsuccessful() + " : " + resp_doc_number_onsuccessful);
			
		}
		NodeList nodeLst_resp_message = document.getElementsByTagName(customAction.getRespMessage());
		if(nodeLst_resp_message.getLength()>0)
		{		
		resp_message = nodeLst_resp_message.item(0).getTextContent();
		customActionResponse.setResp_message(resp_message);
		invoiceStatusTracker.setAction("Exception");
		invoiceStatusTracker.setActionComments(customActionResponse.getResp_message());
		System.out.println("Message: " + resp_message);
		}
		
		if(invoiceStatusTracker.getAction()!=null && invoiceStatusTracker.getAction()!="" )
		{
		invoiceStatusTrackerRepo.save(invoiceStatusTracker);
		//int cnt=invoicedetailRepo.setInvoice_statusForInvoiceDocumentDetail(invoiceStatusTracker.getAction(), invoiceStatusTracker.getDocumentRefId());
		
		}
		else
		{
			customActionResponse.setFailure_message("There is no valid data returned from the service");
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.CUSTOM_ACTION_EXECUTION_UNRECOGNIZED_RESPONSE.getKey(),
					Ex.formatMessage(Ex.CUSTOM_ACTION_EXECUTION_UNRECOGNIZED_RESPONSE.getKeyMessage(), invoiceDetails.getInvoiceDetails().getDocument_ref_id() ))));
		}
			
			

			} catch (Exception e) {
				System.out.println("Error while posting Invoice" + e.getMessage());
						
				e.printStackTrace();
				log.logErrorMessage(e.getMessage(), e);
				return ResponseEntity.ok().body(new ResponseMessage(new APIError(e.getMessage(),
						Ex.formatMessage(e.getMessage(), invoiceDetails.getInvoiceDetails().getDocument_ref_id() ))));
						
				
			}
		
		
		
		

		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(customActionResponse));

	} catch(RuntimeException re)
		{
		
		return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.CUSTOM_ACTION_EXECUTION_HTTP_ERROR.getKey(),
				Ex.formatMessage(Ex.CUSTOM_ACTION_EXECUTION_HTTP_ERROR.getKeyMessage()), getStackTrace(re))));
		}
		catch (Exception e) {
		System.err.println(e);
		e.printStackTrace();
		log.logErrorMessage(e.getMessage(), e);
		return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
				Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
	}

	}
	
	public HashMap<String, String> processInputVariables(HashMap<String, String> input_variables, InvoiceRequestModel irm)
	{
		 for (Map.Entry<String, String> e : input_variables.entrySet())
		 {
			 
	           // System.out.println("Key: " + e.getKey()  + " Value: " + e.getValue());
	            
	            switch (e.getKey()) {
	            case "total_invoice_value":
	            	input_variables.put(e.getKey(), String.valueOf(irm.getInvoiceDetails().getTotal_invoice_value()));
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
	

}
