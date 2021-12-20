package com.tecnics.einvoice.constants;

public class SQLQueries {
	
	public static final String GET_USERID="select user_id  from einvoicing.user_management where user_id =? or user_alias =? or email =? or primary_phone_number =?";
	public  static final String LOGIN_QUERY = "select count(*) from einvoicing.user_authorization_keys where user_id = ? and  authorization_key = ?";
    public  static final  String GET_USER_DETAILS = "select um.user_id , um.email  , um.location , STRING_AGG(ur.user_role, ',' ORDER BY ur.user_role), um.first_name, um.last_name,pt.company_name ,um.partner_id, pt.partner_type from einvoicing.user_management um inner join einvoicing.user_roles ur on um.user_id = ur.user_id  inner join einvoicing.partner_details pt on um.partner_id = pt.partner_id  where um.user_id = ? group by um.user_id , um.email  , um.location,pt.company_name,pt.partner_type";
    public static final  String LOG_USER_SESSION = "INSERT INTO einvoicing.user_login_sessions (user_id, security_token, refresh_token ,client_ip_address,last_accessed_on) VALUES(?, ?,?,?,?)";
    public static final  String GET_REFRESH_TOKEN = "select refresh_token from einvoicing.user_login_sessions where security_token = ?";
    public static  final String UPDATE_REFRESH_TOKEN  = "UPDATE einvoicing.user_login_sessions SET  security_token = ?, refresh_token = ?, last_accessed_on = ?";
    public static final  String GET_PARTNERID_BY_REGID  = "select partner_id  from einvoicing.partner_invitation_details where reg_id =?";
    public static final  String GET_PARTNERID_BY_PID  = "select  transaction_id from einvoicing.partner_profile_activities where partner_id =? ";
    public static final  String UPDATE_INVITATION_DTLS="UPDATE einvoicing.partner_invitation_details SET  status = ? where reg_id=?";
    public static  final  String GET_PARTNERID_PARTNERDETAILS = "select partner_id from einvoicing.user_management where user_id =?";
	public static final   String GET_ALL_VENDORS = "SELECT partner_id,company_name FROM einvoicing.partner_details where status ='Active' and partner_type='Vendor'";
	public static  final  String GET_VENDORS_LIKE_NAME = "SELECT partner_id,company_name FROM einvoicing.partner_details where status ='Active' and partner_type='Vendor' and company_name like ?";
	//For Base Controller Service
    public static final String CHECK_ROLE = "select user_role from  einvoicing.user_roles where user_id =?";
    public static final String GET_MY_RECIPEINTS="select pd.company_name,pd.pan_no ,pd.country,pd.nature_of_business,pd.partner_id,vm.vendor_partner_id,rm.recipient_id from einvoicing.partner_details pd inner join einvoicing.vendor_mapping vm on pd.partner_id = vm.customer_partner_id inner join einvoicing.recipient_mapping rm on pd.partner_id = rm.partner_id where vm.vendor_partner_id = ?";
	public static final String GET_GSTN_DETAILS = "select  gstin,business_name from einvoicing.company_gstin_details cgd where partner_id =?";
	public static final	String FIND_UNPAID_INV_FROM_VENDOR = "select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.customer_partner_id,pd.company_name, coalesce(ispd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate from einvoicing.invoice_document_details idd left join einvoicing.invoice_seller_payment_details ispd on idd.document_ref_id=ispd.document_ref_id left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id where idd.vendor_partner_id =? and idd.invoice_status not in ('Paid') order by invoiceduedate asc";
	public static final	String FIND_PAID_INV_FROM_VENDOR = "select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.customer_partner_id,pd.company_name, coalesce(ispd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate from einvoicing.invoice_document_details idd left join einvoicing.invoice_seller_payment_details ispd on idd.document_ref_id=ispd.document_ref_id left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id where idd.vendor_partner_id =? and idd.invoice_status in ('Paid') order by invoiceduedate asc";
	public static final	String FIND_UNPAID_INV_FROM_CUSTOMER = "select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(ispd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, ird.po_ref_num as ponumber from einvoicing.invoice_document_details idd left join einvoicing.invoice_seller_payment_details ispd on idd.document_ref_id=ispd.document_ref_id left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id left join einvoicing.invoice_reference_details ird on idd.document_ref_id=ird.document_ref_id "
			+ " where idd.customer_partner_id =? and idd.invoice_status not in ('Paid', 'Exception') order by invoiceduedate asc";
	
	public static final	String FIND_PAID_INV_FROM_CUSTOMER = "select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(ispd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate from einvoicing.invoice_document_details idd left join einvoicing.invoice_seller_payment_details ispd on idd.document_ref_id=ispd.document_ref_id left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id where idd.customer_partner_id =? and idd.invoice_status='Paid' order by invoiceduedate asc";
	
	public static final	String FIND_EXCEPTIONS_INV_FROM_CUSTOMER = "select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(ispd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate from einvoicing.invoice_document_details idd left join einvoicing.invoice_seller_payment_details ispd on idd.document_ref_id=ispd.document_ref_id left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id where idd.customer_partner_id =? and idd.invoice_status='Exception' order by invoiceduedate asc";


//	public static final String GET_RECGSTIN_DETAILS=" SELECT einvoicing.recipient_mapping.recipient_id,einvoicing.recipient_gstin_mapping.gstin , einvoicing.recipient_mapping.recipient_tag ,einvoicing.recipient_mapping.description,einvoicing.recipient_mapping.delivery_mode ,einvoicing.recipient_mapping.is_active \r\n" + "FROM einvoicing.recipient_mapping\r\n" + "INNER join einvoicing.recipient_gstin_mapping ON einvoicing.recipient_mapping.recipient_id = einvoicing.recipient_gstin_mapping.recipient_id";
//	public static final String GET_RECGSTIN_DETAILS="select einvoicing.recipient_mapping.recipient_id,einvoicing.recipient_gstin_mapping.gstin , einvoicing.recipient_mapping.recipient_tag ,einvoicing.recipient_mapping.description,einvoicing.recipient_mapping.delivery_mode ,einvoicing.recipient_mapping.is_active \r\n" + "FROM einvoicing.recipient_mapping\r\n" + "INNER join einvoicing.recipient_gstin_mapping ON einvoicing.recipient_mapping.recipient_id = einvoicing.recipient_gstin_mapping.recipient_id";
	public static final String GET_RECGSTIN_DETAILS="SELECT *\r\n" + 
			"FROM einvoicing.recipient_mapping rm ,einvoicing.recipient_gstin_mapping rgm \r\n" + 
			"WHERE rm.recipient_id = rgm.recipient_id  order by rm.recipient_id ";
	
	public static final String GET_INVOICE_DETAILS = "select * from einvoicing.invoice_document_details idd where idd.document_ref_id =?";
	public static final String GET_SELLER_DETAILS ="select * from einvoicing.invoice_seller_payment_details idd where idd.document_ref_id =?";
	public static final String GET_SUPPLIER_BUYER_DETAILS = "select * from einvoicing.invoice_supplier_buyer_details idd where idd.document_ref_id =?";
	public static final String GET_EWAYBILL_DETAILS ="select * from einvoicing.invoice_eway_bill_details idd where idd.document_ref_id =?";
	public static final String GET_INVOICE_REFERENCE_DETAILS ="select * from einvoicing.invoice_reference_details idd where idd.document_ref_id =?";
	public static final String GET_DISPATCH_DETAILS = "select * from einvoicing.invoice_dispatch_shipto_details idd where idd.document_ref_id =?";
	public static final String GET_INV_LINEITEMS = "select * from einvoicing.invoice_item_list idd where idd.document_ref_id =?";
	public static final String GET_INV_ATTACHMENT_DTLS ="select * from einvoicing.document_objects idd where idd.ref_id =?";
	public static final String GET_DOC_SRC_REF_ID_LIST = "select ref_id  from einvoicing.items_exchange_queue ieq where ieq.status = 'Queued' and ieq.queue_name = 'INVOICE' and delivery_mechanism='PULL' and ieq.recipient_id = ?";
	
	public static final String GET_DOCREFIDSBYREFID ="select  document_ref_id  from einvoicing.invoice_document_details idd where doc_source_ref_id =?  and customer_recipient_code =?";
	public static final String GET_RECIPIENTID_BY_PARTNERID = "select recipient_id from einvoicing.recipient_mapping rm where rm.partner_id =?";
	public static final String UPDATE_QUEUE_STATUS = "update einvoicing.items_exchange_queue set status ='Pending Ack' where recipient_id =? and ref_id =?";
	public static final String GET_PARTNERID_BY_RECIPIENTID = "select partner_id  from einvoicing.recipient_mapping rm where rm.recipient_id =?";
	public static final String IS_VENDOR_MAPPED_CUSTOMER = "select count (*) from einvoicing.vendor_mapping vm where customer_partner_id = ? and vendor_partner_id = ?";
	
	public static final String GET_INVOICE_QUERIES_BY_DOCUMENTREFID="SELECT id,query_ref_id, document_ref_id, query_type, query_text, query_from, created_by, CONCAT(first_name,' ',last_name) as createdbydisplayname,created_date, is_dispatched, dispatch_mode, dispatched_on, parent_query_ref_id, subject FROM einvoicing.invoice_queries iq JOIN einvoicing.user_management um on iq.created_by=um.user_id where document_ref_id=? order by query_ref_id asc";
}


