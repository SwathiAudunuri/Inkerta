package com.tecnics.einvoice.alfresco;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.exceptions.InternalException;
import com.tecnics.einvoice.model.FolderResponse;

@Component
public class AlfrescoService {

	
	@Autowired
	RestTemplate restTemplate;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}


	public String callcreateFolder(FolderRequest folderRequest) {
		FolderResponse folderId = restTemplate.postForObject("http://172.16.6.25:8080/alfresco/api/createRegistrationFolder", folderRequest,FolderResponse.class);
		folderId.getResults(); 
		return folderId.getFolderID(); 
		
		
	}


	


	public List<AlfrescoUploadResponse> addDocuments(List<AlfrescoFileUploadRequest> obj) {
		AlfrescoFileUploadResponseList response = restTemplate.postForObject("http://172.16.6.25:8080/alfresco/api/addDocuments", obj,AlfrescoFileUploadResponseList.class);
		return response.getResults();
	}
	
	public String createInvoiceFolder(InvoiceFolderCreate obj) {
		FolderResponse folderId = restTemplate.postForObject("http://172.16.6.25:8080/alfresco/api/createInvoiceFolder", obj,FolderResponse.class);
		folderId.getResults();
		if(folderId.getFolderID()==null) {
            throw new InternalException(Ex.ALFRESCO_FOLDER_CREATE.getKey(),Ex.formatMessage(Ex.ALFRESCO_FOLDER_CREATE.getKeyMessage(),new String[]{}));
		}
		return folderId.getFolderID();
	} 
	
}
