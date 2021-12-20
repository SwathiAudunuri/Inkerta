package com.tecnics.einvoice.file.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FileManagerService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private Environment env;

	public ResponseEntity<CustomeResponseEntity> onboardingFolderCreate(CreateFolderRequest request) {
		ResponseEntity<CustomeResponseEntity> response = null;
		String methodname = "onboardingFolderCreate";
		try {
		} catch (Exception e) {
			System.out.println(methodname + e);
		} finally {
		}
		return response;
	}

	public ResponseEntity<CustomeResponseEntity> productsFolderCreate(CreateFolderRequest request) {
		ResponseEntity<CustomeResponseEntity> response = null;
		String methodname = "productsFolderCreate";
		try {

			response = restTemplate.postForEntity(env.getProperty("products.folder.create.url"), request,
					CustomeResponseEntity.class);
		} catch (Exception e) {
			System.out.println(methodname + e);
		} finally {
		}
		return response;
	}

	public String fileUpload(DocUploadRequest request) {
		ResponseEntity<CustomeResponseEntity> response = null;
		String methodname = "productsFolderCreate";
		try {

			response = restTemplate.postForEntity(env.getProperty("document.upload.url"), request,
					CustomeResponseEntity.class);

			FileIdResponse fileResp = (FileIdResponse) response.getBody().getResults();
			return fileResp.getFileId();
		} catch (Exception e) {
			System.out.println(methodname + e);
		}
		return null;
	}

	public ResponseEntity<CustomeResponseEntity> getFilePath(String docId) {
		ResponseEntity<CustomeResponseEntity> response = null;
		String methodname = "productsFolderCreate";
		try {

			response = restTemplate.getForEntity(env.getProperty("get.file.url") + docId, CustomeResponseEntity.class);
		} catch (Exception e) {
			System.out.println(methodname + e);
		}
		return response;
	}

}
