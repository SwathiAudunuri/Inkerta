package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.CompanyGstinDetailRepo;
import com.tecnics.einvoice.entity.CompanyGstinDetail;

@Component
public class CompanyGstinDetailService {

	@Autowired
	CompanyGstinDetailRepo companyGstinDetailRepo;

	public List<CompanyGstinDetail> findAll() {
		return (List<CompanyGstinDetail>) companyGstinDetailRepo.findAll();
	}

	public CompanyGstinDetail findByGstin(String gstin) {
		return (CompanyGstinDetail) companyGstinDetailRepo.findByGstin(gstin);
	}
	
	

	public CompanyGstinDetail update(int id, CompanyGstinDetail obj) {
		return companyGstinDetailRepo.save(obj);
	}

	public CompanyGstinDetail save(CompanyGstinDetail obj) {
		return companyGstinDetailRepo.save(obj);

	}

	public void delete(int id) {
		 companyGstinDetailRepo.deleteById(id);
	}

	public Iterable<CompanyGstinDetail> saveAll(List<CompanyGstinDetail> obj) {
		return companyGstinDetailRepo.saveAll(obj);
	}



}
