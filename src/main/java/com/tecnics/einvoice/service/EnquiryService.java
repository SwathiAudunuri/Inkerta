package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.EnquiryActivityRepo;
import com.tecnics.einvoice.Repo.EnquiryRepo;
import com.tecnics.einvoice.entity.Enquiry;
import com.tecnics.einvoice.entity.EnquiryActivity;

@Component
public class EnquiryService {

	@Autowired
	EnquiryRepo enquiryRepo;

	@Autowired
	EnquiryActivityRepo enquiryActivityRepo;

	public List<Enquiry> findAll() {
		return (List<Enquiry>) enquiryRepo.findAll();
	}

	public Optional<Enquiry> findById(int id) {
		return (Optional<Enquiry>) enquiryRepo.findById(id);
	}



	public void delete(int id) {
		enquiryRepo.deleteById(id);
	}

	public Enquiry save(Enquiry obj, String username) {
		Enquiry response = new Enquiry();
		try {
			response = enquiryRepo.save(obj);
			for (EnquiryActivity log : obj.getEnquiryActivities()) {
				log.setEnquiry(obj);
				log.setActionBy(username);
				enquiryActivityRepo.save(log);
			}
		} catch (Exception e) {
			System.err.println("error in update"+e);
		}

		return response;
	}

}
