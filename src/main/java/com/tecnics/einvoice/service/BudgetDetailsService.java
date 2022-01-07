
package com.tecnics.einvoice.service;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.BudgetDetailsRepo;

import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.BudgetDetails;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.CustomActionsModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.util.APIError;

@Component
public class BudgetDetailsService extends BaseService {
	


		@Autowired
		BudgetDetailsRepo budgetDetailsRepo;

		
		@Autowired
		ErrorLogRepo errorLogRepo;
		
		public ResponseEntity<ResponseMessage> fetchById(Integer id) {
			Optional<BudgetDetails> optionalBudgetDetails=budgetDetailsRepo.findById(id);
			BudgetDetails budgetDetails=null;
			
			if (optionalBudgetDetails!=null && optionalBudgetDetails.isPresent())
			{
				budgetDetails=optionalBudgetDetails.get();						
			}
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(budgetDetails));
			//return ResponseEntity.ok().body(new ResponseMessage(new APIError("Roles and Invoice Status Fetch Error", "Error while fetch Partner Roles and Invoice Statuses :","fetchPartnerRolesAndInvoicetatuses" )));
			
			
		}
		
		public ResponseEntity<ResponseMessage> deleteBudgetDetailsById(UserLoginDetails userObj,Integer id) {
			try
			{
			int recordsDeleted=budgetDetailsRepo.deleteByIdAndPartnerId(id,userObj.getPartnerId());
			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Budget Details Deleted Successfully"));
			//return ResponseEntity.ok().body(new ResponseMessage(new APIError("Roles and Invoice Status Fetch Error", "Error while fetch Partner Roles and Invoice Statuses :","fetchPartnerRolesAndInvoicetatuses" )));
			}
catch(Exception ex){
				
				ErrorLog error = new ErrorLog();
				error.setComponentName("BudgetDetailService");
				error.setError(ex.getMessage());
				error.setErrorMessage(getStackTrace(ex));
				error.setModule("deleteBudgetDetailsById");
				error.setSource("Java");
				error.setUserId(userObj.getUserId());
				errorLogRepo.save(error);
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("Budget Details deleteByIdAndPartnerId Error", "Budget Details deleteByIdAndPartnerId Error :" + ex.getMessage(),"deleteByIdAndPartnerId" )));
			
			}
			
			
		}
		
		public ResponseEntity<ResponseMessage> fetchByBudgetName(String budgetName,UserLoginDetails userObj) {
			BudgetDetails budgetDetails=null;			
			try
			{
			budgetDetails=budgetDetailsRepo.findByPartnerIdAndBudgetName(userObj.getPartnerId(),budgetName);
			if(budgetDetails==null)
				return null;
			}
			catch(Exception ex){
				
				ErrorLog error = new ErrorLog();
				error.setComponentName("BudgetDetailService");
				error.setError(ex.getMessage());
				error.setErrorMessage(getStackTrace(ex));
				error.setModule("fetchByBudgetName");
				error.setSource("Java");
				error.setUserId(userObj.getUserId());
				errorLogRepo.save(error);
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("Budget Details fetchByBudgetName Error", "Budget Details fetchByBudgetName Error :" + ex.getMessage(),"fetchByBudgetName" )));
			
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(budgetDetails));
		}
		
		
		
		public ResponseEntity<ResponseMessage> fetchByBudgetCategory(String budgetCategory, UserLoginDetails userObj) {
			
			List<BudgetDetails> budgetDetails=null;
			try
			{
			budgetDetails=budgetDetailsRepo.findByPartnerIdAndBudgetCategory(userObj.getPartnerId(), budgetCategory);
			}
			catch(Exception ex){
				
				ErrorLog error = new ErrorLog();
				error.setComponentName("BudgetDetailService");
				error.setError(ex.getMessage());
				error.setErrorMessage(getStackTrace(ex));
				error.setModule("fetchByBudgetCategory");
				error.setSource("Java");
				error.setUserId(userObj.getUserId());
				errorLogRepo.save(error);
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("Budget Details fetchByBudgetCategory Error", "Budget Details fetchByBudgetCategory Error :" + ex.getMessage(),"fetchByBudgetCategory" )));
			
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(budgetDetails));			
			
		}
		

	public ResponseEntity<ResponseMessage> getBudgetDetails(UserLoginDetails userObj) {
			
			List<BudgetDetails> budgetDetails=null;
			try
			{
			budgetDetails=budgetDetailsRepo.findByPartnerId(userObj.getPartnerId());
			}
			catch(Exception ex){
				
				ErrorLog error = new ErrorLog();
				error.setComponentName("BudgetDetailService");
				error.setError(ex.getMessage());
				error.setErrorMessage(getStackTrace(ex));
				error.setModule("getBudgetDetails");
				error.setSource("Java");
				error.setUserId(userObj.getUserId());
				errorLogRepo.save(error);
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("Budget Details getBudgetDetails Error", "Budget Details getBudgetDetails Error :" + ex.getMessage(),"getBudgetDetails" )));
			
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(budgetDetails));			
			
		}
		
	

		public ResponseEntity<ResponseMessage> save(BudgetDetails obj, UserLoginDetails userObj) {
			
			System.out.println("Inside save of BudgetDetails Service");
			BudgetDetails budgetDetails=null;
				
			try {
				obj.setCreated_by(userObj.getUserId());
				obj.setCreated_on(new Timestamp(System.currentTimeMillis()));
				obj.setPartnerId(userObj.getPartnerId());			
				budgetDetails = budgetDetailsRepo.save(obj);				
			}
			
			catch(Exception ex){
				
				ErrorLog error = new ErrorLog();
				error.setComponentName("BudgetDetailService");
				error.setError(ex.getMessage());
				error.setErrorMessage(getStackTrace(ex));
				error.setModule("Save");
				error.setSource("Java");
				error.setUserId(userObj.getUserId());
				errorLogRepo.save(error);
				return ResponseEntity.ok().body(new ResponseMessage(new APIError("Budget Details Save Error", "Budget Details Save Error :" + ex.getMessage(),"save" )));
				
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(budgetDetails));
		}

}

