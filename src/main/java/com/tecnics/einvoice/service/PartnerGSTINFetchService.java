package com.tecnics.einvoice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnics.einvoice.entity.OnboardingRegistrationDetails;
import com.tecnics.einvoice.entity.PartnerGSTINDetails;
import com.tecnics.einvoice.model.PartnerGSTINResponseModel;

@Service
public class PartnerGSTINFetchService {

	

	public PartnerGSTINResponseModel handleJsonJolt(String json) {
		Object transformedOutput = null;
		Object inputJSON = null;
		PartnerGSTINResponseModel bean = null;
		try {
			List chainrSpecJSON = JsonUtils.classpathToList("/static/joltSpec.json");
			Chainr chainr = Chainr.fromSpec(chainrSpecJSON);
			inputJSON = JsonUtils.jsonToObject(json);
			transformedOutput = chainr.transform(inputJSON);
			 bean = new ObjectMapper().readValue(JsonUtils.toJsonString(transformedOutput), PartnerGSTINResponseModel.class);
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
		return bean;
	}
	
	public OnboardingRegistrationDetails handleJsonJoltToOnboardingRegistrationDetails(String json) {
		Object transformedOutput = null;
		Object inputJSON = null;
		OnboardingRegistrationDetails bean = null;
		try {
			List chainrSpecJSON = JsonUtils.classpathToList("/static/joltSpec.json");
			Chainr chainr = Chainr.fromSpec(chainrSpecJSON);
			inputJSON = JsonUtils.jsonToObject(json);
			transformedOutput = chainr.transform(inputJSON);
			 bean = new ObjectMapper().readValue(JsonUtils.toJsonString(transformedOutput), OnboardingRegistrationDetails.class);
			 
			 bean.setOnboardingstatus("In Progress");
				if(bean.getNatureofbusiness_arr().length>=1)
					bean.setNatureofbusiness(String.join(",", bean.getNatureofbusiness_arr()));				
				
		} catch (Exception e) {
			System.out.println("Error in handleJsonJoltToOnboardingRegistrationDetails");
			e.printStackTrace();
		}
		return bean;
	}
	
	public PartnerGSTINDetails handleJsonJoltToPartnerGSTINDetails(String json) {
		Object transformedOutput = null;
		Object inputJSON = null;
		PartnerGSTINDetails bean = null;
		try {
			List chainrSpecJSON = JsonUtils.classpathToList("/static/addGstinJoltSpec.json");
			Chainr chainr = Chainr.fromSpec(chainrSpecJSON);
			inputJSON = JsonUtils.jsonToObject(json);
			transformedOutput = chainr.transform(inputJSON);
			 bean = new ObjectMapper().readValue(JsonUtils.toJsonString(transformedOutput), PartnerGSTINDetails.class);
			 
		
				
		} catch (Exception e) {
			System.out.println("Error in handleJsonJoltToPartnerGSTINDetails met");
			e.printStackTrace();
		}
		return bean;
	}
}
