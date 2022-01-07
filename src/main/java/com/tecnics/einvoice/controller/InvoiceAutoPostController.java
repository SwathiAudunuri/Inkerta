
package com.tecnics.einvoice.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.Repo.InvoiceDocumentDetailRepo;
import com.tecnics.einvoice.Repo.InvoiceStatusTrackerRepo;
import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.entity.InvoiceAttachmentDetail;
import com.tecnics.einvoice.entity.InvoiceAudit;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.entity.InvoiceStatusTracker;
import com.tecnics.einvoice.entity.OutboundConnectors;
import com.tecnics.einvoice.entity.OutboundConnectorsAutopost;
import com.tecnics.einvoice.entity.OutboundConnectorsGSTIN;
import com.tecnics.einvoice.entity.PartnerDetails;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.InvoiceAutoPostResponse;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.InvoiceDocumentDetailService;
import com.tecnics.einvoice.service.InvoiceStatusTrackerService;
import com.tecnics.einvoice.service.OutboundConnectorsService;
import com.tecnics.einvoice.service.PartnerDetailsService;
import com.tecnics.einvoice.serviceImpl.InvoiceDetailsServiceImpl;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.SOAPWebServiceClient;
import com.tecnics.einvoice.util.XMLParser;

import org.w3c.dom.Document;  
import org.w3c.dom.Element;  
import org.w3c.dom.Node;  
import org.w3c.dom.NodeList;  
import org.xml.sax.InputSource;  
import org.xml.sax.SAXException;

import org.json.JSONObject;
import org.json.XML;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class InvoiceAutoPostController extends BaseController {
	
	@Autowired
	OutboundConnectorsService outboundConnectorsService;
	
	
	@Autowired
	InvoiceDocumentDetailService detailService;
	
	@Autowired
	InvoiceDocumentDetailRepo invoicedetailRepo;
	
	@Autowired
	InvoiceStatusTrackerRepo invoiceStatusTrackerRepo;
	
	
	@RequestMapping("/verifyInvoiceAutoPostController")  
    public String verify()  
    {  
        return "The InvoiceAutoPostController is up and running";  
    } 

	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	
	 * @return
	 */

	
	@PostMapping("/invoiceautopost/post/{documentRefId}")
	public ResponseEntity<ResponseMessage> invoicePost(@PathVariable String documentRefId, @RequestHeader("authorization") String token) {
		System.out.println("DocumentRefID of the invoice to be posted ="+ documentRefId);
		try {
			ResponseEntity<ResponseMessage> response=null;
			String userName=getUserName(token);
			UserLoginDetails uld=getUserObjFromToken(token);
			String partnerId = uld.getPartnerId();
			List<OutboundConnectors> ocon=outboundConnectorsService.findByPartnerId(partnerId);
			InvoiceRequestModel irm = detailService.getInvoiceDetails(documentRefId);
			String customerGstin=irm.getInvoiceSupplierBuyerDetails().getBilling_gstin();
			System.out.println("Customer GSTIN = " + customerGstin);
			OutboundConnectorsGSTIN ocGstin=outboundConnectorsService.findByGstin(customerGstin);
			Integer connectorId=ocGstin.getConnectorId();
			System.out.println("Connector ID =  " + connectorId);
			OutboundConnectorsAutopost outboundConnectorsAutopost=outboundConnectorsService.findAutopostConnectorByConnectorId(connectorId);
			if(outboundConnectorsAutopost.getConnectorType().equals("WebService-SOAP"))
				response=processSOAPAction(irm,outboundConnectorsAutopost, uld);
					
						
			
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
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}
	

	public ResponseEntity<ResponseMessage> processSOAPAction(InvoiceRequestModel irm,OutboundConnectorsAutopost outboundConnectorsAutopost, UserLoginDetails uld )
	{
		String response=null;
		InvoiceAutoPostResponse iapr=new InvoiceAutoPostResponse();
		try
		{
		String inputRequest=outboundConnectorsAutopost.getInputRequest();
		String service_username=outboundConnectorsAutopost.getUserName();
		String service_password=outboundConnectorsAutopost.getUserPassword();
		String url=outboundConnectorsAutopost.getUrl();
		String endpointAction=outboundConnectorsAutopost.getOperationAction();
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
		
		input_variables=processInputVariables(input_variables,irm);
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
					Ex.formatMessage(re.getMessage(), irm.getInvoiceDetails().getDocument_ref_id() ))));
		}
		catch(Exception e)
		{
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(e.getMessage(),
					Ex.formatMessage(e.getMessage(), irm.getInvoiceDetails().getDocument_ref_id() ))));
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

			
			invoiceStatusTracker.setDocumentRefId(irm.getInvoiceDetails().getDocument_ref_id());
			
			invoiceStatusTracker.setActionDate(new Timestamp(System.currentTimeMillis()));
			invoiceStatusTracker.setActionType("Invoice Auto Post");
			invoiceStatusTracker.setSource("Customer");
		
		response="<?xml version=\"1.0\" ?>\n" + response;
	//	System.out.println("Response after XML = " + response);
		
		//XML Parsing
		String resp_doc_number_onsuccessful="";
		String resp_message="";
		XMLParser parser = new XMLParser(); 
		Document document = parser.parseXmlFile(response);
		NodeList nodeLst_resp_doc_number_onsuccessful = document.getElementsByTagName(outboundConnectorsAutopost.getRespDocNumberOnsuccessful());
		
		
		if(nodeLst_resp_doc_number_onsuccessful.getLength()>0 && nodeLst_resp_doc_number_onsuccessful.item(0).getTextContent()!="" )
		{
			resp_doc_number_onsuccessful=nodeLst_resp_doc_number_onsuccessful.item(0).getTextContent();
			System.out.println("Doc Number =: " + resp_doc_number_onsuccessful);
			iapr.setResp_doc_number("The Invoice has been posted successfully with " + outboundConnectorsAutopost.getRespDocNumberOnsuccessful() + " : " + resp_doc_number_onsuccessful);
			invoiceStatusTracker.setAction("Posted for Payment");
			invoiceStatusTracker.setActionComments("The Invoice has been posted successfully with " + outboundConnectorsAutopost.getRespDocNumberOnsuccessful() + " : " + resp_doc_number_onsuccessful);
			
		}
		NodeList nodeLst_resp_message = document.getElementsByTagName(outboundConnectorsAutopost.getRespMessage());
		if(nodeLst_resp_message.getLength()>0)
		{		
		resp_message = nodeLst_resp_message.item(0).getTextContent();
		iapr.setResp_message(resp_message);
		invoiceStatusTracker.setAction("Exception");
		invoiceStatusTracker.setActionComments(iapr.getResp_message());
		System.out.println("Message: " + resp_message);
		}
		
		if(invoiceStatusTracker.getAction()!=null && invoiceStatusTracker.getAction()!="" )
		{
		invoiceStatusTrackerRepo.save(invoiceStatusTracker);
		int cnt=invoicedetailRepo.setInvoice_statusForInvoiceDocumentDetail(invoiceStatusTracker.getAction(), invoiceStatusTracker.getDocumentRefId());
		
		}
		else
		{
			iapr.setFailure_message("There is no valid data returned from the service");
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_AUTO_POST_UNRECOGNIZED_RESPONSE.getKey(),
					Ex.formatMessage(Ex.INV_AUTO_POST_UNRECOGNIZED_RESPONSE.getKeyMessage(), irm.getInvoiceDetails().getDocument_ref_id() ))));
		}
			
			

			} catch (Exception e) {
				System.out.println("Error while posting Invoice" + e.getMessage());
						
				e.printStackTrace();
				log.logErrorMessage(e.getMessage(), e);
				return ResponseEntity.ok().body(new ResponseMessage(new APIError(e.getMessage(),
						Ex.formatMessage(e.getMessage(), irm.getInvoiceDetails().getDocument_ref_id() ))));
						
				
			}
		
		//end of Status Tracker update
		
	/*	JSONObject xmlJSONObj = XML.toJSONObject(response);
        String jsonPrettyPrintString = xmlJSONObj.toString(4);
        System.out.println("converted xml to json string = " + jsonPrettyPrintString); */
		
		//End XML Parsing
		
		
		
		
		

		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(iapr));

	} catch(RuntimeException re)
		{
		
		return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_AUTO_POST_HTTP_ERROR.getKey(),
				Ex.formatMessage(Ex.INV_AUTO_POST_HTTP_ERROR.getKeyMessage()), getStackTrace(re))));
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
