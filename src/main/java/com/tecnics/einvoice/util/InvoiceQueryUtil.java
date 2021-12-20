package com.tecnics.einvoice.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tecnics.einvoice.entity.InvoiceQuery;
import com.tecnics.einvoice.service.InvoiceQueryService;

@Component
public class InvoiceQueryUtil {

	private static final Logger logger = LoggerFactory.getLogger(InvoiceQueryUtil.class);
	
	
	public String generateQueryRefId(InvoiceQueryService invoiceQueryService,String documentRefId,String parentQueryRefId)
	{
		System.out.println("inside generateQueryRefId parentQueryRefId= "+parentQueryRefId);
		List<InvoiceQuery> iq1=invoiceQueryService.findByDocumentRefIdAndParentQueryRefId(documentRefId, parentQueryRefId);
		//List<InvoiceQuery> iq1=invoiceQueryService.findByParentQueryRefId(parentQueryRefId);
		if(iq1!=null)
		System.out.println("findByDocumentRefIdAndQueryRefId inside executed size="+iq1.size());
		
		return parentQueryRefId+"."+(iq1.size()+1);
		
	}
	
	
	public List<com.tecnics.einvoice.model.InvoiceQuery> getQueriesTree(List< com.tecnics.einvoice.model.InvoiceQuery> hierarchicalQueries) {
		
		
		com.tecnics.einvoice.model.InvoiceQuery hierarchicalQuery=null;
	    List<com.tecnics.einvoice.model.InvoiceQuery> roots = new ArrayList<>();
	    Map<String, List<com.tecnics.einvoice.model.InvoiceQuery>> parentMap = new HashMap<>();
	    
	    
	
	    // Assign each query to a child list
	    for (com.tecnics.einvoice.model.InvoiceQuery query : hierarchicalQueries) {
	    	
	        // Put the query  in the right child list
	        String parentId = query.getParentQueryRefId();
	        if (parentId == null || parentId.equals("")) {
	            // a root query
	            roots.add(query);
	        } else {
	            // a non-root query
	        	List<com.tecnics.einvoice.model.InvoiceQuery> parentChildren = parentMap.get(parentId);
	            if (parentChildren == null) {
	                parentChildren = new ArrayList<>();
	                parentMap.put(parentId, parentChildren);
	            }
	            parentChildren.add(query);
	        }

	        // Get or create the child list of the query
	        List<com.tecnics.einvoice.model.InvoiceQuery> ownChildren = parentMap.get(query.getQueryRefId());
	        if (ownChildren == null) {
	            ownChildren = new ArrayList<>();
	            parentMap.put(query.getQueryRefId(), ownChildren);
	        }
	        query.setChildren(ownChildren); 
	    }
	    return roots;
	}
	

}
