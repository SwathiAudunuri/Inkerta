/**
 * 
 */
package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.tecnics.einvoice.entity.InvoiceAttachmentDetail;

public interface InvoiceAttachmentDetailsRepo extends CrudRepository<InvoiceAttachmentDetail, Integer> {
	
	//InvoiceAttachmentDetail findByRefIdAndDocType(String refId, String docType);
	List<InvoiceAttachmentDetail> findByRefIdAndDocType(String refId, String docType);
	InvoiceAttachmentDetail findByDocId(String docId);

}
