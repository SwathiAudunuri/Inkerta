package com.tecnics.einvoice.constants;

public class SQLQueries {
	
	public static final String GET_USERID="select user_id  from einvoicing.user_management where user_id =? or user_alias =?"; // or email =? or primary_phone_number =?";
	public  static final String LOGIN_QUERY = "select count(0) from einvoicing.user_authorization_keys where user_id = ? and  authorization_key = ?";

    public  static final  String GET_USER_DETAILS = "select um.user_id , um.user_alias, um.email  , um.location ,"
    		+ " STRING_AGG(ur.user_role, ',' ORDER BY ur.user_role), um.first_name, um.last_name,pt.company_name ,um.partner_id,"
    		+ " pt.partner_type, CASE WHEN pt.status='Active' THEN 'TRUE' ELSE 'FALSE' END as isPartnerActive"
    		+ " from einvoicing.user_management um"
    		+ " inner join einvoicing.user_roles ur on um.user_id = ur.user_id"
    		+ "  inner join einvoicing.partner_details pt on um.partner_id = pt.partner_id"
    		+ "  where um.user_id = ? group by um.user_id , um.email  , um.location,pt.company_name,pt.partner_type, isPartnerActive";
    public  static final  String GET_USER_INFO = "select um.user_id , um.user_alias, um.email  , um.location , STRING_AGG(ur.user_role, ',' ORDER BY ur.user_role) as roles, um.first_name, um.last_name,pt.company_name ,um.partner_id, pt.partner_type from einvoicing.user_management um inner join einvoicing.user_roles ur on um.user_id = ur.user_id  inner join einvoicing.partner_details pt on um.partner_id = pt.partner_id  where um.user_id = ? group by um.user_id , um.email  , um.location,pt.company_name,pt.partner_type";
    
    public  static final  String GET_USERS_FOR_FORWARD = "select um.user_id , CONCAT(um.first_name,' ',um.last_name) as displayName, "
    		+ "STRING_AGG(pr.role_name, ',' ORDER BY pr.role_name) as roles, um.partner_id "
    		+ "from einvoicing.user_management um  "
    		+ "inner join einvoicing.partner_roles pr on um.partner_id = pr.partner_id "
    		+ "inner join einvoicing.partner_user_roles pur on pr.role_id = pur.role_id and um.user_id = pur.user_id "
    		+ "where um.partner_id =?1 and um.user_id !=?2 group by um.user_id ";
    
    public static final String GET_USERS_BY_PARTNER="select um.user_id as userId, CONCAT(um.first_name,' ',um.last_name) as displayName, um.user_alias as useralias,um.is_Locked, " + 
    		"partnerroles.roles as roles, um.partner_id , um.email " + 
    		"from einvoicing.user_management um   " + 
    		"left join  " + 
    		"(select pr.partner_id, pur.user_id,STRING_AGG(pr.role_name, ',' ORDER BY pr.role_name) as roles " + 
    		"from einvoicing.partner_roles pr " + 
    		"left join einvoicing.partner_user_roles pur on pr.role_id = pur.role_id " + 
    		"where pr.partner_id =?1 " + 
    		"group by pr.partner_id, pur.user_id) as partnerroles on um.user_id=partnerroles.user_id " + 
    		"where um.partner_id =?1";
    
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
	//public static final	String FIND_UNPAID_INV_FROM_VENDOR = "select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,idd.customer_partner_id,pd.company_name, coalesce(ispd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate from einvoicing.invoice_document_details idd left join einvoicing.invoice_seller_payment_details ispd on idd.document_ref_id=ispd.document_ref_id left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id where idd.vendor_partner_id =? and idd.invoice_status not in ('Paid','Queried') order by invoiceduedate asc";
	
	public static final String FIND_UNPAID_INV_FROM_VENDOR ="select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status, " + 
			"idd.document_ref_id,pd.company_name, coalesce(idd.creditdays,0) as creditdays, " + 
			"date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate , idd.invoice_currency_code, false as is_external " + 
			"from einvoicing.invoice_document_details idd " + 
			"left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id " + 
			"where idd.vendor_partner_id =? and idd.invoice_status not in ('Paid','Queried') " + 
			"UNION ALL " + 
			"select eidd.invoice_status,eidd.invoicenum,eidd.invoicedate,eidd.total_invoice_value,eidd.status, " + 
			"eidd.document_ref_id,eidd.recipient_company_name as company_name,  " + 
			"coalesce(eidd.creditdays,0) as creditdays, " + 
			"date(eidd.invoicedate + interval '1' day * creditdays) as invoiceduedate , eidd.invoice_currency_code , true as is_external " + 
			"from einvoicing.external_invoice_document_details eidd where partner_id=? and transaction_type='receivable' and " + 
			"invoice_status not in ('Paid','Queried') order by invoiceduedate asc";
	/* public static final	String FIND_PAID_INV_FROM_VENDOR = "select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,"
			+ "idd.document_ref_id,idd.customer_partner_id,pd.company_name, coalesce(idd.creditdays,0) as creditdays, "
			+ "date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate from einvoicing.invoice_document_details idd left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id where idd.vendor_partner_id =? and idd.invoice_status in ('Paid') order by invoiceduedate asc"; */
	public static final String FIND_PAID_INV_FROM_VENDOR ="select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status, " + 
			"idd.document_ref_id,pd.company_name, coalesce(idd.creditdays,0) as creditdays, " + 
			"date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate , idd.invoice_currency_code, false as is_external " + 
			"from einvoicing.invoice_document_details idd " + 
			"left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id " + 
			"where idd.vendor_partner_id =? and idd.invoice_status in ('Paid') " + 
			"UNION ALL " + 
			"select eidd.invoice_status,eidd.invoicenum,eidd.invoicedate,eidd.total_invoice_value,eidd.status, " + 
			"eidd.document_ref_id,eidd.recipient_company_name as company_name,  " + 
			"coalesce(eidd.creditdays,0) as creditdays, " + 
			"date(eidd.invoicedate + interval '1' day * creditdays) as invoiceduedate , eidd.invoice_currency_code , true as is_external " + 
			"from einvoicing.external_invoice_document_details eidd where partner_id=? and transaction_type='receivable' and " + 
			"invoice_status in ('Paid') order by invoiceduedate asc";
	public static final	String FIND_QUERIED_INV_FROM_VENDOR = "select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.customer_partner_id,pd.company_name, coalesce(idd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate from einvoicing.invoice_document_details idd left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id where idd.vendor_partner_id =? and idd.invoice_status in ('Queried') order by invoiceduedate asc";

	/*public static final	String FIND_UNPAID_INV_FROM_CUSTOMER_FOR_USER = "select idd.irn,idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(ispd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, ird.po_ref_num as ponumber, isbd.supplier_gstin, isbd.billing_gstin, um.first_name as assigned_to, idd.invoice_currency_code"
			+ " from einvoicing.invoice_document_details idd"
			+ " left join einvoicing.invoice_seller_payment_details ispd on idd.document_ref_id=ispd.document_ref_id"
			+ " left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id"
			+ " left join einvoicing.invoice_reference_details ird on idd.document_ref_id=ird.document_ref_id "
			+ " left join einvoicing.invoice_supplier_buyer_details isbd on idd.document_ref_id=isbd.document_ref_id"
			+ " where idd.customer_partner_id =? and idd.invoice_status not in ('Paid', 'Exception','Queried') and idd.status not in ('Draft') "
			+ " and ((idd.assigned_to = '' OR idd.assigned_to IS NULL)) order by invoiceduedate asc";
			*/
	public static final String FIND_UNPAID_INV_FROM_CUSTOMER_FOR_USER="select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status, " + 
			"idd.document_ref_id,pd.company_name, " + 
			"coalesce(idd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, " + 
			" idd.assigned_to, idd.invoice_currency_code, false as is_external " + 
			"from einvoicing.invoice_document_details idd " + 
			"left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id " + 
			"where idd.customer_partner_id =? and idd.invoice_status not in ('Paid', 'Exception','Queried') and idd.status not in ('Draft') " + 
			"and ((idd.assigned_to = '' OR idd.assigned_to IS NULL)) " + 
			"UNION ALL " + 
			"select eidd.invoice_status,eidd.invoicenum,eidd.invoicedate,eidd.total_invoice_value,eidd.status,  " + 
			"eidd.document_ref_id,eidd.recipient_company_name as company_name,  " + 
			"coalesce(eidd.creditdays,0) as creditdays, " + 
			"date(eidd.invoicedate + interval '1' day * creditdays) as invoiceduedate ,  eidd.assigned_to, eidd.invoice_currency_code, true as is_external " + 
			"from einvoicing.external_invoice_document_details eidd where partner_id=? and transaction_type='payable' and " + 
			"invoice_status not in ('Paid','Queried') and  (eidd.assigned_to = '' OR eidd.assigned_to IS NULL) " + 
			"order by invoiceduedate asc";
	
	/*public static final	String FIND_UNPAID_INV_FROM_CUSTOMER_FOR_MANAGER = "select idd.irn,idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(ispd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, ird.po_ref_num as ponumber, isbd.supplier_gstin, isbd.billing_gstin, um.first_name as assigned_to, idd.invoice_currency_code"
			+ " from einvoicing.invoice_document_details idd"
			+ " left join einvoicing.invoice_seller_payment_details ispd on idd.document_ref_id=ispd.document_ref_id"
			+ " left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id"
			+ " left join einvoicing.invoice_reference_details ird on idd.document_ref_id=ird.document_ref_id "
			+ " left join einvoicing.invoice_supplier_buyer_details isbd on idd.document_ref_id=isbd.document_ref_id"
			+ " left join einvoicing.user_management um on idd.assigned_to=um.user_id"
			+ " where idd.customer_partner_id =? and idd.invoice_status not in ('Paid', 'Exception','Queried') and idd.status not in ('Draft') "
			+ "  order by invoiceduedate asc";	*/
	
	public static final String FIND_UNPAID_INV_FROM_CUSTOMER_FOR_MANAGER="select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status, " + 
			"idd.document_ref_id,pd.company_name, " + 
			"coalesce(idd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, " + 
			" um.first_name as assigned_to, idd.invoice_currency_code, false as is_external " + 
			"from einvoicing.invoice_document_details idd " + 
			"left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id " + 
			"left join einvoicing.user_management um on idd.assigned_to=um.user_id "+
			"where idd.customer_partner_id =? and idd.invoice_status not in ('Paid', 'Exception','Queried') and idd.status not in ('Draft') " + 
			"UNION ALL " + 
			"select eidd.invoice_status,eidd.invoicenum,eidd.invoicedate,eidd.total_invoice_value,eidd.status,  " + 
			"eidd.document_ref_id,eidd.recipient_company_name as company_name,  " + 
			"coalesce(eidd.creditdays,0) as creditdays, " + 
			"date(eidd.invoicedate + interval '1' day * creditdays) as invoiceduedate ,  um.first_name as assigned_to, eidd.invoice_currency_code, true as is_external " + 
			"from einvoicing.external_invoice_document_details eidd " +
			"left join einvoicing.user_management um on eidd.assigned_to=um.user_id " +
			 "where eidd.partner_id=? and transaction_type='payable' and " + 
			"invoice_status not in ('Paid','Queried') order by invoiceduedate asc";
	
	
	
	/*public static final	String FIND_ASSIGNED_TO_ME_INV_FROM_CUSTOMER = "select idd.irn,idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(ispd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, ird.po_ref_num as ponumber, isbd.supplier_gstin, isbd.billing_gstin, idd.invoice_currency_code"
			+ " from einvoicing.invoice_document_details idd"
			+ " left join einvoicing.invoice_seller_payment_details ispd on idd.document_ref_id=ispd.document_ref_id"
			+ " left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id"
			+ " left join einvoicing.invoice_reference_details ird on idd.document_ref_id=ird.document_ref_id "
			+ " left join einvoicing.invoice_supplier_buyer_details isbd on idd.document_ref_id=isbd.document_ref_id"
			+ " where idd.customer_partner_id =? and (idd.assigned_to =?) order by invoiceduedate asc";*/
	
	public static final	String FIND_ASSIGNED_TO_ME_INV_FROM_CUSTOMER = "select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status, " + 
	"idd.document_ref_id,pd.company_name, coalesce(idd.creditdays,0) as creditdays, " + 
	"date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, " + 
	"idd.invoice_currency_code, false as is_external " + 
	"from einvoicing.invoice_document_details idd " + 
	"left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id " + 
	"left join einvoicing.invoice_reference_details ird on idd.document_ref_id=ird.document_ref_id " + 
	"left join einvoicing.invoice_supplier_buyer_details isbd on idd.document_ref_id=isbd.document_ref_id " + 
	"where idd.customer_partner_id =? and (idd.assigned_to =?) " + 
	"UNION ALL " + 
	"select eidd.invoice_status,eidd.invoicenum,eidd.invoicedate,eidd.total_invoice_value,eidd.status, " + 
	"eidd.document_ref_id,eidd.recipient_company_name as company_name, " + 
	"coalesce(eidd.creditdays,0) as creditdays, " + 
	"date(eidd.invoicedate + interval '1' day * creditdays) as invoiceduedate ,  eidd.invoice_currency_code, true as is_external " + 
	"from einvoicing.external_invoice_document_details eidd " + 
	"left join einvoicing.user_management um on eidd.assigned_to=um.user_id " + 
	"where eidd.partner_id=? and transaction_type='payable' and  " + 
	"(eidd.assigned_to =?)  order by invoiceduedate asc ";
	
/*	public static final	String FIND_PAID_INV_FROM_CUSTOMER_FOR_USER = "select idd.irn,idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(ispd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, ird.po_ref_num as ponumber, isbd.supplier_gstin, isbd.billing_gstin, idd.assigned_to, idd.invoice_currency_code"
			+ " from einvoicing.invoice_document_details idd"
			+ " left join einvoicing.invoice_seller_payment_details ispd on idd.document_ref_id=ispd.document_ref_id"
			+ " left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id"
			+ " left join einvoicing.invoice_reference_details ird on idd.document_ref_id=ird.document_ref_id "
			+ " left join einvoicing.invoice_supplier_buyer_details isbd on idd.document_ref_id=isbd.document_ref_id"
			+ " where idd.customer_partner_id =? and idd.invoice_status='Paid' and idd.status not in ('Draft') "
			+ " and ((idd.assigned_to = '' OR idd.assigned_to IS NULL)) order by invoiceduedate asc"; */
	
/*	public static final	String FIND_PAID_INV_FROM_CUSTOMER_FOR_MANAGER = "select idd.irn,idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(ispd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, ird.po_ref_num as ponumber, isbd.supplier_gstin, isbd.billing_gstin, um.first_name as assigned_to, idd.invoice_currency_code"
			+ " from einvoicing.invoice_document_details idd"
			+ " left join einvoicing.invoice_seller_payment_details ispd on idd.document_ref_id=ispd.document_ref_id"
			+ " left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id"
			+ " left join einvoicing.invoice_reference_details ird on idd.document_ref_id=ird.document_ref_id "
			+ " left join einvoicing.invoice_supplier_buyer_details isbd on idd.document_ref_id=isbd.document_ref_id"
			+ " left join einvoicing.user_management um on idd.assigned_to=um.user_id"
			+ " where idd.customer_partner_id =? and idd.invoice_status='Paid' and idd.status not in ('Draft') "
			+ "  order by invoiceduedate asc"; */
	
	public static final	String FIND_PAID_INV_FROM_CUSTOMER_FOR_USER=	"select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,  " + 
			"			idd.document_ref_id,pd.company_name,  " + 
			"			coalesce(idd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate,  " + 
			"			 um.first_name as assigned_to, idd.invoice_currency_code, false as is_external  " + 
			"			from einvoicing.invoice_document_details idd  " + 
			"			left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id  " + 
			"			left join einvoicing.user_management um on idd.assigned_to=um.user_id " + 
			"			where idd.customer_partner_id =? and idd.invoice_status ='Paid' and idd.status not in ('Draft')  " + 
			"			UNION ALL  " + 
			"			select eidd.invoice_status,eidd.invoicenum,eidd.invoicedate,eidd.total_invoice_value,eidd.status,   " + 
			"			eidd.document_ref_id,eidd.recipient_company_name as company_name,   " + 
			"			coalesce(eidd.creditdays,0) as creditdays,  " + 
			"			date(eidd.invoicedate + interval '1' day * creditdays) as invoiceduedate ,  um.first_name as assigned_to, eidd.invoice_currency_code, true as is_external  " + 
			"			from einvoicing.external_invoice_document_details eidd " + 
			"			left join einvoicing.user_management um on eidd.assigned_to=um.user_id " + 
			"			 where eidd.partner_id=? and transaction_type='payable' and  " + 
			"			invoice_status ='Paid' order by invoiceduedate asc";
	
	public static final	String FIND_PAID_INV_FROM_CUSTOMER_FOR_MANAGER=	"select idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,  " + 
	"			idd.document_ref_id,pd.company_name,  " + 
	"			coalesce(idd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate,  " + 
	"			 um.first_name as assigned_to, idd.invoice_currency_code, false as is_external  " + 
	"			from einvoicing.invoice_document_details idd  " + 
	"			left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id  " + 
	"			left join einvoicing.user_management um on idd.assigned_to=um.user_id " + 
	"			where idd.customer_partner_id =? and idd.invoice_status ='Paid' and idd.status not in ('Draft')  " + 
	"			UNION ALL  " + 
	"			select eidd.invoice_status,eidd.invoicenum,eidd.invoicedate,eidd.total_invoice_value,eidd.status,   " + 
	"			eidd.document_ref_id,eidd.recipient_company_name as company_name,   " + 
	"			coalesce(eidd.creditdays,0) as creditdays,  " + 
	"			date(eidd.invoicedate + interval '1' day * creditdays) as invoiceduedate ,  um.first_name as assigned_to, eidd.invoice_currency_code, true as is_external  " + 
	"			from einvoicing.external_invoice_document_details eidd " + 
	"			left join einvoicing.user_management um on eidd.assigned_to=um.user_id " + 
	"			 where eidd.partner_id=? and transaction_type='payable' and  " + 
	"			invoice_status ='Paid' order by invoiceduedate asc";
	
	public static final	String FIND_QUERIED_INV_FROM_CUSTOMER_FOR_USER = "select idd.irn,idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(idd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, ird.po_ref_num as ponumber, isbd.supplier_gstin, isbd.billing_gstin, idd.assigned_to, idd.invoice_currency_code"
			+ " from einvoicing.invoice_document_details idd"
			+ " left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id"
			+ " left join einvoicing.invoice_reference_details ird on idd.document_ref_id=ird.document_ref_id "
			+ " left join einvoicing.invoice_supplier_buyer_details isbd on idd.document_ref_id=isbd.document_ref_id"
			+ " where idd.customer_partner_id =? and idd.invoice_status='Queried' and idd.status not in ('Draft') "
			+ " and ((idd.assigned_to = '' OR idd.assigned_to IS NULL)) order by invoiceduedate asc";
	
	public static final	String FIND_QUERIED_INV_FROM_CUSTOMER_FOR_MANAGER = "select idd.irn,idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(idd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, ird.po_ref_num as ponumber, isbd.supplier_gstin, isbd.billing_gstin, um.first_name as assigned_to, idd.invoice_currency_code"
			+ " from einvoicing.invoice_document_details idd"
			+ " left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id"
			+ " left join einvoicing.invoice_reference_details ird on idd.document_ref_id=ird.document_ref_id "
			+ " left join einvoicing.invoice_supplier_buyer_details isbd on idd.document_ref_id=isbd.document_ref_id"
			+ " left join einvoicing.user_management um on idd.assigned_to=um.user_id"
			+ " where idd.customer_partner_id =? and idd.invoice_status='Queried' and idd.status not in ('Draft') "
			+ " order by invoiceduedate asc";
	
	public static final	String FIND_EXCEPTIONS_INV_FROM_CUSTOMER_FOR_USER = "select idd.irn,idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(idd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, ird.po_ref_num as ponumber, isbd.supplier_gstin, isbd.billing_gstin, idd.assigned_to, idd.invoice_currency_code"
			+ " from einvoicing.invoice_document_details idd"
			+ " left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id"
			+ " left join einvoicing.invoice_reference_details ird on idd.document_ref_id=ird.document_ref_id "
			+ " left join einvoicing.invoice_supplier_buyer_details isbd on idd.document_ref_id=isbd.document_ref_id"
			+ " where idd.customer_partner_id =? and idd.invoice_status='Exception' and idd.status not in ('Draft') "
			+ " and ((idd.assigned_to = '' OR idd.assigned_to IS NULL)) order by invoiceduedate asc";
	
	public static final	String FIND_EXCEPTIONS_INV_FROM_CUSTOMER_FOR_MANAGER = "select idd.irn,idd.invoice_status,idd.invoicenum,idd.invoicedate,idd.total_invoice_value,idd.status,idd.supply_type_code,idd.document_ref_id,idd.vendor_partner_id,pd.company_name, coalesce(idd.creditdays,0) as creditdays, date(idd.invoicedate + interval '1' day * creditdays) as invoiceduedate, ird.po_ref_num as ponumber, isbd.supplier_gstin, isbd.billing_gstin, um.first_name as assigned_to, idd.invoice_currency_code "
			+ " from einvoicing.invoice_document_details idd"
			+ " left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id"
			+ " left join einvoicing.invoice_reference_details ird on idd.document_ref_id=ird.document_ref_id "
			+ " left join einvoicing.invoice_supplier_buyer_details isbd on idd.document_ref_id=isbd.document_ref_id"
			+ " left join einvoicing.user_management um on idd.assigned_to=um.user_id"
			+ " where idd.customer_partner_id =? and idd.invoice_status='Exception' and idd.status not in ('Draft') "
			+ "  order by invoiceduedate asc";
	
	public static final String FETCH_CUSTOM_ACTIONS_BY_PARTNERID = "select action_id,action_name,created_date,action_connector_type,"
			+ "CONCAT(um.first_name,' ',um.last_name) as createdByDisplayName,routing_component from einvoicing.custom_actions ca "
			+ "inner join einvoicing.user_management um on ca.created_by = um.user_id where ca.partner_id =?1";
	

	
	public static final String FETCH_CUSTOM_ACTIONS_BY_INVOICESTATUS_PARTNERROLES = "select ca.action_id,ca.action_name, ca.created_date,ca.action_connector_type,"
			+ "routing_component "
			+ "from einvoicing.custom_actions ca "
			+ "inner join einvoicing.user_management um on ca.created_by = um.user_id "
			+ "inner join einvoicing.custom_actions_by_invoice_status cabis on ca.action_id = cabis.action_id "
			+ "inner join einvoicing.custom_actions_by_partner_role cabpr on ca.action_id = cabpr.action_id "
			+ "where ca.partner_id =?1 and cabis.invoice_status =?2 and cabpr.role_name in (?3) "
			+ "group by ca.action_id,ca.action_name order by ca.action_name ";

	
	public static final String FETCH_PARTNER_ASSIGNED_ROLES_BY_USERID= "select pur.id,pur.user_id, pur.role_id,pr.role_name, pr.role_description, pur.created_date, pur.created_by "
			+ "from einvoicing.partner_user_roles pur "
			+ "inner join einvoicing.partner_roles pr on pur.role_id = pr.role_id "
			+ "where pur.user_id =?1 order by pr.role_name ";
	public static final String FETCH_PARTNER_UNASSIGNED_ROLES_FOR_USERID="SELECT  pr.role_id,pr.role_name,pr.role_description "
			+ "FROM    einvoicing.partner_roles pr where pr.partner_id=?1 and pr.role_id not in "
			+ "(select pur.role_id from einvoicing.partner_user_roles pur where pur.user_id =?2)";
	
	public static final String FETCH_PARTNER_ROLES = "select role_name from einvoicing.partner_roles where partner_id =?";
	public static final String FETCH_INVOICE_STATUSES = " select distinct(status_value) as invoicestatus from einvoicing.invoice_status"
			+ "	where partner_type='Customer'";
	// For Invoice Status Tracker specific to Vendor or Partner
	
	public static final String FETCH_PARTNERS_LIST = "select pd.partner_id, pd.company_name, pd.partner_type,pd.status,pd.onboarded_on,"
			+ " CONCAT(pcd.first_name,' ',pcd.last_name) as contactName, pcd.primary_phone_number"
			+ " from einvoicing.partner_details pd"
			+ " left join einvoicing.partner_contact_details pcd on pd.partner_id = pcd.partner_id"
			+ " where pcd.type_of_contact is null or pcd.type_of_contact='Primary'" 
			+ "	order by company_name";
	
	public static final String FETCH_EXTERNAL_PARTNERS_LIST="select partner_id,company_name, partner_type, CONCAT(first_name,' ',last_name) as contactName,primary_phone_number "
			+ " from einvoicing.external_partner_details where partner_to=?1";
			
		
	public static final	String FETCH_INV_STATUSTRACKER = "SELECT invoice_status_tracker_id, document_ref_id, action_type, action, action_comments, action_by, action_date, "
			+ "is_dispatched, dispatch_mode, dispatched_on, source, action_to, visible_to_partnerid, "
			+ "CONCAT(um.first_name,' ',um.last_name) as actionByDisplayName "
			+ "FROM einvoicing.invoice_status_tracker ist inner join einvoicing.user_management um on ist.action_by = um.user_id  "
			+ "where (visible_to_partnerid IS NULL or visible_to_partnerid=?1) and document_ref_id=?2 order by action_date desc";
	
	public static final	String FETCH_EXT_INV_STATUSTRACKER = "SELECT invoice_status_tracker_id, document_ref_id, action_type, action, action_comments, action_by, action_date, "
			+ " action_to, CONCAT(um.first_name,' ',um.last_name) as actionByDisplayName "
			+ "FROM einvoicing.external_invoice_status_tracker eist inner join einvoicing.user_management um on eist.action_by = um.user_id  "
			+ "where document_ref_id=?1 order by action_date desc";
	
	
	// For Invoice Notes Service
	
	public static final	String FETCH_INTERNAL_INV_NOTES = "select ino.notes_id , ino.partner_id  , ino.document_ref_id , ino.notes, ino.notes_type, ino.created_by, CONCAT(um.first_name,' ',um.last_name) as createdbydisplayname, ino.created_on"
			+" from einvoicing.invoice_notes ino "
			+ "inner join einvoicing.user_management um on ino.created_by = um.user_id  "
			+ "where ino.partner_id = ? and ino.document_ref_id = ? order by ino.created_on desc";
	
	// For Invoice Notes Service
	
	public static final	String FETCH_EXTERNAL_INV_NOTES = "select ein.notes_id , ein.partner_id  , ein.document_ref_id , ein.notes, ein.notes_type, ein.created_by, CONCAT(um.first_name,' ',um.last_name) as createdbydisplayname, ein.created_on"
				+" from einvoicing.external_invoice_notes ein "
				+ "inner join einvoicing.user_management um on ein.created_by = um.user_id  "
				+ "where ein.partner_id = ? and ein.document_ref_id = ? order by ein.created_on desc";
	
	public static final String GET_INVOICE_DETAILS = "select * from einvoicing.invoice_document_details idd where idd.document_ref_id =?";
	public static final String GET_EXTERNAL_INVOICE_DETAILS = "select * from einvoicing.external_invoice_document_details idd where idd.document_ref_id =?";
	
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
    public static final String GET_OUTBOUND_CONNECTORS_BY_PARTNERID="SELECT connector_id, partner_id, description, connector_tag, is_active, created_on, created_by, invoiceupload_deliverymode, invoicequery_deliverymode, invoicestatusupdate_deliverymode, autopost_on_verification FROM einvoicing.outbound_connectors where partner_id=? ";
    public static final String GET_OUTBOUND_CONNECTOR_BY_CONNECTORID="SELECT connector_id, partner_id, description, connector_tag, is_active, created_on, created_by, invoiceupload_deliverymode, invoicequery_deliverymode, invoicestatusupdate_deliverymode, autopost_on_verification FROM einvoicing.outbound_connectors where connector_id=? ";
    public static final String GET_FIELD_MASTER_VALUES="select field_name, field_value from einvoicing.field_master where module_name=? and module_key=? order by field_name,field_order asc";
    
    public static final String GET_FIELD_MASTER_VALUES_BY_MODULE="select field_name, field_value from einvoicing.field_master where module_name=? order by field_name,field_order asc";
    
    //public static final String DSHBRD_CUST_OPEN_UNPAIDTOTALS="select count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code from einvoicing.invoice_document_details " + 
    //		"where customer_partner_id=?1 and invoice_status!='Paid' " + 
    //		"group by invoice_currency_code order by totals desc Limit 1 ";
    
    public static final String DSHBRD_CUSTOMER_OPEN_UNPAIDTOTALS=" Select sum(noofinvoices) as noofinvoices, sum(totals) as totals, invoice_currency_code from " + 
    		"(select  count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.invoice_document_details  " + 
    		"where customer_partner_id=? and invoice_status!='Paid' " + 
    		"group by invoice_currency_code " + 
    		"	UNION ALL " + 
    		"select count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.external_invoice_document_details  " + 
    		"where partner_id=? and invoice_status!='Paid' and transaction_type='payable' " + 
    		"group by invoice_currency_code) unpaidtotals group by invoice_currency_code  " + 
    		"order by totals desc Limit 1";
    
    
    
    public static final String DSHBRD_CUSTOMER_OPEN_OVERDUETOTALS="select count(0) as noofinvoices,sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.invoice_document_details idd " + 
    		"where idd.customer_partner_id=?1 and idd.invoice_status!='Paid' and " + 
    		"(date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0))))  " + 
    		"< now() " + 
    		"group by invoice_currency_code order by totals desc Limit 1" ;
    
    public static final String DSHBRD_CUSTOMER_OPEN_01TO30DAYSOVERDUETOTALS="select count(0) as noofinvoices,sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.invoice_document_details idd " + 
    		"where idd.customer_partner_id=?1 and idd.invoice_status!='Paid' and " + 
    		"(date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) " + 
    		"BETWEEN date(now() - INTERVAL '30 DAY') and date(now() - INTERVAL '01 DAY') " + 
    		"group by invoice_currency_code order by totals desc Limit 1" ;
    
    public static final String DSHBRD_CUSTOMER_OPEN_31TO90DAYSOVERDUETOTALS="select count(0) as noofinvoices,sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.invoice_document_details idd " + 
    	"where idd.customer_partner_id=?1 and idd.invoice_status!='Paid' and " + 
    		"(date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) " + 
    		"BETWEEN date(now() - INTERVAL '90 DAY') and date(now() - INTERVAL '31 DAY') " + 
    		"group by invoice_currency_code order by totals desc Limit 1" ;
    
    public static final String DSHBRD_CUSTOMER_OPEN_OVER90DAYSOVERDUETOTALS="select count(0) as noofinvoices,sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.invoice_document_details idd " + 
    		"where idd.customer_partner_id=?1 and idd.invoice_status!='Paid' and " + 
    		"(date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) " + 
    		"> date(now() - INTERVAL '90 DAY') " + 
    		"group by invoice_currency_code order by totals desc Limit 1" ;
    
    
    
    public static final String DSHBRD_VENDOR_OPEN_UNPAIDTOTALS=" Select sum(noofinvoices) as noofinvoices, sum(totals) as totals, invoice_currency_code from " + 
    		"(select  count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.invoice_document_details  " + 
    		"where vendor_partner_id=? and invoice_status!='Paid' " + 
    		"group by invoice_currency_code " + 
    		"	UNION ALL " + 
    		"select count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.external_invoice_document_details  " + 
    		"where partner_id=? and invoice_status!='Paid' and transaction_type='receivable' " + 
    		"group by invoice_currency_code) unpaidtotals group by invoice_currency_code  " + 
    		"order by totals desc Limit 1";
    
    public static final String DSHBRD_VENDOR_OPEN_OVERDUETOTALS=" Select sum(noofinvoices) as noofinvoices, sum(totals) as totals, invoice_currency_code from " + 
    		"    		(select  count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"    		from einvoicing.invoice_document_details idd" + 
    		"     		where vendor_partner_id=? and invoice_status!='Paid' and" + 
    		"    		(date(invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) " + 
    		"    		< now()" + 
    		"    		group by invoice_currency_code " + 
    		"    			UNION ALL " + 
    		"    		select count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"    		from einvoicing.external_invoice_document_details  " + 
    		"    		where partner_id=? and invoice_status!='Paid' and transaction_type='receivable' and " + 
    		"			(date(invoicedate + interval '1' day * (coalesce(creditdays,0)))) < now() " + 
    		"    		group by invoice_currency_code) unpaidtotals group by invoice_currency_code  " + 
    		"    		order by totals desc Limit 1	";
    
    public static final String DSHBRD_VENDOR_OPEN_01TO30DAYSOVERDUETOTALS="Select sum(noofinvoices) as noofinvoices, sum(totals) as totals, invoice_currency_code from " + 
    		"( " + 
    		"select count(0) as noofinvoices,sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.invoice_document_details idd " + 
    		"where idd.vendor_partner_id=? and idd.invoice_status!='Paid' and " + 
    		"(date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) " + 
    		"BETWEEN date(now() - INTERVAL '30 DAY') and date(now() - INTERVAL '01 DAY') " + 
    		"group by invoice_currency_code " + 
    		"	UNION ALL " + 
    		"select count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.external_invoice_document_details eidd " + 
    		"where partner_id=? and invoice_status!='Paid' and transaction_type='receivable' " + 
    		"and " + 
    		"(date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) " + 
    		"BETWEEN date(now() - INTERVAL '30 DAY') and date(now() - INTERVAL '01 DAY') " + 
    		"group by invoice_currency_code " + 
    		") unpaidtotals group by invoice_currency_code order by totals desc Limit 1";
    
    public static final String DSHBRD_VENDOR_OPEN_31TO90DAYSOVERDUETOTALS="Select sum(noofinvoices) as noofinvoices, sum(totals) as totals, invoice_currency_code from " + 
    		"( " + 
    		"select count(0) as noofinvoices,sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.invoice_document_details idd " + 
    		"where idd.vendor_partner_id=? and idd.invoice_status!='Paid' and " + 
    		"(date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) " + 
    		"BETWEEN date(now() - INTERVAL '90 DAY') and date(now() - INTERVAL '31 DAY') " + 
    		"group by invoice_currency_code " + 
    		"	UNION ALL " + 
    		"select count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code " + 
    		"from einvoicing.external_invoice_document_details eidd " + 
    		"where partner_id=? and invoice_status!='Paid' and transaction_type='receivable' " + 
    		"and " + 
    		"(date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) " + 
    		"BETWEEN date(now() - INTERVAL '90 DAY') and date(now() - INTERVAL '31 DAY') " + 
    		"group by invoice_currency_code " + 
    		") unpaidtotals group by invoice_currency_code order by totals desc Limit 1";
    
    public static final String DSHBRD_CUSTOMER_TOP10_PAYABLES_BY_VENDOR="select STRING_AGG (CAST(noofinvoices as Varchar),',') as noofinvoices, STRING_AGG (CAST(totals as Varchar),',') as totals, STRING_AGG (invoice_currency_code,',') as invoice_currency_code ,STRING_AGG (company_name,',') as company_name " + 
    		"from ( " + 
    		"Select sum(noofinvoices) as noofinvoices, sum(totals) as totals, invoice_currency_code, company_name " + 
    		" from ( " + 
    		"	 select  count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code, " + 
    		" pd.company_name as company_name " + 
    		"from einvoicing.invoice_document_details idd " + 
    		"left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id " + 
    		"where idd.customer_partner_id=? and idd.invoice_status!='Paid'  " + 
    		"group by idd.invoice_currency_code , pd.company_name " + 
    		"UNION ALL  " + 
    		"select count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code, " + 
    		" recipient_company_name as company_name " + 
    		"from einvoicing.external_invoice_document_details   " + 
    		"where partner_id=? and invoice_status!='Paid' and transaction_type='payable'  " + 
    		"group by invoice_currency_code,recipient_company_name " + 
    		") 	unpaidtotals group by invoice_currency_code, company_name order by totals desc " + 
    		") TOP10_PAYABLES_BY_VENDOR";
    
    public static final String DSHBRD_VENDOR_TOP10_PAYABLES_BY_CUSTOMER="select STRING_AGG (CAST(noofinvoices as Varchar),',') as noofinvoices, STRING_AGG (CAST(totals as Varchar),',') as totals, STRING_AGG (invoice_currency_code,',') as invoice_currency_code ,STRING_AGG (company_name,',') as company_name " + 
    		"    		from ( " + 
    		"    		Select sum(noofinvoices) as noofinvoices, sum(totals) as totals, invoice_currency_code, company_name  " + 
    		"    		 from (  " + 
    		"    			 select  count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code, " + 
    		"    		 pd.company_name as company_name  " + 
    		"    		from einvoicing.invoice_document_details idd  " + 
    		"    		left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id  " + 
    		"    		where idd.vendor_partner_id=? and idd.invoice_status!='Paid'   " + 
    		"    		group by idd.invoice_currency_code , pd.company_name  " + 
    		"    		UNION ALL   " + 
    		"    		select count(0) as noofinvoices, sum(total_invoice_value) as totals, invoice_currency_code,  " + 
    		"    		 recipient_company_name as company_name  " + 
    		"    		from einvoicing.external_invoice_document_details    " + 
    		"    		where partner_id=? and invoice_status!='Paid' and transaction_type='receivable'   " + 
    		"    		group by invoice_currency_code,recipient_company_name  " + 
    		"    		) 	unpaidtotals group by invoice_currency_code, company_name order by totals desc  " + 
    		"    		) TOP10_PAYABLES_BY_VENDOR";
    
    
  /*  public static final String DSHBRD_ALL_RECEIVABLES_BY_COMPANIES="select cr.system_rating as rating, vendor_company_name, customer_company_name,vendor_partner_id, customer_partner_id, " + 
    		"	noofinvoices,current,oneto30days,thirtyoneto60days,sixtyoneto90days,over90days,balancedue,transaction_type, true as isexternal from ExternalInvoiceDuesByCompany " + 
    		" left join einvoicing.company_rating cr on customer_partner_id=cr.to_partner_id " +
    		"	where vendor_partner_id=? and transaction_type='receivable' " + 
    		"	UNION ALL " + 
    		"		select cr.system_rating as rating, vendor_company_name, customer_company_name,vendor_partner_id, customer_partner_id, " + 
    		"	noofinvoices,current,oneto30days,thirtyoneto60days,sixtyoneto90days,over90days,balancedue, transaction_type,false as isexternal from internalinvoiceduesbycompany " + 
    		" left join einvoicing.company_rating cr on customer_partner_id=cr.to_partner_id " +
    		"	where vendor_partner_id=? order by balancedue desc"; */
    
    public static final String DSHBRD_ALL_CURRENT_RECEIVABLES_BY_COMPANIES="select cr.system_rating as rating, invoice_currency_code,vendor_company_name, customer_company_name,vendor_partner_id, customer_partner_id,    " + 
    		" noofinvoices,current,oneto30days,thirtyoneto60days,sixtyoneto90days,over90days,balancedue,paid_amount,transaction_type, true as isexternal   " + 
    		" from currentExternalInvoicesByCompany    " + 
    		"left join einvoicing.company_rating cr on customer_partner_id=cr.to_partner_id   " + 
    		"where vendor_partner_id=?1 and transaction_type='receivable' and invoice_currency_code=?2  " + 
    		"UNION ALL    " + 
    		"select cr.system_rating as rating,invoice_currency_code, vendor_company_name, customer_company_name,vendor_partner_id, customer_partner_id,    " + 
    		"noofinvoices,current,oneto30days,thirtyoneto60days,sixtyoneto90days,over90days,balancedue, paid_amount,transaction_type,false as isexternal " + 
    		"from currentinternalinvoicesbycompany    " + 
    		"left join einvoicing.company_rating cr on customer_partner_id=cr.to_partner_id   " + 
    		"where vendor_partner_id=?1 and invoice_currency_code=?2 order by balancedue desc";
    
    
    
    public static final String DSHBRD_ALL_CURRENT_PAYABLES_BY_COMPANIES="select cr.system_rating as rating, invoice_currency_code,vendor_company_name, customer_company_name,vendor_partner_id, customer_partner_id,    " + 
    		" noofinvoices,current,oneto30days,thirtyoneto60days,sixtyoneto90days,over90days,balancedue,paid_amount,transaction_type, true as isexternal   " + 
    		" from currentExternalInvoicesByCompany    " + 
    		"left join einvoicing.company_rating cr on customer_partner_id=cr.to_partner_id   " + 
    		"where customer_partner_id=?1 and transaction_type='payable' and invoice_currency_code=?2  " + 
    		"UNION ALL    " + 
    		"select cr.system_rating as rating,invoice_currency_code, vendor_company_name, customer_company_name,vendor_partner_id, customer_partner_id,    " + 
    		"noofinvoices,current,oneto30days,thirtyoneto60days,sixtyoneto90days,over90days,balancedue, paid_amount,transaction_type,false as isexternal " + 
    		"from currentinternalinvoicesbycompany    " + 
    		"left join einvoicing.company_rating cr on vendor_partner_id=cr.to_partner_id   " + 
    		"where customer_partner_id=?1 and invoice_currency_code=?2 order by balancedue desc";
    
 /*   public static final String DSHBRD_ALL_RECEIVABLES_SUMMARY="select sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days, " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue " + 
    		"from ( " + 
    		"select sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days, " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue from ExternalInvoiceDuesByCompany  " + 
    		"    		where vendor_partner_id=? and transaction_type='receivable'  " + 
    		"    		UNION ALL  " + 
    		"select sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days, " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue from internalinvoiceduesbycompany  " + 
    		"    		where vendor_partner_id=?) summary	"; */
    
    public static final String DSHBRD_ALL_RECEIVABLES_SUMMARY ="select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,    " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue, sum(paidamount) as paidamount   " + 
    		"from (    " + 
    		"select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,    " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue, sum(paid_amount) as paidamount   " + 
    		"from currentexternalinvoicesbycompany     " + 
    		"where vendor_partner_id=?1 and transaction_type='receivable' group by invoice_currency_code    " + 
    		"UNION ALL     " + 
    		"select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,    " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue ,  sum(paid_amount) as paidamount   " + 
    		"from currentinternalinvoicesbycompany     " + 
    		"where vendor_partner_id=?1 group by invoice_currency_code ) summary group by invoice_currency_code" + 
    		"";
    
    public static final String DSHBRD_ALL_PAYABLES_SUMMARY="select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,    " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue, sum(paidamount) as paidamount   " + 
    		"from (    " + 
    		"select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,    " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue, sum(paid_amount) as paidamount   " + 
    		"from currentexternalinvoicesbycompany     " + 
    		"where customer_partner_id=?1 and transaction_type='receivable' group by invoice_currency_code    " + 
    		"UNION ALL     " + 
    		"select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,    " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue ,  sum(paid_amount) as paidamount   " + 
    		"from currentinternalinvoicesbycompany     " + 
    		"where customer_partner_id=?1 group by invoice_currency_code ) summary group by invoice_currency_code";
    
    public static final String DSHBRD_ALL_CLOSED_RECEIVABLES_SUMMARY="select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,   " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue, sum(paidamount) as paidamount  " + 
    		"from (   " + 
    		"select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,   " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue, sum(paid_amount) as paidamount  " + 
    		"from closedexternalinvoicesbycompany    " + 
    		"where vendor_partner_id=?1 and transaction_type='receivable' group by invoice_currency_code   " + 
    		"UNION ALL    " + 
    		"select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,   " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue ,  sum(paid_amount) as paidamount  " + 
    		"from closedinternalinvoicesbycompany    " + 
    		"where vendor_partner_id=?1 group by invoice_currency_code ) summary group by invoice_currency_code	";
    
    public static final String DSHBRD_ALL_CLOSED_PAYABLES_SUMMARY="select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,   " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue , sum(paid_amount) as paid_amount  " + 
    		"from (   " + 
    		"select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,   " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue, sum(paid_amount) as paid_amount  " + 
    		"from closedexternalinvoicesbycompany    " + 
    		"where customer_partner_id=?1 and transaction_type='payable' group by invoice_currency_code   " + 
    		"UNION ALL    " + 
    		"select invoice_currency_code,sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days,   " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue ,  sum(paid_amount) as paid_amount  " + 
    		"from closedinternalinvoicesbycompany    " + 
    		"where customer_partner_id=?1 group by invoice_currency_code ) summary group by invoice_currency_code	";
    
    public static final String DSHBRD_ALL_CLOSED_RECEIVABLES_BY_COMPANIES="select cr.system_rating as rating, invoice_currency_code,vendor_company_name, customer_company_name,vendor_partner_id, customer_partner_id,   " + 
    		" noofinvoices,current,oneto30days,thirtyoneto60days,sixtyoneto90days,over90days,balancedue,paid_amount,transaction_type, true as isexternal  " + 
    		" from closedExternalInvoicesByCompany   " + 
    		"left join einvoicing.company_rating cr on customer_partner_id=cr.to_partner_id  " + 
    		"where vendor_partner_id=?1 and transaction_type='receivable' and invoice_currency_code=?2  " + 
    		"UNION ALL   " + 
    		"select cr.system_rating as rating,invoice_currency_code, vendor_company_name, customer_company_name,vendor_partner_id, customer_partner_id,   " + 
    		"noofinvoices,current,oneto30days,thirtyoneto60days,sixtyoneto90days,over90days,balancedue, paid_amount,transaction_type,false as isexternal from closedinternalinvoicesbycompany   " + 
    		"left join einvoicing.company_rating cr on customer_partner_id=cr.to_partner_id  " + 
    		"where vendor_partner_id=?1 and invoice_currency_code=?2 order by balancedue desc";
    
    public static final String DSHBRD_ALL_CLOSED_PAYABLES_BY_COMPANIES="select cr.system_rating as rating,invoice_currency_code, vendor_company_name, customer_company_name,vendor_partner_id, customer_partner_id,   " + 
    		" noofinvoices,current,oneto30days,thirtyoneto60days,sixtyoneto90days,over90days,balancedue,paid_amount,transaction_type, true as isexternal  " + 
    		" from closedExternalInvoicesByCompany   " + 
    		"left join einvoicing.company_rating cr on customer_partner_id=cr.to_partner_id  " + 
    		"where customer_partner_id=?1 and transaction_type='payable'  and invoice_currency_code=?2 " + 
    		"UNION ALL   " + 
    		"select cr.system_rating as rating,invoice_currency_code, vendor_company_name, customer_company_name,vendor_partner_id, customer_partner_id,   " + 
    		"noofinvoices,current,oneto30days,thirtyoneto60days,sixtyoneto90days,over90days,balancedue, paid_amount,transaction_type,false as isexternal from closedinternalinvoicesbycompany   " + 
    		"left join einvoicing.company_rating cr on customer_partner_id=cr.to_partner_id  " + 
    		"where customer_partner_id=?1 and invoice_currency_code=?2 order by balancedue desc";
    
   /* public static final String DSHBRD_ALL_PAYABLES_SUMMARY="select sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days, " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue " + 
    		"from ( " + 
    		"select sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days, " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue from ExternalInvoiceDuesByCompany  " + 
    		"    		where vendor_partner_id=? and transaction_type='payable'  " + 
    		"    		UNION ALL  " + 
    		"select sum(noofinvoices) as noofinvoices,sum(current) as current,sum(oneto30days) as oneto30days ,sum(thirtyoneto60days) as thirtyoneto60days, " + 
    		"sum(sixtyoneto90days) as sixtyoneto90days ,sum(over90days)  as over90days,sum(balancedue) as balancedue from internalinvoiceduesbycompany  " + 
    		"    		where vendor_partner_id=?) summary	"; */
    
   /* public static final String DSHBRD_ALL_RECEIVABLES_BY_COMPANY="select idd.document_ref_id, idd.vendor_partner_id,idd.customer_partner_id, pd.company_name,invoicenum, invoicedate, " + 
    		"invoice_currency_code, invoice_status, total_invoice_value,sum(ipd.paid_amount) as paid_amount, " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance, " + 
    		" (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) as invoice_duedate, " + 
    		"case when now() < (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) then null " + 
    		" else " + 
    		" extract(day from now() - (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0))))) end as age " +
    		" from einvoicing.invoice_document_details idd " + 
    		"left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id " + 
    		"left join einvoicing.invoice_paid_details ipd on idd.document_ref_id = ipd.document_ref_id " + 
    		"where idd.vendor_partner_id=? and idd.customer_partner_id=? and invoice_status!='Paid' " + 
    		"group by idd.document_ref_id,pd.company_name  " + 
    		"union all " + 
    		"select eidd.document_ref_id, eidd.partner_id as vendor_partner_id, recipient_partner_id as customer_partner_id, epd.company_name,invoicenum, invoicedate, " + 
    		"invoice_currency_code, invoice_status, total_invoice_value,sum(ipd.paid_amount) as paid_amount, " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance, " + 
    		" (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) as invoice_duedate, " + 
    		" case when now() < (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) then null " + 
    		" else " + 
    		" extract(day from now() - (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0))))) end as age " +
    		" from einvoicing.external_invoice_document_details eidd " + 
    		"left join einvoicing.external_partner_details epd on eidd.recipient_partner_id = epd.partner_id " + 
    		"left join einvoicing.invoice_paid_details ipd on eidd.document_ref_id = ipd.document_ref_id " + 
    		"where eidd.partner_id=? and eidd.recipient_partner_id=? and invoice_status!='Paid' and transaction_type='receivable' " + 
    		"group by eidd.document_ref_id,epd.company_name order by invoice_duedate asc"; */
    
    public static final String DSHBRD_ALL_CURRENT_RECEIVABLES_BY_COMPANY="select idd.document_ref_id, idd.vendor_partner_id,idd.customer_partner_id, pd.company_name,invoicenum, invoicedate, " + 
    		"invoice_currency_code, invoice_status, total_invoice_value,sum(ipd.paid_amount) as paid_amount, " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance, " + 
    		" (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) as invoice_duedate, " + 
    		"case when now() < (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) then null " + 
    		" else " + 
    		" extract(day from now() - (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0))))) end as age " +
    		" from einvoicing.invoice_document_details idd " + 
    		"left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id " + 
    		"left join einvoicing.invoice_paid_details ipd on idd.document_ref_id = ipd.document_ref_id " + 
    		"where idd.vendor_partner_id=?1 and idd.customer_partner_id=?2 and invoice_status not in ('Paid') and invoice_currency_code=?3 " + 
    		"group by idd.document_ref_id,pd.company_name  " + 
    		"union all " + 
    		"select eidd.document_ref_id, eidd.partner_id as vendor_partner_id, recipient_partner_id as customer_partner_id, epd.company_name,invoicenum, invoicedate, " + 
    		"invoice_currency_code, invoice_status, total_invoice_value,sum(ipd.paid_amount) as paid_amount, " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance, " + 
    		" (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) as invoice_duedate, " + 
    		" case when now() < (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) then null " + 
    		" else " + 
    		" extract(day from now() - (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0))))) end as age " +
    		" from einvoicing.external_invoice_document_details eidd " + 
    		"left join einvoicing.external_partner_details epd on eidd.recipient_partner_id = epd.partner_id " + 
    		"left join einvoicing.invoice_paid_details ipd on eidd.document_ref_id = ipd.document_ref_id " + 
    		"where eidd.partner_id=?1 and eidd.recipient_partner_id=?2 and transaction_type='receivable' and invoice_status not in ('Paid') and invoice_currency_code=?3 " + 
    		"group by eidd.document_ref_id,epd.company_name order by invoice_duedate asc";
    
    
    
    public static final String DSHBRD_ALL_CLOSED_RECEIVABLES_BY_COMPANY="select idd.document_ref_id, idd.vendor_partner_id,idd.customer_partner_id, pd.company_name,invoicenum, invoicedate, " + 
    		"invoice_currency_code, invoice_status, total_invoice_value,sum(ipd.paid_amount) as paid_amount, " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance, " + 
    		" (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) as invoice_duedate, " + 
    		"case when now() < (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) then null " + 
    		" else " + 
    		" extract(day from now() - (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0))))) end as age " +
    		" from einvoicing.invoice_document_details idd " + 
    		"left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id " + 
    		"left join einvoicing.invoice_paid_details ipd on idd.document_ref_id = ipd.document_ref_id " + 
    		"where idd.vendor_partner_id=?1 and idd.customer_partner_id=?2 and invoice_status in ('Paid') and invoice_currency_code=?3 " + 
    		"group by idd.document_ref_id,pd.company_name  " + 
    		"union all " + 
    		"select eidd.document_ref_id, eidd.partner_id as vendor_partner_id, recipient_partner_id as customer_partner_id, epd.company_name,invoicenum, invoicedate, " + 
    		"invoice_currency_code, invoice_status, total_invoice_value,sum(ipd.paid_amount) as paid_amount, " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance, " + 
    		" (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) as invoice_duedate, " + 
    		" case when now() < (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) then null " + 
    		" else " + 
    		" extract(day from now() - (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0))))) end as age " +
    		" from einvoicing.external_invoice_document_details eidd " + 
    		"left join einvoicing.external_partner_details epd on eidd.recipient_partner_id = epd.partner_id " + 
    		"left join einvoicing.invoice_paid_details ipd on eidd.document_ref_id = ipd.document_ref_id " + 
    		"where eidd.partner_id=?1 and eidd.recipient_partner_id=?2 and transaction_type='receivable' and invoice_status in ('Paid') and invoice_currency_code=?3 " + 
    		"group by eidd.document_ref_id,epd.company_name order by invoice_duedate asc";
    
    
    public static final String DSHBRD_ALL_CLOSED_PAYABLES_BY_COMPANY="select idd.document_ref_id, idd.vendor_partner_id,idd.customer_partner_id, pd.company_name,invoicenum, invoicedate, " + 
    		"invoice_currency_code, invoice_status, total_invoice_value,sum(ipd.paid_amount) as paid_amount, " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance, " + 
    		" (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) as invoice_duedate, " + 
    		"case when now() < (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) then null " + 
    		" else " + 
    		" extract(day from now() - (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0))))) end as age " +
    		" from einvoicing.invoice_document_details idd " + 
    		"left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id " + 
    		"left join einvoicing.invoice_paid_details ipd on idd.document_ref_id = ipd.document_ref_id " + 
    		"where idd.customer_partner_id=?1 and idd.vendor_partner_id=?2 and and invoice_status in ('Paid') " + 
    		"group by idd.document_ref_id,pd.company_name  " + 
    		"union all " + 
    		"select eidd.document_ref_id, eidd.partner_id as vendor_partner_id, recipient_partner_id as customer_partner_id, epd.company_name,invoicenum, invoicedate, " + 
    		"invoice_currency_code, invoice_status, total_invoice_value,sum(ipd.paid_amount) as paid_amount, " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance, " + 
    		" (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) as invoice_duedate, " + 
    		" case when now() < (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) then null " + 
    		" else " + 
    		" extract(day from now() - (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0))))) end as age " +
    		" from einvoicing.external_invoice_document_details eidd " + 
    		"left join einvoicing.external_partner_details epd on eidd.recipient_partner_id = epd.partner_id " + 
    		"left join einvoicing.invoice_paid_details ipd on eidd.document_ref_id = ipd.document_ref_id " + 
    		"where eidd.partner_id=?1 and eidd.recipient_partner_id=?2 and transaction_type='payable' and invoice_status in ('Paid') " + 
    		"group by eidd.document_ref_id,epd.company_name order by invoice_duedate asc";
    
    public static final String DSHBRD_ALL_CURRENT_PAYABLES_BY_COMPANY="select idd.document_ref_id, idd.vendor_partner_id,idd.customer_partner_id, pd.company_name,invoicenum, invoicedate, " + 
    		"invoice_currency_code, invoice_status, total_invoice_value,sum(ipd.paid_amount) as paid_amount, " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance, " + 
    		" (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) as invoice_duedate, " + 
    		"case when now() < (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) then null " + 
    		" else " + 
    		" extract(day from now() - (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0))))) end as age " +
    		" from einvoicing.invoice_document_details idd " + 
    		"left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id " + 
    		"left join einvoicing.invoice_paid_details ipd on idd.document_ref_id = ipd.document_ref_id " + 
    		"where idd.vendor_partner_id=?1 and idd.customer_partner_id=?2 and invoice_status not in ('Paid') and invoice_currency_code=?3 " + 
    		"group by idd.document_ref_id,pd.company_name  " + 
    		"union all " + 
    		"select eidd.document_ref_id, eidd.partner_id as vendor_partner_id, recipient_partner_id as customer_partner_id, epd.company_name,invoicenum, invoicedate, " + 
    		"invoice_currency_code, invoice_status, total_invoice_value,sum(ipd.paid_amount) as paid_amount, " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance, " + 
    		" (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) as invoice_duedate, " + 
    		" case when now() < (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) then null " + 
    		" else " + 
    		" extract(day from now() - (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0))))) end as age " +
    		" from einvoicing.external_invoice_document_details eidd " + 
    		"left join einvoicing.external_partner_details epd on eidd.recipient_partner_id = epd.partner_id " + 
    		"left join einvoicing.invoice_paid_details ipd on eidd.document_ref_id = ipd.document_ref_id " + 
    		"where eidd.partner_id=?1 and eidd.recipient_partner_id=?2 and transaction_type='receivable' and invoice_status not in ('Paid') and invoice_currency_code=?3 " + 
    		"group by eidd.document_ref_id,epd.company_name order by invoice_duedate asc";
    
    
    public static final String DSHBRD_RECEIVABLES_SUMMARY_BY_ONBOARDED_COMPANY= "SELECT pdvendor.company_name AS vendor_company_name, " + 
    "            pdcustomer.company_name AS customer_company_name," + 
    "            idd.vendor_partner_id, " + 
    "            idd.customer_partner_id,           " + 
    "		   SUM(CASE WHEN date(idd.invoicedate + interval '1' day * COALESCE(idd.creditdays, 0)) > date(now() - interval '0' day) THEN idd.total_invoice_value  " + 
    "    			ELSE 0 END) AS currentdue, " + 
    "   			SUM(CASE WHEN date(idd.invoicedate + interval '1' day * COALESCE(idd.creditdays, 0)) < date(now() - interval '1' day) THEN idd.total_invoice_value  " + 
    "    			ELSE 0 END) AS overdue, " + 
    "			SUM(CASE WHEN idd.invoice_status='Approved' THEN idd.total_invoice_value  " + 
    "    			ELSE 0 END) AS promisetopay, " + 
    "			SUM(CASE WHEN idd.invoice_status not in ('Approved','Paid','Written-Off','Voided') THEN idd.total_invoice_value  " + 
    "    			ELSE 0 END) AS pending, " + 
    "			SUM(CASE WHEN idd.invoice_status in ('Paid','Partially Paid') THEN ipd.paid_amount  " + 
    "    			ELSE 0 END) AS paidamount, " + 
    "			sum(idd.total_invoice_value) AS totaltobepaid   " + 
    "           FROM einvoicing.invoice_document_details idd " + 
    "		   left join einvoicing.invoice_paid_details ipd on idd.document_ref_id = ipd.document_ref_id     		 " + 
    "             LEFT JOIN einvoicing.partner_details pdvendor ON idd.vendor_partner_id = pdvendor.partner_id " + 
    "             LEFT JOIN einvoicing.partner_details pdcustomer ON idd.customer_partner_id = pdcustomer.partner_id " + 
    "         where idd.vendor_partner_id=? and idd.customer_partner_id=?  " + 
    "		  GROUP BY pdvendor.company_name, pdcustomer.company_name, idd.vendor_partner_id, idd.customer_partner_id";
    
    
    public static final String DSHBRD_RECEIVABLES_SUMMARY_BY_EXTERNAL_COMPANY="SELECT pdvendor.company_name AS vendor_company_name, " + 
    		"            pdcustomer.company_name AS customer_company_name, " + 
    		"            eidd.partner_id as vendor_partner_id, " + 
    		"            eidd.recipient_partner_id as customer_partner_id,           " + 
    		"		   SUM(CASE WHEN date(eidd.invoicedate + interval '1' day * COALESCE(eidd.creditdays, 0)) > date(now() - interval '0' day) THEN eidd.total_invoice_value  " + 
    		"    			ELSE 0 END) AS currentdue, " + 
    		"   			SUM(CASE WHEN date(eidd.invoicedate + interval '1' day * COALESCE(eidd.creditdays, 0)) < date(now() - interval '1' day) THEN eidd.total_invoice_value  " + 
    		"    			ELSE 0 END) AS overdue, " + 
    		"			SUM(CASE WHEN eidd.invoice_status='Approved' THEN eidd.total_invoice_value  " + 
    		"    			ELSE 0 END) AS promisetopay, " + 
    		"			SUM(CASE WHEN eidd.invoice_status not in ('Approved','Paid','Written-Off','Voided') THEN eidd.total_invoice_value  " + 
    		"    			ELSE 0 END) AS pending, " + 
    		"			SUM(CASE WHEN eidd.invoice_status in ('Paid','Partially Paid') THEN ipd.paid_amount  " + 
    		"    			ELSE 0 END) AS paidamount, " + 
    		"			sum(eidd.total_invoice_value) AS totaltobepaid   " + 
    		"           FROM einvoicing.external_invoice_document_details eidd " + 
    		"		   left join einvoicing.invoice_paid_details ipd on eidd.document_ref_id = ipd.document_ref_id     		 " + 
    		"             LEFT JOIN einvoicing.partner_details pdvendor ON eidd.partner_id = pdvendor.partner_id " + 
    		"             LEFT JOIN einvoicing.external_partner_details pdcustomer ON eidd.recipient_partner_id = pdcustomer.partner_id " + 
    		"         where eidd.partner_id=? and eidd.recipient_partner_id=?  " + 
    		"		  GROUP BY pdvendor.company_name, pdcustomer.company_name, eidd.partner_id, eidd.recipient_partner_id";
    
    public static final String DSHBRD_CONTACTDETAILS_ONBOARDED_COMPANY="select pd.partner_id,pd.company_name, " + 
    "'A' as rating, " + 
    "pgd.partner_address, " + 
    "CONCAT(pcd.first_name,' ',pcd.last_name) as contactname, " + 
    "pcd.primary_phone_number,pcd.email " + 
    "from einvoicing.partner_details pd " + 
    "left join einvoicing.partner_contact_details pcd on pd.partner_id=pcd.partner_id " + 
    " LEFT JOIN LATERAL ( " + 
    "        SELECT CONCAT(bnm,' ',bno,' ',flno,' ',city,' ',pncd) as partner_address " + 
    "        FROM einvoicing.partner_gstin_details " + 
    "        WHERE partner_id = pd.partner_id          " + 
    "        LIMIT 1) pgd ON true " + 
    "where pd.partner_id=? and pcd.type_of_contact='Primary'";
    
    public static final String DSHBRD_CONTACTDETAILS_EXTERNAL_COMPANY="select epd.partner_id,epd.company_name, " + 
    		"'A' as rating, " + 
    		"epd.partner_address, " + 
    		"CONCAT(epd.first_name,' ',epd.last_name) as contactname, " + 
    		"epd.primary_phone_number,epd.email " + 
    		"from einvoicing.external_partner_details epd " + 
    		"where epd.partner_id=?";
    
    
    public static final String DSHBRD_INTERNAL_INVOICE_RECEIVABLE_ACTIVITIES="select notes as activity,CONCAT(um.first_name,' ',um.last_name) as activityby,  " + 
    		"ein.created_on as activity_date,idd.invoicenum,notes_type as activity_subtype, 'notes' as activity_type " + 
    		"from einvoicing.invoice_notes ein " + 
    		"left join einvoicing.user_management um on ein.created_by=um.user_id " + 
    		"left join einvoicing.invoice_document_details idd on ein.document_ref_id=idd.document_ref_id " + 
    		"where idd.vendor_partner_id=? and idd.customer_partner_id=? and idd.invoice_status not in('Paid')" + 
    		"union all " + 
    		"select action_comments as activity,CONCAT(um.first_name,' ',um.last_name) activityby,  " + 
    		"ist.action_date as activity_date,idd.invoicenum,ist.action as activity_subtype, 'statusupdate' as activity_type " + 
    		"from einvoicing.invoice_status_tracker ist " + 
    		"left join einvoicing.user_management um on ist.action_by=um.user_id " + 
    		"left join einvoicing.invoice_document_details idd on ist.document_ref_id=idd.document_ref_id " + 
    		"where idd.vendor_partner_id=? and idd.customer_partner_id=? and idd.invoice_status not in('Paid')" + 
    		"union all " + 
    		"select query_text as activity,CONCAT(um.first_name,' ',um.last_name) activityby,  " + 
    		"iq.created_date as activity_date,idd.invoicenum,iq.query_type as activity_subtype, 'query' as activitytype " + 
    		"from einvoicing.invoice_queries iq " + 
    		"left join einvoicing.user_management um on iq.created_by=um.user_id " + 
    		"left join einvoicing.invoice_document_details idd on iq.document_ref_id=idd.document_ref_id " + 
    		"where idd.vendor_partner_id=? and idd.customer_partner_id=? and idd.invoice_status not in('Paid') " + 
    		"order by activity_date desc";
    
    public static final String DSHBRD_EXTERNAL_INVOICE_RECEIVABLE_ACTIVITIES="select notes as activity,CONCAT(um.first_name,' ',um.last_name) as activityby,  " + 
    		"ein.created_on as activity_date,eidd.invoicenum,notes_type as activity_subtype, 'notes' as activity_type " + 
    		"from einvoicing.external_invoice_notes ein " + 
    		"left join einvoicing.user_management um on ein.created_by=um.user_id " + 
    		"left join einvoicing.external_invoice_document_details eidd on ein.document_ref_id=eidd.document_ref_id " + 
    		"where eidd.partner_id=? and eidd.recipient_partner_id=? and eidd.invoice_status not in ('Paid')  " + 
    		"union all " + 
    		"select action_comments as activity,CONCAT(um.first_name,' ',um.last_name) activityby,  " + 
    		"eist.action_date as activity_date,eidd.invoicenum,eist.action as activity_subtype, 'statusupdate' as activity_type " + 
    		"from einvoicing.external_invoice_status_tracker eist " + 
    		"left join einvoicing.user_management um on eist.action_by=um.user_id " + 
    		"left join einvoicing.external_invoice_document_details eidd on eist.document_ref_id=eidd.document_ref_id " + 
    		"where eidd.partner_id=? and eidd.recipient_partner_id=? and eidd.invoice_status not in ('Paid') "+
    		"order by activity_date desc ";
    
    public static final String DSHBRD_ALL_RECEIVABLES_FOR_CASHFLOW  =" select idd.document_ref_id, pd.company_name,invoicenum, 'invoice' as type,invoice_currency_code,  " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance,  " + 
    		"(date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) as invoice_duedate, " + 
    		"(date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) as expected_paymentdate " + 
    		"from einvoicing.invoice_document_details idd " + 
    		"left join einvoicing.partner_details pd on idd.customer_partner_id = pd.partner_id  " + 
    		"left join einvoicing.invoice_paid_details ipd on idd.document_ref_id = ipd.document_ref_id  " + 
    		"where idd.vendor_partner_id=?1 and invoice_status!='Paid'  " + 
    		" and  ((date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) <= ?2 " + 
    		"   or  (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) BETWEEN " + 
    		"   ?2 and ?3 ) " + 
    		"group by idd.document_ref_id,pd.company_name  " + 
    		"union all " + 
    		"select eidd.document_ref_id, epd.company_name,invoicenum, 'invoice' as type,invoice_currency_code,   " + 
    		"   total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance,   " + 
    		"   (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) as invoice_duedate, " + 
    		"   (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) as expected_paymentdate " +
    		"     from einvoicing.external_invoice_document_details eidd   " + 
    		"   left join einvoicing.external_partner_details epd on eidd.recipient_partner_id = epd.partner_id   " + 
    		"   left join einvoicing.invoice_paid_details ipd on eidd.document_ref_id = ipd.document_ref_id   " + 
    		"   where eidd.partner_id=?1 and invoice_status!='Paid' and transaction_type='receivable'   " + 
    		"   and  ((date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) <= ?2 " + 
    		"   or  (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) BETWEEN " + 
    		"   ?2 and ?3 ) " + 
    		"   group by eidd.document_ref_id,epd.company_name " +
    		" union all " + 
    		"select 'NA' as document_ref_id, budget_name, cast( id as character varying) as invoicenum, 'budget' as type, budget_currency as invoice_currency_code, " + 
    		"budget_value as balance,date(invoice_duedate) as invoice_duedate, " + 
    		" date(invoice_duedate) as expected_paymentdate " +
    		"from einvoicing.budget_details cross join lateral " + 
    		"  generate_series(greatest(budget_starts_on,?2), least(budget_ends_on,?3), interval '7 day') AS invoice_duedate " + 
    		"where  partner_id=?1 and budget_occurs='Every week' and budget_category='Receivable' and" + 
    		"  (?2 between budget_starts_on and budget_ends_on) and " + 
    		"  (?3 between budget_starts_on and budget_ends_on) " +   
    		" union all " + 
    		" select 'NA' as document_ref_id, budget_name, cast( id as character varying) as invoicenum, 'budget' as type, budget_currency as invoice_currency_code, " +
			" budget_value as balance,date(invoice_duedate) as invoice_duedate, " +
    		" date(invoice_duedate) as expected_paymentdate " +
			" from einvoicing.budget_details cross join lateral " +
			" generate_series(greatest(budget_starts_on,?2), least(budget_ends_on,?3), interval '1 month') AS invoice_duedate " +
			" where partner_id=?1 and budget_occurs='Every month' and budget_category='Receivable' and " +
			" (?2 between budget_starts_on and budget_ends_on) and " +
			" (?3 between budget_starts_on and budget_ends_on) " +
    		" union all " + 
    		"select 'NA' as document_ref_id, budget_name, cast( id as character varying) as invoicenum, 'budget' as type, budget_currency as invoice_currency_code, " + 
    		"budget_value as balance,date(invoice_duedate) as invoice_duedate, " + 
    		" date(invoice_duedate) as expected_paymentdate " +
    		"from einvoicing.budget_details cross join lateral " + 
    		"     generate_series(greatest(budget_starts_on,?2), least(budget_ends_on,?3), interval '90 day') AS invoice_duedate " + 
    		"where partner_id=?1 and budget_occurs='Every quarter' and budget_category='Receivable' and " + 
    		" (?2 between budget_starts_on and budget_ends_on) and " + 
    		" (?3 between budget_starts_on and budget_ends_on) " +    	
    		" order by invoice_duedate asc	";
    		
    
    public static final String DSHBRD_ALL_PAYABLES_FOR_CASHFLOW  =" select idd.document_ref_id, pd.company_name,invoicenum, 'invoice' as type,invoice_currency_code,  " + 
    		"total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance,  " + 
    		"(date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) as invoice_duedate, " + 
    		"(date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) as expected_paymentdate " + 
    		"from einvoicing.invoice_document_details idd " + 
    		"left join einvoicing.partner_details pd on idd.vendor_partner_id = pd.partner_id  " + 
    		"left join einvoicing.invoice_paid_details ipd on idd.document_ref_id = ipd.document_ref_id  " + 
    		"where idd.customer_partner_id=?1 and invoice_status!='Paid'  " + 
    		" and  ((date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) <= ?2 " + 
    		"   or  (date(idd.invoicedate + interval '1' day * (coalesce(idd.creditdays,0)))) BETWEEN " + 
    		"   ?2 and ?3 ) " + 
    		"group by idd.document_ref_id,pd.company_name  " + 
    		"union all " + 
    		"select eidd.document_ref_id, epd.company_name,invoicenum, 'invoice' as type,invoice_currency_code,   " + 
    		"   total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance,   " + 
    		"   (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) as invoice_duedate, " + 
    		"   (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) as expected_paymentdate " + 
    		"     from einvoicing.external_invoice_document_details eidd   " + 
    		"   left join einvoicing.external_partner_details epd on eidd.recipient_partner_id = epd.partner_id   " + 
    		"   left join einvoicing.invoice_paid_details ipd on eidd.document_ref_id = ipd.document_ref_id   " + 
    		"   where eidd.partner_id=?1 and invoice_status!='Paid' and transaction_type='payable'   " + 
    		"   and  ((date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) <= ?2 " + 
    		"   or  (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) BETWEEN " + 
    		"   ?2 and ?3 ) " + 
    		"   group by eidd.document_ref_id,epd.company_name " +
    		" union all " + 
    		"select 'NA' as document_ref_id, budget_name, cast( id as character varying) as invoicenum, 'budget' as type, budget_currency as invoice_currency_code, " + 
    		"budget_value as balance,date(invoice_duedate) as invoice_duedate, " + 
    		" date(invoice_duedate) as expected_paymentdate " + 
    		"from einvoicing.budget_details cross join lateral " + 
    		"     generate_series(greatest(budget_starts_on,?2), least(budget_ends_on,?3), interval '7 day') AS invoice_duedate " + 
    		"where  partner_id=?1 and budget_occurs='Every week' and budget_category='Payable' and " + 
    		" (?2 between budget_starts_on and budget_ends_on) and " + 
    		" (?3 between budget_starts_on and budget_ends_on) " +
    		" union all " +     		
			" select 'NA' as document_ref_id, budget_name, cast( id as character varying) as invoicenum, 'budget' as type, budget_currency as invoice_currency_code, " +
			" budget_value as balance,date(invoice_duedate) as invoice_duedate, " +
			" date(invoice_duedate) as expected_paymentdate " +
			" from einvoicing.budget_details cross join lateral " +
			" generate_series(greatest(budget_starts_on,?2), least(budget_ends_on,?3), interval '1 month') AS invoice_duedate " +
			" where partner_id=?1 and budget_occurs='Every month' and budget_category='Payable' and " +
			" (?2 between budget_starts_on and budget_ends_on) and " +
			" (?3 between budget_starts_on and budget_ends_on) " +
    		" union all " + 
    		"select 'NA' as document_ref_id, budget_name, cast( id as character varying) as invoicenum, 'budget' as type, budget_currency as invoice_currency_code, " + 
    		"budget_value as balance,date(invoice_duedate) as invoice_duedate, " + 
    		" date(invoice_duedate) as expected_paymentdate " +
    		"from einvoicing.budget_details cross join lateral " + 
    		"     generate_series(greatest(budget_starts_on,?2), least(budget_ends_on,?3), interval '90 day') AS invoice_duedate " + 
    		"where partner_id=?1 and budget_occurs='Every quarter' and budget_category='Payable' and " + 
    		" (?2 between budget_starts_on and budget_ends_on) and " + 
    		" (?3 between budget_starts_on and budget_ends_on) " +    	
    		"order by invoice_duedate asc	";
  
    
    /*
     * 		"select 'NA' as document_ref_id, budget_name, cast( id as character varying) as invoicenum, 'budget' as type, budget_currency as invoice_currency_code, " + 
    		"budget_value as balance,date(invoice_duedate) as invoice_duedate " + 
    		"from einvoicing.budget_details cross join lateral " + 
    		"     generate_series(budget_starts_on, budget_ends_on, interval '1 month') AS invoice_duedate " + 
    		"where partner_id=?1 and budget_occurs='Every month' and budget_category='Payable' " + 
     */
  // Invoice Reminder Templates  
    
    public static final String FETCH_INVOICE_REMINDER_TEMPLATE_NAMES = " select template_id,template_name,partner_id from einvoicing.invoice_reminder_templates " + 
    		"where partner_id=? or partner_id='system'";
    
    public static final String FETCH_INVOICE_REMINDER_TEMPLATE = " select * from einvoicing.invoice_reminder_templates " + 
    		"where template_id=? and (partner_id=? or partner_id='system')";
    
    public static final String FETCH_PAID_INVOICES_FOR_EXTERNAL_COMPANY_RATING= "select eidd.document_ref_id, eidd.partner_id as vendor_partner_id, recipient_partner_id as customer_partner_id, epd.company_name,invoicenum, invoicedate, " + 
    		"    invoice_status, total_invoice_value,sum(ipd.paid_amount) as paid_amount, " + 
    		"    total_invoice_value - coalesce(sum(ipd.paid_amount) , 0) as balance, " + 
    		"    (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) as invoice_duedate, " + 
    		"    case when ipd.paid_on < (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0)))) then 0 " + 
    		"     else " + 
    		"    extract(day from ipd.paid_on - (date(eidd.invoicedate + interval '1' day * (coalesce(eidd.creditdays,0))))) end as age_in_days " + 
    		"    from einvoicing.external_invoice_document_details eidd " + 
    		"    left join einvoicing.external_partner_details epd on eidd.recipient_partner_id = epd.partner_id " + 
    		"    left join einvoicing.invoice_paid_details ipd on eidd.document_ref_id = ipd.document_ref_id " + 
    		"    where eidd.partner_id=? and eidd.recipient_partner_id=? and invoice_status='Paid' and transaction_type='receivable' " + 
    		"    group by eidd.document_ref_id,epd.company_name,ipd.paid_on order by invoice_duedate asc	";
    
   
    
}

