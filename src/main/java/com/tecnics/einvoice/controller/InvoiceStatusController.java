package com.tecnics.einvoice.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.entity.InvoiceSellerPaymentInformation;
import com.tecnics.einvoice.entity.InvoiceStatus;
import com.tecnics.einvoice.entity.InvoiceSupplierBuyerInfo;
import com.tecnics.einvoice.entity.PartnerDetails;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.InvoiceMetaDataModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;

import com.tecnics.einvoice.service.InvoiceStatusService;
import com.tecnics.einvoice.service.PartnerDetailsService;
import com.tecnics.einvoice.serviceImpl.InvoiceDetailsServiceImpl;
import com.tecnics.einvoice.util.APIError;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class InvoiceStatusController extends BaseController {
	
	@Autowired
	InvoiceStatusService invoiceStatusService;
	
	
	@Autowired
	InvoiceDetailsServiceImpl invoiceDetailsServiceImpl;
	
	
	@RequestMapping("/verifyInvoiceStatusController")  
    public String verify()  
    {  
        return "The InvoiceStatusController is up and running";  
    } 

	
	@GetMapping("/invoicestatus/findall")
	public ResponseEntity<ResponseMessage> findAll(@RequestHeader("authorization") String token) {
		System.out.println("inside findAll of invoiceStatus");
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
		List<InvoiceStatus> response =  invoiceStatusService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
	}
	
	@GetMapping("/invoicestatus/getstatusbydocument_ref_id/{documentRefId}")
	public ResponseEntity<ResponseMessage> getStatusByPartnerTypeAndStatusKey(@PathVariable String documentRefId,@RequestHeader("authorization") String token) {
		System.out.println("inside getStatusByPartnerTypeAndStatusKey of invoiceStatusController ***");
		

		System.out.println("User Name is : " + getUserName(token));
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("user Obj = " + userObj);
		System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
		System.out.println("User Name from Obj = " + userObj.getUserId());
		System.out.println("Partner Type from Obj = " + userObj.getPartnerType());
		
		List<InvoiceStatus> response =null;
		InvoiceRequestModel irm=null;
		
		HashMap<String, String> invoiceHeader = new HashMap<>();
		 ArrayList<Object> responseList = new ArrayList <>();
		 
		 
		 Map<String,Object> responseValues = new HashMap<>();
		 SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy"); 
		
		try
		{
			irm=invoiceDetailsServiceImpl.getInvoiceDetails(documentRefId);
			InvoiceMetaDataModel imdm=irm.getInvoiceDetails();
			InvoiceSupplierBuyerInfo isbi=irm.getInvoiceSupplierBuyerDetails();
			Date invoiceDate=imdm.getInvoicedate();
			InvoiceSellerPaymentInformation ispi=irm.getInvoiceSellerPaymentDetails();
			
			
			
			invoiceHeader.put("invoicenum", imdm.getInvoicenum());
			invoiceHeader.put("total_invoice_value",imdm.getTotal_invoice_value().toString());
			invoiceHeader.put("supplier_legal_name", isbi.getSupplier_legal_name());
			invoiceHeader.put("invoicedate", formatter.format(imdm.getInvoicedate()));
			invoiceHeader.put("invoiceduedate","");
			if(invoiceDate!=null && (ispi.getCreditdays()!=null && ispi.getCreditdays().intValue()!=0))
			{
			Calendar cal = Calendar.getInstance();
		        cal.setTime(invoiceDate);
		        cal.add(Calendar.DATE, ispi.getCreditdays().intValue()); //minus number would decrement the days
		        
			invoiceHeader.put("invoiceduedate", formatter.format(cal.getTime()));
			}
			
			System.out.println("Customer Partner ID from invoiceDetails=" + imdm.getCustomer_partner_id());
			System.out.println("Vendor Partner ID from invoiceDetails=" + imdm.getVendor_partner_id());
			 String partnerType = userObj.getPartnerType();
			    String statusKey = imdm.getInvoice_status();
			    System.out.println("Partner Type = " + partnerType);
			    System.out.println("Status Key  = " + statusKey);
		 response =  invoiceStatusService.findByPartnerTypeAndStatusKey(partnerType, statusKey);
		 
		
		 //responseList.add(invoiceHeader);
		// responseList.add(response);
		 
		 responseValues.put("invoiceheader", invoiceHeader);
		 responseValues.put("statuskeys", response);
		 
			
			
			
		}
	 catch (Exception e) {
		System.err.println(e);
		e.printStackTrace();
		log.logErrorMessage(e.getMessage(), e);
		return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
				Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
	}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(responseValues));
	}
	

	
}
