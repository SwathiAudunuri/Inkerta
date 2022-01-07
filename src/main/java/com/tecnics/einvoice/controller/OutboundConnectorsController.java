package com.tecnics.einvoice.controller;


import java.sql.Timestamp;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tecnics.einvoice.constants.Constants;

import com.tecnics.einvoice.exceptions.Ex;

import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoice.service.OutboundConnectorsService;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.model.InvoiceGridResponse;
import com.tecnics.einvoice.model.OutboundConnectorsModel;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class OutboundConnectorsController extends BaseController {
	
	@RequestMapping("/verifyOutboundConnectorsController")  
    public String verify()  
    {  
        return "The OutboundConnectorsController is up and running";  
    } 
	
	@Autowired
	OutboundConnectorsService outboundConnectorsService;
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/outboundconnectors/fetchConnector/{connectorId}")
	public ResponseEntity<ResponseMessage> fetchConnector(@PathVariable Integer connectorId,@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside fetchConnectors");
			
			String partnerId=userObj.getPartnerId();
			System.out.println("PartnerId from fetchConnectors " +partnerId );
			ResponseEntity<ResponseMessage> response = outboundConnectorsService.fetchConnectorByConnectorId(connectorId);
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_LIST_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_LIST_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}

	}
	
	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/outboundconnectors/fetchConnectors")
	public ResponseEntity<ResponseMessage> fetchConnectors(@RequestHeader("authorization") String token) {
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			System.out.println("Inside fetchConnectors");
			
			String partnerId=userObj.getPartnerId();
			System.out.println("PartnerId from fetchConnectors " +partnerId );
			ResponseEntity<ResponseMessage> response = outboundConnectorsService.fetchConnectors(partnerId);
			return response;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_LIST_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_LIST_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}

	}
	
	
	/***
	 * 
	 * @param OutboundConnectorsModel
	 * @param token
	
	 * @return
	 */
	@PostMapping("/outboundconnectors/connectorSave")

	public ResponseEntity<ResponseMessage> save(@RequestBody OutboundConnectorsModel outboundConnectorsModel,@RequestHeader("authorization") String token) 
		{
		try
		{
		System.out.println("Inside outboundconnectors connectorSave");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		String jsonString = objectMapper.writeValueAsString(outboundConnectorsModel);
		System.out.println("jsonString = " + jsonString);
		UserLoginDetails userObj=getUserObjFromToken(token);
		return outboundConnectorsService.save(outboundConnectorsModel, userObj);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
		}

}
