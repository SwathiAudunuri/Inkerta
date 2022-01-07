
package com.tecnics.einvoice.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;

import com.tecnics.einvoice.Repo.CompanyRatingRepo;
import com.tecnics.einvoice.Repo.CompanyRatingRepo.PaidInvoicesForCompanyRatingResults;
import com.tecnics.einvoice.entity.ErrorLog;

import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;


import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.util.APIError;
@Component

public class CompanyRatingService extends BaseService {
	

	@Autowired
	CompanyRatingRepo companyRatingRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	
	public String getCompanyRating(UserLoginDetails userObj,List<PaidInvoicesForCompanyRatingResults> inputEntity, BigDecimal buffer_days) {

		try
		{
				int inputLength = inputEntity.size();
				System.out.println("inputLength in getCompanyRating" + inputLength );
		
				BigDecimal totalInvoiceValue = new BigDecimal(0);
				BigDecimal invoiceOverdue = new BigDecimal(0);
				BigDecimal sumOfInvoiceOverdue = new BigDecimal(0);
				for(int i = 0; i <= inputLength-1;i++) {
		
					BigDecimal amount = new BigDecimal(inputEntity.get(i).getTotal_invoice_value().intValue());
					totalInvoiceValue = totalInvoiceValue.add(amount);
					
					System.out.println(" totalInvoiceValue = " + totalInvoiceValue + " at position " + i);
					if(inputEntity.get(i).getAge_in_days()!=null && inputEntity.get(i).getAge_in_days()>0)
					{
					invoiceOverdue = inputEntity.get(i).getTotal_invoice_value().multiply(new BigDecimal(inputEntity.get(i).getAge_in_days().intValue()));
					sumOfInvoiceOverdue = sumOfInvoiceOverdue.add(invoiceOverdue);
					}
					else
						sumOfInvoiceOverdue = new BigDecimal(0);	
					
					System.out.println(" sumOfInvoiceOverdue = " + sumOfInvoiceOverdue + " at position " + i);
					
		
				}
		
						System.out.println(totalInvoiceValue);
						System.out.println(sumOfInvoiceOverdue);
				
				BigDecimal avgNumberOfDays = sumOfInvoiceOverdue.divide(totalInvoiceValue,0,RoundingMode.CEILING);
				System.out.println("Avg number of days   " + avgNumberOfDays);
				
				if(avgNumberOfDays.compareTo(buffer_days)==-1 || avgNumberOfDays.compareTo(buffer_days)==0) {
					System.out.println("A");
					return "A";
				}
				else if (avgNumberOfDays.compareTo(buffer_days)==1 && avgNumberOfDays.intValue() <= 30 ) {
					System.out.println("B");
					return "B";
				}
				else if(avgNumberOfDays.intValue() > 30 && avgNumberOfDays.intValue() <= 60) {
					System.out.println("C");
					return "C";
				}
				else if (avgNumberOfDays.intValue() > 60 && avgNumberOfDays.intValue() <= 90) {
					System.out.println("D");
					return "D";
				}
				else {
					System.out.println("E");
					return "E";
				}

			
	}
			catch(Exception ex){
				ex.printStackTrace();
				ErrorLog error = new ErrorLog();
				error.setComponentName("CustomActionsService");
				error.setError(ex.getMessage());
				error.setErrorMessage(getStackTrace(ex));
				error.setModule("Save");
				error.setSource("Java");
				error.setUserId(userObj.getUserId());
				errorLogRepo.save(error);
				
			}

	return null;
	}

}

