
package com.tecnics.einvoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.OutboundConnectorsAutopostRepo;
import com.tecnics.einvoice.Repo.OutboundConnectorsGSTINRepo;
import com.tecnics.einvoice.Repo.OutboundConnectorsItemDeliveryEmailMappingRepo;
import com.tecnics.einvoice.Repo.OutboundConnectorsItemDeliveryWebserviceMappingRepo;
import com.tecnics.einvoice.Repo.OutboundConnectorsRepo;

import com.tecnics.einvoice.dao.OutboundConnectorsModelDAO;
import com.tecnics.einvoice.entity.ErrorLog;

import com.tecnics.einvoice.entity.OutboundConnectors;
import com.tecnics.einvoice.entity.OutboundConnectorsAutopost;
import com.tecnics.einvoice.entity.OutboundConnectorsGSTIN;
import com.tecnics.einvoice.entity.OutboundConnectorsItemDeliveryEmailMapping;
import com.tecnics.einvoice.entity.OutboundConnectorsItemDeliveryWebserviceMapping;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.OutboundConnectorsModel;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.util.APIError;
@Component
public class OutboundConnectorsService extends BaseService {

	@Autowired
	OutboundConnectorsRepo outboundConnectorsRepo;
	
	@Autowired
	OutboundConnectorsAutopostRepo outboundConnectorsAutopostRepo;
	
	@Autowired
	OutboundConnectorsGSTINRepo outboundConnectorsGSTINRepo;
	
	@Autowired
	OutboundConnectorsItemDeliveryEmailMappingRepo outboundConnectorsItemDeliveryEmailMappingRepo;
	
	@Autowired
	OutboundConnectorsItemDeliveryWebserviceMappingRepo outboundConnectorsItemDeliveryWebserviceMappingRepo;
	
	@Autowired
	OutboundConnectorsModelDAO outboundConnectorsModelDAO;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	public List<OutboundConnectors> findByPartnerId(String partnerId) {
		return outboundConnectorsRepo.findByPartnerId(partnerId);
	}
	
	public OutboundConnectorsGSTIN findByGstin(String gstin) {
		return (OutboundConnectorsGSTIN) outboundConnectorsGSTINRepo.findByGstin(gstin);
	}
	
	public List<OutboundConnectorsGSTIN> findGSINsByConnectorId(Integer connectorId)
	{
	return outboundConnectorsGSTINRepo.findByConnectorId(connectorId);
	}
	
	
	public OutboundConnectorsAutopost findAutopostConnectorByConnectorId(Integer connectorId) {
		return (OutboundConnectorsAutopost) outboundConnectorsAutopostRepo.findByConnectorId(connectorId);
	}
	
	public ResponseEntity<ResponseMessage> fetchConnectors(String partnerId)
	{
		
		List<OutboundConnectorsModel> outboundConnectorsModel=null;
		outboundConnectorsModel=outboundConnectorsModelDAO.fetchConnectors(partnerId);
		
		//Retrieving all GSTNs
		if(outboundConnectorsModel!=null)
		{
		for (OutboundConnectorsModel outboundConnectorsModel_val : outboundConnectorsModel) {
			
			List<OutboundConnectorsGSTIN> outboundConnectorsGSTIN=outboundConnectorsGSTINRepo.findByConnectorId(outboundConnectorsModel_val.getConnectorId());
			if(outboundConnectorsGSTIN!=null)
				outboundConnectorsModel_val.setOutboundConnectorsGSTIN(outboundConnectorsGSTIN);
			
        }
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(outboundConnectorsModel));
		
	}
	
	
	public ResponseEntity<ResponseMessage> fetchConnectorByConnectorId(Integer connectorId)
	{
		
		OutboundConnectorsModel outboundConnectorsModel=null;
		outboundConnectorsModel=outboundConnectorsModelDAO.fetchConnectorByConnectorId(connectorId);
		
		//Retrieving all GSTNs and other mapping details WebServices,Email and Autoposting
		if(outboundConnectorsModel!=null)
		{
			List<OutboundConnectorsGSTIN> outboundConnectorsGSTIN=outboundConnectorsGSTINRepo.findByConnectorId(outboundConnectorsModel.getConnectorId());
			if(outboundConnectorsGSTIN!=null)
				outboundConnectorsModel.setOutboundConnectorsGSTIN(outboundConnectorsGSTIN);
			List<OutboundConnectorsItemDeliveryWebserviceMapping> outboundConnectorsItemDeliveryWebserviceMapping=outboundConnectorsItemDeliveryWebserviceMappingRepo.findByConnectorId(connectorId);
			if(outboundConnectorsItemDeliveryWebserviceMapping!=null)			
				outboundConnectorsModel.setOutboundConnectorsItemDeliveryWebserviceMapping(outboundConnectorsItemDeliveryWebserviceMapping);
			
			List<OutboundConnectorsItemDeliveryEmailMapping> outboundConnectorsItemDeliveryEmailMapping=outboundConnectorsItemDeliveryEmailMappingRepo.findByConnectorId(connectorId);
			if(outboundConnectorsItemDeliveryEmailMapping!=null)
				outboundConnectorsModel.setOutboundConnectorsItemDeliveryEmailMapping(outboundConnectorsItemDeliveryEmailMapping);
			OutboundConnectorsAutopost outboundConnectorsAutopost=outboundConnectorsAutopostRepo.findByConnectorId(connectorId);
			if(outboundConnectorsAutopost!=null)
				outboundConnectorsModel.setOutboundConnectorsAutopost(outboundConnectorsAutopost);
			
        }

		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(outboundConnectorsModel));
		
	}
	
	
	public ResponseEntity<ResponseMessage> save(OutboundConnectorsModel obj, UserLoginDetails userObj) {
		int failCount =0,successCount=0;
		System.out.println("Inside save of OutboundConnectors Service");
			TransactionResponse outboundConnectorsResponse = new TransactionResponse();
		try {
			OutboundConnectors outboundConnectors=new OutboundConnectors();
			outboundConnectors.setIsActive(obj.isActive());
			outboundConnectors.setAutopostOnVerification(obj.isAutopostOnVerification());
			outboundConnectors.setConnectorTag(obj.getConnectorTag());
			outboundConnectors.setCreatedBy(userObj.getUserId());
			outboundConnectors.setDescription(obj.getDescription());
			outboundConnectors.setInvoicequeryDeliverymode(obj.getInvoicequeryDeliverymode());
			outboundConnectors.setInvoicestatusupdateDeliverymode(obj.getInvoicestatusupdateDeliverymode());
			outboundConnectors.setInvoiceuploadDeliverymode(obj.getInvoiceuploadDeliverymode());
			outboundConnectors.setPartnerId(userObj.getPartnerId());
			
			
			List<OutboundConnectorsGSTIN> outboundConnectorsGSTIN=obj.getOutboundConnectorsGSTIN();
			
			OutboundConnectorsAutopost outboundConnectorsAutopost=obj.getOutboundConnectorsAutopost();
			List<OutboundConnectorsItemDeliveryWebserviceMapping> outboundConnectorsItemDeliveryWebserviceMapping=obj.getOutboundConnectorsItemDeliveryWebserviceMapping();
			List<OutboundConnectorsItemDeliveryEmailMapping> outboundConnectorsItemDeliveryEmailMapping=obj.getOutboundConnectorsItemDeliveryEmailMapping();
			
			OutboundConnectors outboundConnectors_saved=outboundConnectorsRepo.save(outboundConnectors);
			System.out.println("Connector id for the saved object = "+outboundConnectors_saved.getConnectorId());
			
			//Processing and Saving GSTNs
			if(outboundConnectorsGSTIN!=null)
			{
			for (OutboundConnectorsGSTIN outboundConnectorsGSTIN_val : outboundConnectorsGSTIN) {	            
	            outboundConnectorsGSTIN_val.setConnectorId(outboundConnectors_saved.getConnectorId());	            
	        }
			if(outboundConnectorsGSTIN.size()>0)
			outboundConnectorsGSTINRepo.saveAll(outboundConnectorsGSTIN);
			}
			
			//Processing and Saving OutboundConnectorsItemDeliveryWebserviceMapping
			if(outboundConnectorsItemDeliveryWebserviceMapping!=null)
			{
			for (OutboundConnectorsItemDeliveryWebserviceMapping outboundConnectorsItemDeliveryWebserviceMapping_val : outboundConnectorsItemDeliveryWebserviceMapping) {	            
				outboundConnectorsItemDeliveryWebserviceMapping_val.setConnectorId(outboundConnectors_saved.getConnectorId());	            
	        }
			if(outboundConnectorsItemDeliveryWebserviceMapping.size()>0)
			outboundConnectorsItemDeliveryWebserviceMappingRepo.saveAll(outboundConnectorsItemDeliveryWebserviceMapping);
			}
			
			//Processing and Saving OutboundConnectorsItemDeliveryEmailMapping
			if(outboundConnectorsItemDeliveryEmailMapping!=null)
			{
			for (OutboundConnectorsItemDeliveryEmailMapping outboundConnectorsItemDeliveryEmailMapping_val : outboundConnectorsItemDeliveryEmailMapping) {	            
				outboundConnectorsItemDeliveryEmailMapping_val.setConnectorId(outboundConnectors_saved.getConnectorId());	            
	        }	
			if(outboundConnectorsItemDeliveryEmailMapping.size()>0)
			outboundConnectorsItemDeliveryEmailMappingRepo.saveAll(outboundConnectorsItemDeliveryEmailMapping);
			}
			
			//Processing and Saving OutboundConnectorsAutopost configurations
			if(outboundConnectorsAutopost!=null)
			{
			outboundConnectorsAutopost.setConnectorId(outboundConnectors_saved.getConnectorId());
			outboundConnectorsAutopostRepo.save(outboundConnectorsAutopost);
			}
			
			
			successCount++;
			outboundConnectorsResponse.setRefid(outboundConnectors_saved.getConnectorId());	
			outboundConnectorsResponse.setSuccessTransactionsCount(successCount);
			outboundConnectorsResponse.setTotalTransactionsCount(successCount);
		}
		
		catch(Exception ex){
			ex.printStackTrace();
			failCount ++;
			ErrorLog error = new ErrorLog();
			error.setComponentName("OutboundConnectors");
			error.setError(ex.getMessage());
			error.setErrorMessage(getStackTrace(ex));
			error.setModule("Save");
			error.setSource("Java");
			error.setUserId(userObj.getUserId());
			errorLogRepo.save(error);			
				return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.OUTBOUNDCONNECTORS_ERROR.getKey(),Ex.formatMessage(Ex.OUTBOUNDCONNECTORS_ERROR.getKeyMessage()),Ex.OUTBOUNDCONNECTORS_ERROR.getKeyMessage())));
			
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(outboundConnectorsResponse));
	
	}
	
	
}





