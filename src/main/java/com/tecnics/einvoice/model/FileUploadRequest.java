/**
 * 
 */
package com.tecnics.einvoice.model;

import java.util.List;
public class FileUploadRequest {
	  public String documentName;
	    public String documentBase64;
	    public String addedBy;
	    public String mimeType;
	    public String className;
	    public String folderId;
	    public List<Property> properties;
	
		public String getDocumentName() {
			return documentName;
		}
		public void setDocumentName(String documentName) {
			this.documentName = documentName;
		}
		public String getDocumentBase64() {
			return documentBase64;
		}
		public void setDocumentBase64(String documentBase64) {
			this.documentBase64 = documentBase64;
		}
		public String getAddedBy() {
			return addedBy;
		}
		public void setAddedBy(String addedBy) {
			this.addedBy = addedBy;
		}
		public String getMimeType() {
			return mimeType;
		}
		public void setMimeType(String mimeType) {
			this.mimeType = mimeType;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getFolderId() {
			return folderId;
		}
		public void setFolderId(String folderId) {
			this.folderId = folderId;
		}
		public List<Property> getProperties() {
			return properties;
		}
		public void setProperties(List<Property> properties) {
			this.properties = properties;
		}
	    
		
		@Override
		public String toString() {
			return "FileUploadRequest [documentName=" + documentName + ", documentBase64=" + documentBase64
					+ ", addedBy=" + addedBy + ", mimeType=" + mimeType + ", className=" + className + ", folderId="
					+ folderId + ", properties=" + properties + "]";
		}
	    
}

class Property{
    public String name;
    public String value;
	@Override
	public String toString() {
		return "Property [name=" + name + ", value=" + value + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Property(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	public Property() {
		super();
	}
	
}