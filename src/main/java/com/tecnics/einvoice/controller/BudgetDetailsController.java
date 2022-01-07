
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

import com.tecnics.einvoice.Repo.BudgetDetailsRepo;

import com.tecnics.einvoice.entity.BudgetDetails;

import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.BudgetDetailsService;

import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.SOAPWebServiceClient;
import com.tecnics.einvoice.util.XMLParser;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BudgetDetailsController  extends BaseController {
	
	@Autowired
	BudgetDetailsService budgetDetailsService;
	

	@RequestMapping("/budgetdetails/verifyBudgetDetailsController")  
    public String verify()  
    {  
        return "The BudgetDetailsController is up and running";  
    } 
	
	
	/***
	 * 
	 * @param id
	 * @param token
	 * @return
	 */
	@GetMapping("/budgetdetails/getbyid/{id}")
	public ResponseEntity<ResponseMessage> getBudgetDetails(@PathVariable Integer id,
			@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside BudgetDetailsController actiondetails");
			
		
			System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
			ResponseEntity<ResponseMessage> response=budgetDetailsService.fetchById(id);
			
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Budget Details getBudgetDetails Error", "Budget Details getBudgetDetails Error :" + e.getMessage(),"getBudgetDetails" )));
			
		}
	}
	

	/***
	 * 
	 * @param category
	 * @param token
	 * @return
	 */
	@GetMapping("/budgetdetails/fetchbycatgory/{budgetCategory}")
	public ResponseEntity<ResponseMessage> fetchByCategory(@PathVariable String budgetCategory,
			@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside BudgetDetailsController ");
			
		
			System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
			ResponseEntity<ResponseMessage> response=budgetDetailsService.fetchByBudgetCategory(budgetCategory, userObj);
			
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Budget Details fetchByCategory Error", "Budget Details fetchByCategory Error :" + e.getMessage(),"fetchByCategory" )));
			
		}
	}
	
	/***
	 * 
	 * @param category
	 * @param token
	 * @return
	 */
	@GetMapping("/budgetdetails/getbudgetdetails")
	public ResponseEntity<ResponseMessage> getBudgetDetails(@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside BudgetDetailsController ");
			
		
			System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
			ResponseEntity<ResponseMessage> response=budgetDetailsService.getBudgetDetails(userObj);
			
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Budget Details fetchByCategory Error", "Budget Details fetchByCategory Error :" + e.getMessage(),"fetchByCategory" )));
			
		}
	}
	
	
	/***
	 * 
	 * @param id
	 * @param token
	 * @return
	 */
	@GetMapping("/budgetdetails/deletebyid/{id}")
	public ResponseEntity<ResponseMessage> deleteBudgetDetailsById(@PathVariable Integer id,
			@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside BudgetDetailsController actiondetails");
			
		
			System.out.println("Partner ID from Obj = " + userObj.getPartnerId());
			ResponseEntity<ResponseMessage> response=budgetDetailsService.deleteBudgetDetailsById(userObj,id);
			
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError("Budget Details getBudgetDetails Error", "Budget Details getBudgetDetails Error :" + e.getMessage(),"getBudgetDetails" )));
			
		}
	}
	
	/***
	 * 
	 * @param BudgetDetails
	 * @param token 
	
	 * @return
	 */
	@PostMapping("/budgetdetails/save")

	public ResponseEntity<ResponseMessage> save(@RequestBody BudgetDetails budgetDetails,@RequestHeader("authorization") String token) 
		{
		ResponseEntity<ResponseMessage> response=null;
		UserLoginDetails userObj=getUserObjFromToken(token);
		System.out.println("Inside BudgetDetailsController save");		
		
		String partnerId= userObj.getPartnerId();		
			
			//check if  action exists with the same action name in the system
					
				try {
					response=budgetDetailsService.fetchByBudgetName(budgetDetails.getBudgetName(),userObj);
					
					if (response!=null) 
						return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseMessage(new APIError("Duplicate BudgetDetails", "The record exists with the same Budget name :",budgetDetails.getBudgetName() )));
					response =budgetDetailsService.save(budgetDetails, userObj);
					return response;
					
				} catch (Exception e) {
					e.printStackTrace();
					return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseMessage(new APIError("budgetdetails save Error", e.getMessage())));
				}
					
			
		}	
		


}

