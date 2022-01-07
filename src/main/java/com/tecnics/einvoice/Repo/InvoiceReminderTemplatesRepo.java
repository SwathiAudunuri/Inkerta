package com.tecnics.einvoice.Repo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.InvoiceReminderTemplates;

import com.tecnics.einvoice.constants.SQLQueries;

public interface InvoiceReminderTemplatesRepo extends CrudRepository<InvoiceReminderTemplates, Integer> {
	
	InvoiceReminderTemplates findByTemplateId(Integer templateId);
	Optional<InvoiceReminderTemplates> findByTemplateIdAndPartnerId(Integer templateId,String partnerId);
	Optional<InvoiceReminderTemplates> findByTemplateIdAndTemplateName(Integer templateId,String templateName);
	
	
	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_INVOICE_REMINDER_TEMPLATE)
	Optional<InvoiceReminderTemplates> fetchInvoiceReminderTemplate(Integer templateId, String partnerId);
	
	@Query(nativeQuery = true, value=SQLQueries.FETCH_INVOICE_REMINDER_TEMPLATE_NAMES)
	List<InvoiceReminderTemplateNames> fetchInvoiceReminderTemplateNames(String partnerId);
	

	public interface InvoiceReminderTemplateNames {
		Integer getTemplate_id();
		String getTemplate_name();
		String getPartner_id();	
	}


}
