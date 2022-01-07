package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import com.tecnics.einvoice.entity.AttachmentDetails;

public interface AttachmentDetailsRepo extends CrudRepository<AttachmentDetails, Integer> {
	

	List<AttachmentDetails> findByRefIdAndDocType(String refId, String docType);
	AttachmentDetails findByDocId(String docId);
	List<AttachmentDetails> findByRefIdAndDocCategory(String refId, String docCategory);
	AttachmentDetails findByDocIdAndDocCategory(String docId,String docCategory);
	AttachmentDetails findByRefIdAndDocCategoryAndDocType(String refId,String docCategory, String docType);
}