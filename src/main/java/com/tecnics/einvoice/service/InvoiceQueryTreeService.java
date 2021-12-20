package com.tecnics.einvoice.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tecnics.einvoice.model.InvoiceQueryTreeRow;
import com.tecnics.einvoice.model.InvoiceQueryTreeEntity;
public class InvoiceQueryTreeService {
	
	
	List<Map<String,InvoiceQueryTreeEntity>> nodeLists=new ArrayList();
	
	
	public List<InvoiceQueryTreeEntity> getTreeQueries(List<com.tecnics.einvoice.model.InvoiceQueryTreeRow> hierarchicalQueries)
	{
		
		List<InvoiceQueryTreeEntity> qry_tree_lists=new ArrayList<InvoiceQueryTreeEntity>();
		
		  for (InvoiceQueryTreeRow iqtr : hierarchicalQueries) {
			  System.out.println("Processing IQTR " + iqtr.getQueryRefId() + " parent = " + iqtr.getParentQueryRefId());
			  insertItem(iqtr,qry_tree_lists);
		  }
		
			       
		

				
		return qry_tree_lists;
	}
	
	public void insertItem(InvoiceQueryTreeRow row,List<InvoiceQueryTreeEntity> qne_lists)
	{
	
		System.out.println("QueryRefId="+row.getQueryRefId());
		
		InvoiceQueryTreeEntity qne=new InvoiceQueryTreeEntity(row);
		if(row.getParentQueryRefId()==null || row.getParentQueryRefId().equals("")) {
	
			qne_lists.add(qne);
		}
		else
		{
		
			  for (InvoiceQueryTreeEntity qn2e : qne_lists) {
				  InvoiceQueryTreeRow rowItem=qn2e.getData();
		    	   //System.out.println("checking in RefId = " + rowItem.getQueryRefId() + " ParentRefID : " + rowItem.getParentQueryRefId());
		    	   InvoiceQueryTreeEntity pnodeEntity=search(qn2e,row.getParentQueryRefId());
		    	   if (pnodeEntity != null) {
			        	//System.out.println("Found entity adding child to Parent");
			        	pnodeEntity.addChildren(qne);
		    	   }
			  }
		}
		
		
	}
	
	public InvoiceQueryTreeEntity search(InvoiceQueryTreeEntity root, String queryRefId) { 
	
		InvoiceQueryTreeRow row=null;
	
		 if (root.getData().getQueryRefId().equals(queryRefId)) {
		    	//System.out.println("First Match Found returning root");
		        return root;
		    } else {
		       
		    	if(root.getChildren()!=null)
		    	{
		    	for (InvoiceQueryTreeEntity entity : root.getChildren()) {
		    		 row=entity.getData();
		    		 
		    		 if(row!=null && row.getQueryRefId().equals(queryRefId))
		    			 return entity;
		    		 InvoiceQueryTreeEntity parent = search(entity, queryRefId);
		    		 if(parent!=null)
		    		 row=parent.getData();
		    		   
		    		 if(row!=null && row.getQueryRefId().equals(queryRefId))
		    			 return parent;
		    		 
		    		  
		    	}
		    	}
		    	
		
		
	}
		return null;
	}

}

