package com.tecnics.einvoice.Repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import com.tecnics.einvoice.entity.BudgetDetails;

public interface BudgetDetailsRepo extends CrudRepository<BudgetDetails, Integer> {	


	List<BudgetDetails> findByPartnerIdAndBudgetCategory(String partnerId, String budgetCategory);
	BudgetDetails findByPartnerIdAndBudgetName(String partnerId,String budgetName);
	
	List<BudgetDetails> findByPartnerId(String partnerId);
	
	@Transactional
	@Modifying	
	@Query("delete from BudgetDetails where id = ?1 and partnerId=?2")
	public int deleteByIdAndPartnerId(Integer id, String partner_id);
	
}
