package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.CompanyBankDetailRepo;
import com.tecnics.einvoice.entity.CompanyBankDetail;

@Component
public class CompanyBankDetailService {

	@Autowired
	CompanyBankDetailRepo companyBankDetailRepo;

	public List<CompanyBankDetail> findAll() {
		return (List<CompanyBankDetail>) companyBankDetailRepo.findAll();
	}

	public Optional<CompanyBankDetail> findById(int id) {
		return (Optional<CompanyBankDetail>) companyBankDetailRepo.findById(id);
	}

	public CompanyBankDetail update(int id, CompanyBankDetail obj) {
		return companyBankDetailRepo.save(obj);
	}

	public CompanyBankDetail save(CompanyBankDetail obj) {
		return companyBankDetailRepo.save(obj);

	}

	public void delete(int id) {
		 companyBankDetailRepo.deleteById(id);
	}


	public Iterable<CompanyBankDetail> saveAll(List<CompanyBankDetail> obj) {
		return companyBankDetailRepo.saveAll(obj);
	}

}
