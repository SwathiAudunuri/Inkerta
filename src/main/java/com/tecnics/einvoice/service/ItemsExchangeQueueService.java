package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.ItemsExchangeQueueRepo;
import com.tecnics.einvoice.constants.RecipientConstants;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.ItemsExchangeQueue;
import com.tecnics.einvoice.response.TransactionResponse;
@Component
public class ItemsExchangeQueueService extends BaseService{

	public static final String CLASSNAME="ItemsExchangeQueueService";
	
	
	@Autowired
	ItemsExchangeQueueRepo itemsExchangeQueueRepo;
	
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	

	public List<ItemsExchangeQueue> findAll() {
		return (List<ItemsExchangeQueue>) itemsExchangeQueueRepo.findAll();
	}

	public Optional<ItemsExchangeQueue> findById(int id) {
		return (Optional<ItemsExchangeQueue>) itemsExchangeQueueRepo.findById(id);
	}

	public ItemsExchangeQueue update(ItemsExchangeQueue obj) {
		return itemsExchangeQueueRepo.save(obj);
	}


	
	
	public TransactionResponse save(ItemsExchangeQueue obj) {
		final String methodname="TransactionResponse";
		log.logMethodEntry(CLASSNAME + methodname);
		
		int failCount =0;
		int successCount=0;

		TransactionResponse response = new TransactionResponse();
		try {
			ItemsExchangeQueue myresponse = itemsExchangeQueueRepo.save(obj);
			response.setRefid(myresponse.getBatchId());
			successCount++;
		}
		
		catch(Exception ex){
			log.logErrorMessage(ex.getMessage(),ex);
			failCount ++;
			errorLogRepo.save( new ErrorLog(CLASSNAME,"",getStackTrace(ex),RecipientConstants.SAVE_ACTION,"","Backend","TESTUSER"));
		}
		
		response.setFailureTransactionsCount(failCount);
		response.setSuccessTransactionsCount(successCount);
		response.setTotalTransactionsCount(failCount+successCount);
		return response;
	}

}
