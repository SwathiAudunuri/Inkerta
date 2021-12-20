package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.tecnics.einvoice.entity.VendorMapping;
import com.tecnics.einvoicejson.model.VendorMappingSearchModel;


public interface VendorMappingRepo extends CrudRepository<VendorMapping, Integer>{

	List<VendorMapping> findByCustomerPartnerId(String customerPartnerId);

	@Query("SELECT p FROM VendorMapping p WHERE CONCAT(p.status, p.description, p.customerPartnerId,p.vendorPartnerId) LIKE %:searchString% and p.customerPartnerId=:id")
	List<VendorMapping> searchFilter(String searchString ,String id);
	
//	 @Query("SELECT * FROM einvoicing.vendor_mapping "+" where  customer_partner_id like ? "
//		+ " OR vendor_partner_id like ? " 
//				+ " OR  description  like ? " )
//	    public List<VendorMapping> search(String keyword);


}
