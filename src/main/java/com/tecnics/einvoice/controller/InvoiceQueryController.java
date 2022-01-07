package com.tecnics.einvoice.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.InvoiceQuery;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.entity.InvoiceSupplierBuyerInfo;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.InvoiceMetaDataModel;
import com.tecnics.einvoice.model.QueryNode;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.InvoiceQueryService;
import com.tecnics.einvoice.service.InvoiceQueryTreeService;
import com.tecnics.einvoice.serviceImpl.InvoiceDetailsServiceImpl;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.InvoiceQueryUtil;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tecnics.einvoice.Repo.InvoiceDocumentDetailRepo;
import com.tecnics.einvoice.dao.InvoiceQueryDAO;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class InvoiceQueryController extends BaseController{

	private InvoiceQueryService invoiceQueryService;
	
	@Autowired
	InvoiceDetailsServiceImpl invoiceDetailsServiceImpl;
	
	
	@Autowired
	InvoiceDocumentDetailRepo invoicedetailRepo;
	
	@Autowired
	InvoiceQueryDAO invoiceQueryDAO;

	@Autowired
	public InvoiceQueryController(InvoiceQueryService theInvoiceQueryService) {
		invoiceQueryService = theInvoiceQueryService;
	}
	
	@RequestMapping("/verifyInvoiceQueryController")  
    public String verify()  
    {  
        return "The InvoiceQueryController is up and running";  
    } 

	
    
    
	@GetMapping("/invoicequeries/findall")
	public ResponseEntity<ResponseMessage> findAll(@RequestHeader("authorization") String token) {
		System.out.println("inside findAll of invoiceQueries");
		System.out.println("Toke String is : " + token);
		System.out.println("User Name is : " + getUserName(token));
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + userObj);
		System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
		System.out.println("User Name from Obj = " + userObj.getUserId());
		System.out.println("Partner Type from Obj = " + userObj.getPartnerType());
		java.util.List <String>roles=userObj.getRoles();
		System.out.println("Roles from Obj = " );
		for (String role : roles) { 
           
            System.out.println(role);
        }
		List<InvoiceQuery> response =  invoiceQueryService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	
	@GetMapping("/invoicequeries/findbydocument_ref_id/{documentRefId}")
	public ResponseEntity<ResponseMessage> findByDocument_ref_id(@PathVariable String documentRefId,@RequestHeader("authorization") String token) {
		System.out.println("inside findByDocument_ref_id of invoiceQueries");
		System.out.println("User Name is : " + getUserName(token));
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + userObj);
		System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
		System.out.println("User Name from Obj = " + userObj.getUserId());
		System.out.println("Partner Type from Obj = " + userObj.getPartnerType());
		InvoiceRequestModel irm=null;
		List<InvoiceQuery> response =null;
		
		try
		{
			irm=invoiceDetailsServiceImpl.getInvoiceDetails(documentRefId);
			InvoiceMetaDataModel imdm=irm.getInvoiceDetails();
			System.out.println("Customer Partner ID from invoiceDetails=" + imdm.getCustomer_partner_id());
			System.out.println("Vendor Partner ID from invoiceDetails=" + imdm.getVendor_partner_id());
			
			if (!userObj.getPartnerId().equals(imdm.getCustomer_partner_id()) && !userObj.getPartnerId().equals(imdm.getVendor_partner_id()))	
					{
					return ResponseEntity.ok().body(
					new ResponseMessage(new APIError(Ex.UNAUTHORIZED.getKey(),							
							Ex.formatMessage(Ex.UNAUTHORIZED.getKeyMessage(), " User is not authorized to perform the desired action for the documentRefId : "+ documentRefId))));
					}
			System.out.println("Serving Queries for documentRefID" + documentRefId);
			response = invoiceQueryService.findByDocumentRefId(documentRefId);
			
			
		}
	 catch (Exception e) {
		System.err.println(e);
		log.logErrorMessage(e.getMessage(), e);
		return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
				Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
	}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	@GetMapping("/invoicequeries/findbyid/{id}")
	public ResponseEntity<ResponseMessage> findById(@PathVariable int id) {
		List<InvoiceQuery> response = invoiceQueryService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}


	//can be deleted
	@GetMapping("/invoicequeries/gettreebydocument_ref_id_old/{documentRefId}")
	public ResponseEntity<ResponseMessage> getTreeByDocument_ref_id_old(@PathVariable String documentRefId,@RequestHeader("authorization") String token) {
		System.out.println("inside findByDocument_ref_id of invoiceQueries ***");
		System.out.println("User Name is : " + getUserName(token));
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + userObj);
		System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
		System.out.println("User Name from Obj = " + userObj.getUserId());
		System.out.println("Partner Type from Obj = " + userObj.getPartnerType());
		InvoiceRequestModel irm=null;
		List<com.tecnics.einvoice.model.InvoiceQuery> response =null;
		List<com.tecnics.einvoice.model.InvoiceQuery> hierarchicalQueries = null;

		
		
		try
		{
			irm=invoiceDetailsServiceImpl.getInvoiceDetails(documentRefId);
			InvoiceMetaDataModel imdm=irm.getInvoiceDetails();
			System.out.println("Customer Partner ID from invoiceDetails=" + imdm.getCustomer_partner_id());
			System.out.println("Vendor Partner ID from invoiceDetails=" + imdm.getVendor_partner_id());
			
			if (!userObj.getPartnerId().equals(imdm.getCustomer_partner_id()) && !userObj.getPartnerId().equals(imdm.getVendor_partner_id()))	
					{
					return ResponseEntity.ok().body(
					new ResponseMessage(new APIError(Ex.UNAUTHORIZED.getKey(),							
							Ex.formatMessage(Ex.UNAUTHORIZED.getKeyMessage(), " User is not authorized to perform the desired action for the documentRefId : "+ documentRefId))));
					}
			System.out.println("Serving Queries for documentRefID" + documentRefId);
	
			response=invoiceQueryDAO.findInvoiceQueriesByDocumentRefId(documentRefId);
			// hierachical Queries start
			InvoiceQueryUtil qryUtil=new InvoiceQueryUtil();
			System.out.println("inside hierachical queries start logic ");
		hierarchicalQueries=qryUtil.getQueriesTree(response);
			System.out.println("hierachical queries size =  " + hierarchicalQueries.size());
			
			//end of hierachical Querie
			
		
			
			
			
		}
	 catch (Exception e) {
		System.err.println(e);
		log.logErrorMessage(e.getMessage(), e);
		return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
				Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
	}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(hierarchicalQueries));
	}
	
	

	//can be deleted
	
	@GetMapping("/invoicequeries/gettreebydocument_ref_id2/{documentRefId}")
	public ResponseEntity<ResponseMessage> getTreeByDocument_ref_id2(@PathVariable String documentRefId,@RequestHeader("authorization") String token) {
		System.out.println("inside findByDocument_ref_id of invoiceQueries ***");
		System.out.println("User Name is : " + getUserName(token));

		List<Map<String,QueryNode>> response=null;
		String jsonString ="";
		
		try
		{
			com.tecnics.einvoice.service.QueryNodeService qns=new com.tecnics.einvoice.service.QueryNodeService();
			response=qns.createQueries();
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			jsonString = objectMapper.writeValueAsString(response);
			System.out.println("jsonString = " + jsonString);
			
			//end of hierachical Querie
			
			
		}
	 catch (Exception e) {
		System.err.println(e.getMessage());
		//e.printStackTrace();
		log.logErrorMessage(e.getMessage(), e);
		return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
				Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
	}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	
	
	@GetMapping("/invoicequeries/gettreebydocument_ref_id/{documentRefId}")
	public ResponseEntity<ResponseMessage> getTreeByDocument_ref_id(@PathVariable String documentRefId,@RequestHeader("authorization") String token) {
		System.out.println("inside findByDocument_ref_id3 of invoiceQueries ***");
		System.out.println("User Name is : " + getUserName(token));
		UserLoginDetails userObj=getUserObjFromToken(token);
		//System.out.println("user Obj = " + userObj);
		//System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
		//System.out.println("User Name from Obj = " + userObj.getUserId());
		//System.out.println("Partner Type from Obj = " + userObj.getPartnerType());
		List<com.tecnics.einvoice.model.InvoiceQueryTreeEntity> response=null;
		String jsonString ="";
		InvoiceRequestModel irm=null;
		List<com.tecnics.einvoice.model.InvoiceQueryTreeRow> hierarchicalQueries = null;
		Map headerMap = new HashMap<>();
		
		try
		{
			irm=invoiceDetailsServiceImpl.getInvoiceDetails(documentRefId);
			InvoiceMetaDataModel imdm=irm.getInvoiceDetails();
			InvoiceSupplierBuyerInfo supplierBuyerInfo = irm.getInvoiceSupplierBuyerDetails();
		
			 headerMap.put("invoicenum", imdm.getInvoicenum());
			 headerMap.put("Supplier_legal_name", supplierBuyerInfo.getSupplier_legal_name());
			//System.out.println("Customer Partner ID from invoiceDetails=" + imdm.getCustomer_partner_id());
			//System.out.println("Vendor Partner ID from invoiceDetails=" + imdm.getVendor_partner_id());
			
			if (!userObj.getPartnerId().equals(imdm.getCustomer_partner_id()) && !userObj.getPartnerId().equals(imdm.getVendor_partner_id()))	
					{
					return ResponseEntity.ok().body(
					new ResponseMessage(new APIError(Ex.UNAUTHORIZED.getKey(),							
							Ex.formatMessage(Ex.UNAUTHORIZED.getKeyMessage(), " User is not authorized to perform the desired action for the documentRefId : "+ documentRefId))));
					}
			
			com.tecnics.einvoice.service.InvoiceQueryTreeService iqts=new com.tecnics.einvoice.service.InvoiceQueryTreeService();
			hierarchicalQueries=invoiceQueryDAO.findInvoiceQueriesByDocumentRefIdForTree(documentRefId);
			response=new InvoiceQueryTreeService().getTreeQueries(hierarchicalQueries);
			
			
			
			
		}
	 catch (Exception e) {
		System.err.println(e.getMessage());
		//e.printStackTrace();
		log.logErrorMessage(e.getMessage(), e);
		return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
				Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
	}
		//new code with invoice header
		
		
		
		 Map rootMap = new HashMap<>();
		 
		 rootMap.put("queryresults", response);
		 rootMap.put("invoiceheader", headerMap);
	        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(rootMap));
	       // return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
		// end of invoie header
		
		//return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	/**
	 * @param todoTask
	 * @param token
	 * @return TodoResponse
	 */
	@PostMapping("/invoicequeries/save")
	public ResponseEntity<ResponseMessage> save(@RequestBody InvoiceQuery invoiceQuery,@RequestHeader("authorization") String token) {
		
		try
		{
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside InvoiceQueryController save");
			String qryRefId=null;
			InvoiceRequestModel irm=null;
		
		
		if(invoiceQuery.getParentQueryRefId()!=null && invoiceQuery.getParentQueryRefId().trim()!="")
		{
			InvoiceQueryUtil qryUtil=new InvoiceQueryUtil();
			System.out.println("inside if 1 = ");
			qryRefId=qryUtil.generateQueryRefId(invoiceQueryService,invoiceQuery.getDocumentRefId(),invoiceQuery.getParentQueryRefId());
			invoiceQuery.setQueryRefId(qryRefId);
		}
		else
		{
			long queriesByDocumentRefId=invoiceQueryService.findCountByDocumentRefId(invoiceQuery.getDocumentRefId());
		
			System.out.println("findCountByDocumentRefId count = " + queriesByDocumentRefId);
			invoiceQuery.setQueryRefId(String.valueOf(queriesByDocumentRefId+1));
			
				
		}
		System.out.println("Paret Query Ref Id = " + invoiceQuery.getParentQueryRefId());
		
		List<InvoiceQuery> iq1=invoiceQueryService.findByParentQueryRefId(invoiceQuery.getParentQueryRefId());
		if(iq1!=null)
		{
		System.out.println("findByparentQueryRefIf executed size="+iq1.size());
		System.out.println("Inside invoiceQueryController Save" + invoiceQuery.toString());
		}
		TransactionResponse response = invoiceQueryService.save(invoiceQuery,userObj);
		
		irm=invoiceDetailsServiceImpl.getInvoiceDetails(invoiceQuery.getDocumentRefId());
		InvoiceMetaDataModel imdm=irm.getInvoiceDetails();
	
		System.out.println("Setting  Query status");
		if(userObj.getPartnerId().equals(imdm.getVendor_partner_id()))
		{
		int cnt=invoicedetailRepo.setInvoice_statusForInvoiceDocumentDetail("Pending", invoiceQuery.getDocumentRefId());
		System.out.println("Query  Doc Status is set to Pending");
		}
		else if(userObj.getPartnerId().equals(imdm.getCustomer_partner_id()))
		{
			int cnt=invoicedetailRepo.setInvoice_statusForInvoiceDocumentDetail("Queried", invoiceQuery.getDocumentRefId());	
			System.out.println("Query Doc Status is set to Queried");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_QUERY_SAVE_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_QUERY_SAVE_ERROR.getKeyMessage(),"actiondetails()"), getStackTrace(e))));
		}
		
	}
	
	@GetMapping("/invoicequeries/getdocument/{docId}")
	public ResponseEntity<ResponseMessage> getDocument(@PathVariable String docId,@RequestHeader("authorization") String token) {
		
			UserLoginDetails userObj=getUserObjFromToken(token);
		
		return invoiceQueryService.getDocument(userObj,docId);
	}

}

