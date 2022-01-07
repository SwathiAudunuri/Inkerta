package com.tecnics.einvoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.VendorMappingActivityRepo;
import com.tecnics.einvoice.Repo.VendorMappingRepo;
import com.tecnics.einvoice.constants.RecipientConstants;
import com.tecnics.einvoice.constants.SQLQueries;
import com.tecnics.einvoice.constants.VendorMappingConstants;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.RecipientMapping;
import com.tecnics.einvoice.entity.TodoTaskActivity;
import com.tecnics.einvoice.entity.VendorMapping;
import com.tecnics.einvoice.entity.VendorMappingActivity;
import com.tecnics.einvoice.model.CustomerInfoModel;
import com.tecnics.einvoice.response.TransactionResponse;
import com.tecnics.einvoicejson.model.VendorMappingSearchModel;

@Component
public class VendorMappingService extends BaseService{

	public static final String CLASSNAME="VendorMappingService";
	
	@Autowired
	VendorMappingRepo vendorMappingRepo;

	
	@Autowired
	VendorMappingActivityRepo vendorMappingActivityRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	/**
	 * @param customerPartnerId
	 * @return
	 */
	public List<VendorMapping> findByCustomerPartnerId(String token) {
		String userid =getUserName(token);
		String cusotmerPid= getPartnerIdfromUserId(userid);
		return vendorMappingRepo.findByCustomerPartnerId(cusotmerPid);
	}



	
	public TransactionResponse save(VendorMapping obj, String user) {
		final String methodname="TransactionResponse";
		log.logMethodEntry(CLASSNAME + methodname);
		int failCount =0,successCount=0;
		List<VendorMappingActivity> activities = obj.getVendorMappingActivities();

		TransactionResponse vmResponse = new TransactionResponse();
		try {
			VendorMapping response = vendorMappingRepo.save(obj);
			
			vmResponse.setRefid(response.getMappingId());
			for (VendorMappingActivity activity : activities) {
				List<Integer> actIds = new ArrayList<Integer>();
				activity.setActionBy(user);
				activity.setVendorMapping(obj);
					VendorMappingActivity actResponse = vendorMappingActivityRepo.save(activity);
					actIds.add(actResponse.getId());
				
				}
			successCount++;
		}
		
		catch(Exception ex){
			log.logErrorMessage(ex.getMessage(),ex);
			failCount ++;
			errorLogRepo.save( new ErrorLog(VendorMappingConstants.MODULENAME,"",getStackTrace(ex),RecipientConstants.SAVE_ACTION,"","Backend",user));
		}
		
	
		vmResponse.setFailureTransactionsCount(failCount);
		vmResponse.setSuccessTransactionsCount(successCount);
		vmResponse.setTotalTransactionsCount(failCount+successCount);
		return vmResponse;
	}
	
	
	/**
	 * @return
	 */
	public Map<Object, Object> findAllVendors() {
		return findAllVendorsService();
	}


	/**
	 * @return
	 */
	public Map<Object, Object> findVendorsByName(String companyName) {
		// TODO Auto-generated method stub
		return findVendorsByNameService(companyName);
	}




	public List<CustomerInfoModel> findByVendorPartnerId(String token) {
		String userid =getUserName(token);
		String vendorPartnerId= getPartnerIdfromUserId(userid);	
		List<CustomerInfoModel> customers = jdbcTemplate.query(SQLQueries.GET_MY_RECIPEINTS, new String[] { vendorPartnerId },
				new BeanPropertyRowMapper<CustomerInfoModel>(CustomerInfoModel.class));
		return customers;
	}




	public Optional<VendorMapping> findById(Integer id) {
		// TODO Auto-generated method stub
		return (Optional<VendorMapping>) vendorMappingRepo.findById(id);
	}




	public VendorMapping update(int i, VendorMapping vendorMapping) {
		// TODO Auto-generated method stub
		vendorMapping.setMappingId(i);
		return vendorMappingRepo.save(vendorMapping);
	}




	public void delete(Integer id) {
		// TODO Auto-generated method stub
		vendorMappingRepo.deleteById(id);
	}




	public List<VendorMapping> searchVendor(VendorMappingSearchModel vendorMapping) {
		// TODO Auto-generated method stub
		return vendorMappingRepo.searchFilter(vendorMapping.getSearchString(),vendorMapping.getId());
	}




//	public List<VendorMappingActivity> findByvendorMapping(int id) {
//		// TODO Auto-generated method stub
//		return VendorMappingActivityRepo.resultsmapping_id(id);
//	}

//	public List<TodoTaskActivity> findBytaskRefId(int id) {
//		// TODO Auto-generated method stub
//		return todoActivityRepo.resultsTask_ref_id(id);
//	}

//	 public List<VendorMapping> listAll(String keyword) {
//	        if (keyword != null) {
//	            return vendorMappingRepo.search(keyword);
//	        }
//	        return (List<VendorMapping>) vendorMappingRepo.findAll();
//	    }
	 
//	public List<VendorMapping> searchVendor(VendorMapping vendorMapping) {
//		// TODO Auto-generated method stub
//		String sql= "SELECT * FROM einvoicing.vendor_mapping "+" where  "
//		+ " vendor_partner_id like ? " 
//				+ " OR  description  like ? "
//		+" and customer_partner_id like ? "; 
//				 
//		
//		String customerPartnerId ="" ;
//		String vendorPartnerId = "";
//		String description ="";
//		
//		if (vendorMapping.getCustomerPartnerId()!="") {
//			customerPartnerId = vendorMapping.getCustomerPartnerId()+"%";
//		}
//		if(vendorMapping.getVendorPartnerId()!="") {
//			vendorPartnerId = vendorMapping.getVendorPartnerId()+"%";
//		}
//		if(vendorMapping.getDescription()!="") {
//			description = vendorMapping.getDescription()+"%";
//		}
//		return jdbcTemplate.query(sql, new Object[] {customerPartnerId ,vendorPartnerId, description},
//		new BeanPropertyRowMapper<VendorMapping>(VendorMapping.class));
//	}


//	public List<Vendor> searchVendor(Vendor vendor) {
//
//		String sql = "SELECT * FROM vendornew.vendor " + " where vendor_code like ? "
//				+ " OR typeofbusiness like ? " + " OR city like ? ";
//
//		String vendorCode = "";
//		String typeofbusiness = "";
//		String city = "";
//		
//		if(vendor.getVendor_code()!="") {
//			vendorCode = vendor.getVendor_code()+"%";
//		}
//		if(vendor.getTypeofbusiness()!="") {
//			typeofbusiness =vendor.getTypeofbusiness()+"%";
//		}
//		if(vendor.getCity()!="") {
//			city =vendor.getCity()+"%";
//		}
//		
//		return jdbcTemplate.query(sql, new Object[] {vendorCode,typeofbusiness,city },
//				new BeanPropertyRowMapper<Vendor>(Vendor.class));
//	}
	
}
