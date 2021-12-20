package com.tecnics.einvoice.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class InvoiceQueryTreeEntity {
	
	public InvoiceQueryTreeRow data=null;
	public InvoiceQueryTreeEntity(InvoiceQueryTreeRow data)
	{
		this.data=data;
	}
	
	public InvoiceQueryTreeRow getData() {
		return data;
	}
	public void setData(InvoiceQueryTreeRow data) {
		this.data = data;
	}

	List<InvoiceQueryTreeEntity> children=null;
	
	public List<InvoiceQueryTreeEntity> getChildren() {
		return children;
	}
	public void setChildren(List<InvoiceQueryTreeEntity> children) {
		this.children = children;
	}
	
	public void addChildren(InvoiceQueryTreeEntity child)
	{	
		if(children==null)
			children=new ArrayList<InvoiceQueryTreeEntity>();
		children.add(child);
	}

	


}
