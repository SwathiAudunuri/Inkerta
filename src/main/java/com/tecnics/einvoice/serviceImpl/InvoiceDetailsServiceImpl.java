package com.tecnics.einvoice.serviceImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tecnics.einvoice.Repo.FolderObjectsRepo;
import com.tecnics.einvoice.Repo.InvoiceAttachmentDetailsRepo;
import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.entity.FolderObjects;
import com.tecnics.einvoice.entity.InvoiceAttachmentDetail;
import com.tecnics.einvoice.entity.InvoiceDispatchShiptoDetail;
import com.tecnics.einvoice.entity.InvoiceEwayBillDetail;
import com.tecnics.einvoice.entity.InvoiceItemList;
import com.tecnics.einvoice.entity.InvoicePullResponse;
import com.tecnics.einvoice.entity.InvoiceReferenceDetails;
import com.tecnics.einvoice.entity.InvoiceRequestModel;
import com.tecnics.einvoice.entity.InvoiceSellerPaymentInformation;
import com.tecnics.einvoice.entity.InvoiceSupplierBuyerInfo;
import com.tecnics.einvoice.file.manager.CreateFolderRequest;
import com.tecnics.einvoice.file.manager.CustomeResponseEntity;
import com.tecnics.einvoice.file.manager.DocUploadRequest;
import com.tecnics.einvoice.model.InvoiceGridResponse;
import com.tecnics.einvoice.model.InvoiceMetaDataModel;
import com.tecnics.einvoice.model.UserLoginDetails;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
@Component
public class InvoiceDetailsServiceImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private Environment env;
	
	@Autowired
	private FolderObjectsRepo folderObjectsRepo;

	@Autowired
	private InvoiceAttachmentDetailsRepo invoiceAttachmentDetailsRepo;
	/**
	 * generateDocSourceRefId
	 * 
	 * @return
	 */
	public String generateDocSourceRefId() {
		String sql = "select einvoicing.generate_doc_source_ref_id() ";
		return jdbcTemplate.queryForObject(sql, new Object[] {}, String.class);
	}

	/**
	 * generateDocRefId
	 * 
	 * @param subtypeCode
	 * @return
	 */
	public String generateDocRefId(String subtypeCode) {
		String sql = "select einvoicing.generate_document_ref_id(?) ";
		if (subtypeCode == null)
			subtypeCode = "INV";
		String refId = jdbcTemplate.queryForObject(sql, new Object[] { subtypeCode }, String.class);
		return refId;
	}

	/**
	 * rollbackFailureRecord
	 * 
	 * @param documentRefId
	 * @return
	 */
	public String rollbackFailureRecord(String documentRefId) {
		String status = null;
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			java.sql.CallableStatement cstmt;
			cstmt = connection.prepareCall("CALL einvoicing.sp_delete_invoice(?,'')");
			cstmt.setString(1, documentRefId);
			cstmt.registerOutParameter(1, Types.VARCHAR);
			cstmt.executeUpdate();
			status = cstmt.getString(1);
			cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * findUnpaidInvoicesByVendorPartnerId
	 * 
	 * @param partnerId
	 * @return
	 */
	public List<InvoiceGridResponse> findUnpaidInvoicesByVendorPartnerId(String partnerId) {
		System.out.println("** Partner Id =  " + partnerId);
		return jdbcTemplate.query(SQLQueries.FIND_UNPAID_INV_FROM_VENDOR, new Object[] { partnerId,partnerId },
				new BeanPropertyRowMapper(InvoiceGridResponse.class));
	}
	

	/**
	 * findByVendorPartnerId
	 * 
	 * @param partnerId
	 * @return
	 */
	public List<InvoiceGridResponse> findPaidInvoicesByVendorPartnerId(String partnerId) {

		return jdbcTemplate.query(SQLQueries.FIND_PAID_INV_FROM_VENDOR, new Object[] { partnerId,partnerId },
				new BeanPropertyRowMapper(InvoiceGridResponse.class));
	}
	
	
	/**
	 * findByVendorPartnerId
	 * 
	 * @param partnerId
	 * @return
	 */
	public List<InvoiceGridResponse> findQueriedInvoicesByVendorPartnerId(String partnerId) {

		return jdbcTemplate.query(SQLQueries.FIND_QUERIED_INV_FROM_VENDOR, new Object[] { partnerId },
				new BeanPropertyRowMapper(InvoiceGridResponse.class));
	}
	
	
	
	/**
	 * findByCustomerPartnerId
	 * 
	 * @param partnerId
	 * @return
	 */
	public List<InvoiceGridResponse> findUnpaidInvoicesByCustomerPartnerId(UserLoginDetails userObj) {
		
		if(userObj.getRoles().contains("customer_user") || userObj.getRoles().contains("businesspartner_user"))
		{
			System.out.println("Processing Customer user view");
			return jdbcTemplate.query(SQLQueries.FIND_UNPAID_INV_FROM_CUSTOMER_FOR_USER, new Object[] { userObj.getPartnerId(),userObj.getPartnerId() },
				new BeanPropertyRowMapper(InvoiceGridResponse.class));
		}
		else
		{
			System.out.println("Processing Customer manager view");
			return jdbcTemplate.query(SQLQueries.FIND_UNPAID_INV_FROM_CUSTOMER_FOR_MANAGER, new Object[] { userObj.getPartnerId(),userObj.getPartnerId()  },
					new BeanPropertyRowMapper(InvoiceGridResponse.class));
		}
			
		
		
	}
	
	/**
	 * findPaidInvoicesByCustomerPartnerId
	 * 
	 * @param partnerId
	 * @return
	 */
	public List<InvoiceGridResponse> findPaidInvoicesByCustomerPartnerId(UserLoginDetails userObj) {
		
		if(userObj.getRoles().contains("customer_user"))
		{
			
			return jdbcTemplate.query(SQLQueries.FIND_PAID_INV_FROM_CUSTOMER_FOR_USER, new Object[] { userObj.getPartnerId(),userObj.getPartnerId() },
				new BeanPropertyRowMapper(InvoiceGridResponse.class));		
		}
		else
			return jdbcTemplate.query(SQLQueries.FIND_PAID_INV_FROM_CUSTOMER_FOR_MANAGER, new Object[] { userObj.getPartnerId(),userObj.getPartnerId() },
					new BeanPropertyRowMapper(InvoiceGridResponse.class));	
			
		
	}
	
	/**
	 * findQueriedInvoicesByCustomerPartnerId
	 * 
	 * @param partnerId
	 * @return
	 */
	public List<InvoiceGridResponse> findQueriedInvoicesByCustomerPartnerId(UserLoginDetails userObj) {
		
		if(userObj.getRoles().contains("customer_user"))
		{
			
		return jdbcTemplate.query(SQLQueries.FIND_QUERIED_INV_FROM_CUSTOMER_FOR_USER, new Object[] { userObj.getPartnerId() },
			new BeanPropertyRowMapper(InvoiceGridResponse.class));		
		}
		else
			return jdbcTemplate.query(SQLQueries.FIND_QUERIED_INV_FROM_CUSTOMER_FOR_MANAGER, new Object[] { userObj.getPartnerId() },
					new BeanPropertyRowMapper(InvoiceGridResponse.class));	
			
	
}
	
	/**
	 * findByCustomerPartnerId
	 * 
	 * @param partnerId
	 * @return
	 */
	public List<InvoiceGridResponse> findExceptionInvoicesByCustomerPartnerId(UserLoginDetails userObj) {
		
		if(userObj.getRoles().contains("customer_user"))
		{
			
			return jdbcTemplate.query(SQLQueries.FIND_EXCEPTIONS_INV_FROM_CUSTOMER_FOR_USER, new Object[] { userObj.getPartnerId() },
				new BeanPropertyRowMapper(InvoiceGridResponse.class));		
		}
		else
			return jdbcTemplate.query(SQLQueries.FIND_EXCEPTIONS_INV_FROM_CUSTOMER_FOR_MANAGER, new Object[] { userObj.getPartnerId() },
					new BeanPropertyRowMapper(InvoiceGridResponse.class));	
			
		
	}
	
	/**
	 * findAssignedToMeByUserIdandCustomerPartnerId
	 * 
	 * @param partnerId
	 * @return
	 */
	public List<InvoiceGridResponse> findAssignedToMeInvoicesByCustomerPartnerId(String partnerId,String userId) {
		
			return jdbcTemplate.query(SQLQueries.FIND_ASSIGNED_TO_ME_INV_FROM_CUSTOMER, new Object[] { partnerId,userId, partnerId, userId },
				new BeanPropertyRowMapper(InvoiceGridResponse.class));		
		
	}
	
	

	/**
	 * getInvoiceDetails
	 * 
	 * @param documentRefId
	 * @param token
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public InvoiceRequestModel getInvoiceDetails(String documentRefId) throws MalformedURLException, IOException {

		InvoiceRequestModel model = new InvoiceRequestModel();
		InvoiceMetaDataModel invoiceDocumentDetail = getInvoiceMetaDataModel(documentRefId);
		InvoiceSellerPaymentInformation sellerPaymentInformation = getSellerPaymentInformation(documentRefId);
		InvoiceSupplierBuyerInfo supplierBuyerInfo = getInvoiceSupplierBuyerInfo(documentRefId);
		InvoiceEwayBillDetail ewayBillDetail = getInvoiceEwayBillDetail(documentRefId);
		InvoiceReferenceDetails invoiceReferenceDetails = getInvoiceReferenceDetails(documentRefId);
		InvoiceDispatchShiptoDetail dispatchShiptoDetail = getInvoiceDispatchShiptoDetail(documentRefId);
		List<InvoiceItemList> lineItemDetails = getInvoiceItemList(documentRefId);

		//commented below 3+1 line to make sure we return without attachment, there is a seperate service takes care of returning attahcmnets
		//List<InvoiceAttachmentDetail> invoiceAttachmentDetails = getInvoiceAttachmentDetail(documentRefId);
		
		//System.out.println("getInvoiceDetails AttachmentDetails size = " + invoiceAttachmentDetails.size());
		
		//invoiceAttachmentDetails = getAttachmentsBase64(invoiceAttachmentDetails);
		
		model.setInvoiceDetails(invoiceDocumentDetail);
		
		model.setInvoiceSellerPaymentDetails(sellerPaymentInformation);
		model.setInvoiceEwayBillDetails(ewayBillDetail);
		model.setInvoiceReferenceDetails(invoiceReferenceDetails);
		model.setInvoiceSupplierBuyerDetails(supplierBuyerInfo);
		model.setInvoiceDispatchShiptoDetails(dispatchShiptoDetail);
		model.setLineItemDetails(lineItemDetails);
		//model.setInvoiceAttachmentDetails(invoiceAttachmentDetails);

		return model;
	}

	private List<InvoiceAttachmentDetail> getAttachmentsBase64(List<InvoiceAttachmentDetail> fileList) throws IOException {
		
		List<InvoiceAttachmentDetail> mylist = new ArrayList<>() ;
		
		for(InvoiceAttachmentDetail obj :fileList) {
			//String docId =obj.getDoc_id();
			//String  path =getFilePath(docId);
			String  path =obj.getDocPath();
			InputStream finput = new FileInputStream(path);
			    byte[] bytes = IOUtils.toByteArray(finput);
			    String encoded = Base64.getEncoder().encodeToString(bytes);
			    obj.setBase64(encoded);
			    mylist.add(obj);
		}
		
		return mylist;
	}
	
	public InvoiceAttachmentDetail findPDFInvoiceDocument(String documentRefId, String docType) throws IOException {
		List<InvoiceAttachmentDetail> invoiceAttachmentDetail=invoiceAttachmentDetailsRepo.findByRefIdAndDocType(documentRefId, docType);
		if(invoiceAttachmentDetail.size()>0)
		return invoiceAttachmentDetail.get(0);
		else
			return null;
	}
	
	public InvoiceAttachmentDetail findSupportingInvoiceDocument(String docId) throws IOException {
		InvoiceAttachmentDetail invoiceAttachmentDetail=invoiceAttachmentDetailsRepo.findByDocId(docId);
		if(invoiceAttachmentDetail!=null)
		return invoiceAttachmentDetail;
		else
			return null;
	}
	
	public List<InvoiceAttachmentDetail> getSupportingInvoiceDocumentDetails(String documentRefId, String docType) throws IOException {
		
		List<InvoiceAttachmentDetail> invoiceAttachmentDetails=invoiceAttachmentDetailsRepo.findByRefIdAndDocType(documentRefId, docType);
		return invoiceAttachmentDetails;
	}

	
	
	public String getFilePath(String docId) {
		ResponseEntity<CustomeResponseEntity> response = null;
		String methodname = "productsFolderCreate";
		try {

			response = restTemplate.getForEntity(env.getProperty("get.file.url") + docId, CustomeResponseEntity.class);
		} catch (Exception e) {
			return null;
		}
		return (String) response.getBody().getResults();
	}
	
	
	
	/**
	 * 
	 * @param documentRefId
	 * @return
	 */
	private InvoiceMetaDataModel getInvoiceMetaDataModel(String documentRefId) {
		try {
			return (InvoiceMetaDataModel) jdbcTemplate.queryForObject(SQLQueries.GET_INVOICE_DETAILS,
					new Object[] { documentRefId }, new BeanPropertyRowMapper(InvoiceMetaDataModel.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	/***
	 * 
	 * @param documentRefId
	 * @return
	 */
	private List<InvoiceItemList> getInvoiceItemList(String documentRefId) {
		List<InvoiceItemList> obj = new ArrayList<>();
		try {
			return jdbcTemplate.query(SQLQueries.GET_INV_LINEITEMS, new Object[] { documentRefId },
					new BeanPropertyRowMapper<InvoiceItemList>(InvoiceItemList.class));
		} catch (EmptyResultDataAccessException e) {
			return obj;
		}

	}

	/***
	 * 
	 * @param documentRefId
	 * @return
	 */
	private List<InvoiceAttachmentDetail> getInvoiceAttachmentDetail(String documentRefId) {
		List<InvoiceAttachmentDetail> obj = new ArrayList<InvoiceAttachmentDetail>();
		try {
			return jdbcTemplate.query(SQLQueries.GET_INV_ATTACHMENT_DTLS, new Object[] { documentRefId },
					new BeanPropertyRowMapper<InvoiceAttachmentDetail>(InvoiceAttachmentDetail.class));
		} catch (EmptyResultDataAccessException e) {
			return obj;
		}
	}

	/***
	 * 
	 * @param documentRefId
	 * @return
	 */
	private InvoiceDispatchShiptoDetail getInvoiceDispatchShiptoDetail(String documentRefId) {
		try {
			return (InvoiceDispatchShiptoDetail) jdbcTemplate.queryForObject(SQLQueries.GET_DISPATCH_DETAILS,
					new Object[] { documentRefId }, new BeanPropertyRowMapper(InvoiceDispatchShiptoDetail.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	/***
	 * 
	 * @param documentRefId
	 * @return
	 */
	private InvoiceEwayBillDetail getInvoiceEwayBillDetail(String documentRefId) {
		try {
			return (InvoiceEwayBillDetail) jdbcTemplate.queryForObject(SQLQueries.GET_EWAYBILL_DETAILS,
					new Object[] { documentRefId }, new BeanPropertyRowMapper(InvoiceEwayBillDetail.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	/***
	 * 
	 * @param documentRefId
	 * @return
	 */
	private InvoiceReferenceDetails getInvoiceReferenceDetails(String documentRefId) {
		try {
			return (InvoiceReferenceDetails) jdbcTemplate.queryForObject(SQLQueries.GET_INVOICE_REFERENCE_DETAILS,
					new Object[] { documentRefId }, new BeanPropertyRowMapper(InvoiceReferenceDetails.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	

	/**
	 * 
	 * @param documentRefId
	 * @return
	 */
	private InvoiceSupplierBuyerInfo getInvoiceSupplierBuyerInfo(String documentRefId) {
		try {
			return (InvoiceSupplierBuyerInfo) jdbcTemplate.queryForObject(SQLQueries.GET_SUPPLIER_BUYER_DETAILS,
					new Object[] { documentRefId }, new BeanPropertyRowMapper(InvoiceSupplierBuyerInfo.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	/***
	 * 
	 * @param documentRefId
	 * @return
	 */
	private InvoiceSellerPaymentInformation getSellerPaymentInformation(String documentRefId) {
		try {
			return (InvoiceSellerPaymentInformation) jdbcTemplate.queryForObject(SQLQueries.GET_SELLER_DETAILS,
					new Object[] { documentRefId }, new BeanPropertyRowMapper(InvoiceSellerPaymentInformation.class));
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	/****
	 * 
	 * @param recipientId
	 * @return
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public InvoicePullResponse getInvoiceInfoPull(String recipientId) throws MalformedURLException, IOException {

		InvoicePullResponse response = new InvoicePullResponse();
		List<String> docSourceIds = jdbcTemplate.queryForList(SQLQueries.GET_DOC_SRC_REF_ID_LIST,
				new Object[] { recipientId }, String.class);
		List<InvoiceRequestModel> invoices = new ArrayList<InvoiceRequestModel>();
		if (!docSourceIds.isEmpty() && docSourceIds.size() > 0) {
			if (docSourceIds.size() > 1) {
				response.setMoreBatchesAvailable(true);
			}
			List<String> docRefIds = jdbcTemplate.queryForList(SQLQueries.GET_DOCREFIDSBYREFID,
					new Object[] { docSourceIds.get(0), recipientId }, String.class);

			for (int j = 0; j < docRefIds.size(); j++) {
				InvoiceRequestModel result = getInvoiceDetails(docRefIds.get(j));
				invoices.add(result);

			}

			updateStatusFlagForbatch(recipientId, docRefIds.get(0));

		}

		response.setInvoices(invoices);
		return response;
	}

	/**
	 * 
	 * @param recipientId
	 * @param string
	 */
	private void updateStatusFlagForbatch(String recipientId, String string) {

		Object[] params = { recipientId, string };
		int[] types = { Types.VARCHAR, Types.VARCHAR };
		int rows = jdbcTemplate.update(SQLQueries.UPDATE_QUEUE_STATUS, params, types);
	}

	/**
	 * * Fetches customer Partner Id from recipient mapping using customer Recipient
	 * Code
	 * 
	 * @param recipientID
	 * @return
	 */
	public String fetchpartnerIdByRecipientCode(String recipientID) {
		String partnerId = null;
		try {
			partnerId = jdbcTemplate.queryForObject(SQLQueries.GET_PARTNERID_BY_RECIPIENTID,
					new Object[] { recipientID }, String.class);
		}

		catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return partnerId;
	}

	/**
	 * recipeintMappedToVendor
	 * 
	 * @param userPartnerID
	 * @param pid
	 * @return
	 */
	public boolean recipeintMappedToVendor(String vpid, String cpid) {
		boolean flag = false;
		int count = jdbcTemplate.queryForObject(SQLQueries.IS_VENDOR_MAPPED_CUSTOMER, new Object[] { cpid, vpid },
				Integer.class);
		if (count > 0)
			flag = true;
		return flag;
	}

	public int acknowlegeInkreta(String status, String docSourceRefId) {
		int i = 0;
		this.jdbcTemplate.update("update einvoicing.items_exchange_queue set status = ? where doc_source_ref_id = ?",
				status, docSourceRefId);
		if (status.equalsIgnoreCase("Acknowledged")) {
			i = this.jdbcTemplate.update(
					"update einvoicing.invoice_document_details set invoice_status = 'Sent' where doc_source_ref_id = ?",
					status, docSourceRefId);
		}

		return i;
	}

	public String fileUpload(DocUploadRequest request) {
		ResponseEntity<CustomeResponseEntity> response = null;
		String methodname = "productsFolderCreate";
		try {

			response = restTemplate.postForEntity(env.getProperty("document.upload.url"), request,
					CustomeResponseEntity.class);

			String fileResp = (String) response.getBody().getResults();
			return fileResp;
		} catch (Exception e) {
		}
		return null;
	}

	public String invoiceFolderCreate(String partnerId) {

		CreateFolderRequest folderRequest = new CreateFolderRequest();
		folderRequest.setPartnerId(partnerId);
		ResponseEntity<CustomeResponseEntity> response = null;
		String methodname = "callcreateFolder";
		try {

			response = restTemplate.postForEntity(env.getProperty("invoice.folder.create.url"), folderRequest,
					CustomeResponseEntity.class);


			String folderId =  (String) response.getBody().getResults();
			return folderId;

		} catch (Exception e) {
			return null;
		}

	}
	
	
	public FolderObjects osFolderCreateForInvoice(String partnerId,String documentRefId) {
		Path directories =null;
		FolderObjects fo=null;
		try {
		
		 	String rootFolder=env.getProperty("os.folder.invoices");
		 	String folderSeperator=env.getProperty("os.file.seperator");
		 
		 	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();        
			String dateToStr = dateFormat.format(date);
			System.out.println("Folder rootFolder is "+ rootFolder);
			System.out.println("Folder Date is "+ dateToStr);
		 
			Path path = Paths.get(rootFolder+folderSeperator+partnerId+folderSeperator+dateToStr+folderSeperator+documentRefId);
			//Path path= Paths.get("d:\\inkretainvoices\\V_10031\\20210922\\I210922000073");
								       
	       
	        if (!Files.exists(path)) {
	        
	        	fo=new FolderObjects();
	        	fo.setFolder_id(UUID.randomUUID().toString());
	        	
	            System.out.println("directories created: " + directories);
	            directories =Files.createDirectories(path);
	            fo.setFolderPath(directories.toString());
	            fo.setFolder_type("Invoice");
	            fo.setCreated_on(new Timestamp(new Date().getTime()));
	            System.out.println("directories created: " + directories);
	            folderObjectsRepo.save(fo);
	        } else {
	        	directories=path;
	        	List<FolderObjects> folderObjects=folderObjectsRepo.findByFolderPath(directories.toString());
	        	fo=folderObjects.get(0);
	            System.out.println("Directory already exists");
	            System.out.println("Folder ID = " + fo.getFolder_id());
	        }
	        
		}
		catch (Exception e) {
			System.out.println("Error in osFolderCreateForInvoice " + e.getMessage());
			e.printStackTrace();
		}

		return 	fo;	

	}

}
