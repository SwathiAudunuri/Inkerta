package com.tecnics.einvoice.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryNode {	
	    
	    private Integer id;
		
		private String queryRefId;

		private String documentRefId;
		

		private String queryText;

	
		private String parentQueryRefId;
		
		public QueryNode(Integer id,String queryRefId,String documentRefId,String queryText,String parentQueryRefId)
		{
			this.id=id;
			this.queryRefId=queryRefId;
			this.documentRefId=documentRefId;
			this.queryText=queryText;
			this.parentQueryRefId=parentQueryRefId;
			children=new ArrayList();
		}

		private List<Map<String,QueryNode>> children;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getQueryRefId() {
			return queryRefId;
		}

		public void setQueryRefId(String queryRefId) {
			this.queryRefId = queryRefId;
		}

		public String getDocumentRefId() {
			return documentRefId;
		}

		public void setDocumentRefId(String documentRefId) {
			this.documentRefId = documentRefId;
		}

		public String getQueryText() {
			return queryText;
		}

		public void setQueryText(String queryText) {
			this.queryText = queryText;
		}

		public String getParentQueryRefId() {
			return parentQueryRefId;
		}

		public void setParentQueryRefId(String parentQueryRefId) {
			this.parentQueryRefId = parentQueryRefId;
		}

		public List<Map<String,QueryNode>> getChildren() {
			return children;
		}

		public void setChildren(List<Map<String,QueryNode>> children) {
			this.children = children;
		}
		
		public void addChildren(String keyName, QueryNode child)
		{
			HashMap<String,QueryNode> childMap=new HashMap<String,QueryNode>();
			childMap.put("data", child);
			children.add(childMap);
		}

	}


