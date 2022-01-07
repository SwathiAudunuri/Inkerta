/**
 * 
 */
package com.tecnics.einvoice.controller;

import java.util.List;

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
import com.tecnics.einvoice.alfresco.AlfrescoService;
import com.tecnics.einvoice.alfresco.FolderRequest;
import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.entity.InvoiceAttachmentDetail;
import com.tecnics.einvoice.entity.InvoicePullResponse;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.model.InvoiceGridResponse;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.InvoiceDocumentDetailService;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.model.InvoiceQRCodeData;
import com.tecnics.einvoicejson.model.InvoiceJsonModel;
import com.tecnics.einvoicejson.model.ReqModel;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class InvoiceController extends BaseController {

	@Autowired
	AlfrescoService alfrescoService;

	@Autowired
	InvoiceDocumentDetailService detailService;
	
	

	FolderRequest folderRequest = new FolderRequest();

	UserLoginDetails userLoginDetails = new UserLoginDetails();
	/**
	 * End point test url to check service status
	 * 
	 * @return
	 */
	@GetMapping("/test1")
	public String getMessage() {
		return "Hi Its Working";
	}
	/***
	 * 
	 * @param invoicedetails
	 * @param token
	 * @param requestLength
	 * @return
	 */
	@PostMapping("/invoiceUpload")
	public ResponseEntity<ResponseMessage> invoiceUpload(@RequestBody List<InvoiceRequestModel> invoicedetails,
			@RequestHeader("authorization") String token, @RequestHeader("Content-Length") float requestLength) {
		UserLoginDetails userObj=getUserObjFromToken(token);
		if (requestLength > Constants.INV_UPLOAD_LIMIT)
			return ResponseEntity.ok().body(new ResponseMessage(
					new APIError(Ex.EXCEEDING_INV_LIMIT.getKey(), Ex.EXCEEDING_INV_LIMIT.getKeyMessage())));
		return detailService.save(invoicedetails, userObj);
	}

	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/invoiceList")
	public ResponseEntity<ResponseMessage> findAll(@RequestHeader("authorization") String token) {
		try {
			List<InvoiceGridResponse> response = detailService.findAll(getUserName(token));
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_LIST_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_LIST_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}

	}
	
	@GetMapping("/customerInvoiceList/{filterParam}")
	public ResponseEntity<ResponseMessage> findcustomerInvoices(@PathVariable String filterParam,@RequestHeader("authorization") String token) {
		System.out.println("inside customerInvoiceList  of " + filterParam + " at " + new java.util.Date());
		try {
			UserLoginDetails userObj=getUserObjFromToken(token);
			List<InvoiceGridResponse> response = detailService.findCustomerInvoices(userObj,filterParam);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_LIST_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_LIST_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}

	}
	

	
	@GetMapping("/vendorInvoiceList/{filterParam}")
	public ResponseEntity<ResponseMessage> findVendorInvoices(@PathVariable String filterParam,@RequestHeader("authorization") String token) {
		try {
			System.out.println("Finding vendor invoices");
			List<InvoiceGridResponse> response = detailService.findVendorInvoices(getUserName(token),filterParam);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_LIST_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_LIST_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}

	}

	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("/getInvoiceDetails/{documentRefId}")
	public ResponseEntity<ResponseMessage> getInvoiceDetails(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		try {
			InvoiceRequestModel response = detailService.getInvoiceDetails(documentRefId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("/getPDFInvoiceDocument/{documentRefId}")
	public ResponseEntity<ResponseMessage> getPDFInvoiceDocument(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		System.out.println("DocumentRefID of the attachment to be retrieved ="+ documentRefId);
		try {
			InvoiceAttachmentDetail response = detailService.getPDFInvoiceDocument(getUserName(token),documentRefId);
			if(response==null)
			{
				System.out.println("There is no document found for the document ref id " + documentRefId);
				return ResponseEntity.ok().body(new ResponseMessage(
						new APIError(Ex.INV_DOC_NOT_FOUND.getKey(), Ex.formatMessage(Ex.INV_DOC_NOT_FOUND.getKeyMessage(),documentRefId))));
			}
			System.out.println("Response returning getPDFInvoiceDocument");
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}	
	
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("/getSupportingInvoiceDocumentDetails/{documentRefId}")
	public ResponseEntity<ResponseMessage> getSupportingInvoiceDocumentDetails(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		try {
			List<InvoiceAttachmentDetail> response = detailService.getSupportingInvoiceDocumentDetails(getUserName(token), documentRefId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("/getSupportingInvoiceDocument/{docId}")
	public ResponseEntity<ResponseMessage> getSupportingInvoiceDocument(@PathVariable String docId,
			@RequestHeader("authorization") String token) {
		System.out.println("Document ID of the attachment to be retrieved ="+ docId);
		try {
			InvoiceAttachmentDetail response = detailService.getSupportingInvoiceDocument(getUserName(token),docId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_DTL_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_DTL_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}
	
	/***
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 */
	@GetMapping("/verifyQRCode/{documentRefId}")
	public ResponseEntity<ResponseMessage> verifyQRCode(@PathVariable String documentRefId,
			@RequestHeader("authorization") String token) {
		System.out.println("DocumentRefID of the attachment to be verified for QRCode ="+ documentRefId);
		try {
	
			InvoiceQRCodeData response = detailService.verifyQRCode(getUserName(token),documentRefId);
			if(response.getQrcodebase64()==null)
				
			{
				System.out.println("There is an error while decoding QR code for the document ref id " + documentRefId);
				return ResponseEntity.ok().body(new ResponseMessage(
						new APIError(Ex.INV_QRCODE_DECODE_ERROR.getKey(), Ex.formatMessage(Ex.INV_QRCODE_DECODE_ERROR.getKeyMessage(),documentRefId))));
			}

			else
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_QRCODE_DECODE_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_QRCODE_DECODE_ERROR.getKeyMessage(),documentRefId), getStackTrace(e))));
		}
	}	

	/***
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/pullInvoices")
	public ResponseEntity<ResponseMessage> getInvoiceInfoPull(@RequestHeader("authorization") String token) {
		try {

			String recipientId = detailService.getRecipientId(getUserName(token));
			if (detailService.getRecipientId(getUserName(token)) != null) {
				InvoicePullResponse response = detailService.getInvoiceInfoPull(getUserName(token), recipientId);
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));
			}
			return null;

		} catch (EmptyResultDataAccessException e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.NO_RECIPIENTS_MAPPED.getKey(),
					Ex.formatMessage(Ex.NO_RECIPIENTS_MAPPED.getKeyMessage()), getStackTrace(e))));
		} catch (IncorrectResultSizeDataAccessException e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.MULTIPLE_RECIPIENTS_MAPPED.getKey(),
					Ex.formatMessage(Ex.MULTIPLE_RECIPIENTS_MAPPED.getKeyMessage()), getStackTrace(e))));
		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_LIST_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_LIST_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}

	/**
	 * 
	 * @param token
	 * @param recipientId
	 * @return
	 */
	@GetMapping("/pullInvoices/{recipientId}")
	public ResponseEntity<ResponseMessage> getInvoiceInfoPull(@RequestHeader("authorization") String token,
			@PathVariable("recipientId") String recipientId) {
		try {
			InvoicePullResponse response = detailService.getInvoiceInfoPull(getUserName(token), recipientId);
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(response));

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.INV_LIST_FETCH_ERROR.getKey(),
					Ex.formatMessage(Ex.INV_LIST_FETCH_ERROR.getKeyMessage()), getStackTrace(e))));
		}
	}
	
	
	@PostMapping("/pull/ack/{status}")
	public int acknowlegeInkreta(@PathVariable String status,@RequestBody String docSourceRefId) {
		try {
			int i = detailService.acknowlegeInkreta(status,docSourceRefId);
			return i;

		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage(e.getMessage(), e);
			return 0;
		}
		
	}
//	@PostMapping("/inv/json")
//	public ReqModel uploadInvoiceJson(@RequestBody  List<InvoiceJsonModel> obj){
//		System.err.println("Hello i'm calling");
//		return detailService.JsonUpload(obj);
//	}
	@PostMapping("/inv/jsonConvert")
	public String uploadInvoiceJsonConvert(@RequestBody  List<InvoiceJsonModel> obj){
		//System.err.println("Hello i'm calling");
		return detailService.JsonUpload(obj);
	}
}
