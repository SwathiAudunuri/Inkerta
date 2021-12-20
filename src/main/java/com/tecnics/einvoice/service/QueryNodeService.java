package com.tecnics.einvoice.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tecnics.einvoice.model.QueryNode;
public class QueryNodeService {
	
	public List<QueryNode> rootNodes=new ArrayList();
	
	List<Map<String,QueryNode>> nodeLists=new ArrayList();
	
	public QueryNode search(QueryNode root, String queryRefId) { 
		System.out.println("Searching for queryRefId =  " + queryRefId);
	    if (root.getQueryRefId() == queryRefId) {
	    	System.out.println("First Match Found returning root");
	        return root;
	    } else {
	       
	    	for (Map<String, QueryNode> entry : root.getChildren()) {
	    		QueryNode exNode =null;
	    		for (String key : entry.keySet()) 
			        exNode = entry.get(key);
			        System.out.println("RefId = " + exNode.getQueryRefId() + " ParentRefID : " + exNode.getParentQueryRefId());
			        QueryNode parent = search(exNode, queryRefId);
			        if (parent != null) {
		                return parent;
		            }
	    	}
	    	
	    /*	
	    	for (QueryNode child : root.getChildren()) {
	        	QueryNode node = search(child, queryRefId);
	            if (node != null) {
	                return node;
	            }
	        } */
	    }
	    return null;
	}

	public void insert(QueryNode node, String parentId) {
		if(parentId==null || parentId=="") {
			HashMap<String,QueryNode> nodeMap1=new HashMap<String,QueryNode>();
		nodeMap1.put("data", node);
		nodeLists.add(nodeMap1);
		}
		else
		{	
			
			//start
			System.out.println("Entering into else");
			for (Map<String, QueryNode> entry : nodeLists) {
			    for (String key : entry.keySet()) {
			        QueryNode exNode = entry.get(key);
			        System.out.println("RefId = " + exNode.getQueryRefId() + " ParentRefID : " + exNode.getParentQueryRefId());
			        QueryNode parent = search(exNode, parentId);
			        
			        if (parent != null) {
			        	System.out.println("adding child to Parent");
		    	        parent.addChildren("data", node);
		    	        
		    	        System.out.println("parent data = " + parent.toString());
		    	        try {
		    	        ObjectMapper objectMapper = new ObjectMapper();
		    			objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		    			String jsonString = objectMapper.writeValueAsString(parent);
		    			System.out.println("parent data json format = " + jsonString);
		    	        }
		    	        catch(Exception e)
		    	        {
		    	        	e.printStackTrace();
		    	        }
		    	        
		    	        
		    	        break;
		    	    }
			    }
			}
			//end
		
		/*	Iterator qryitr = rootNodes.iterator();
	        while(qryitr.hasNext()) {    
	            QueryNode exNode = (QueryNode) qryitr.next();
	            System.out.println("Existing Root Node DocRefID = " + exNode.getDocumentRefId() +" : QryText = " + "" + exNode.getQueryText()) ;
	            QueryNode parent = search(exNode, parentId);
	            

	    	    if (parent != null) {
	    	        parent.getChildren().add(node);
	    	        break;
	    	    }
	            
	        }*/
	
		}
	}
	
	public List<Map<String,QueryNode>> createQueries()
	{

		QueryNode rootNode1=new QueryNode(1,"1","A12345","This is 1",null);
		
		insert(rootNode1,null);
		
		QueryNode rootNode1_c1=new QueryNode(1001,"1001","A12345","This is 1001","1");
		insert(rootNode1_c1,"1");
		
		QueryNode rootNode1_c1_c1=new QueryNode(10001,"10001","A12345","This is 10001","1001");
		insert(rootNode1_c1_c1,"1001");
		
		QueryNode rootNode2=new QueryNode(2,"2","A12345","This is 2",null);
		insert(rootNode2,null);	
		
		QueryNode rootNode2_c1=new QueryNode(1002,"1002","A12345","This is 1002","2");
		insert(rootNode2_c1,"2");
		
		QueryNode rootNode2_c2=new QueryNode(1003,"1003","A12345","This is 1003","2");
		insert(rootNode2_c2,"2");
		
		QueryNode rootNode3=new QueryNode(3,"3","A12345","This is 3",null);
		insert(rootNode3,null);
		QueryNode rootNode4=new QueryNode(4,"4","A12345","This is 4",null);
		insert(rootNode4,null);
		
		//printing test
	       System.out.println("printing list = ");
		for (Map<String, QueryNode> entry : nodeLists) {
		    for (String key : entry.keySet()) {
		        QueryNode node = entry.get(key);
		        System.out.println("RefId = " + node.getQueryRefId() + " ParentRefID : " + node.getParentQueryRefId());
		 
		    }
		}
		
		//end of printing
				
		return nodeLists;
	}

}
