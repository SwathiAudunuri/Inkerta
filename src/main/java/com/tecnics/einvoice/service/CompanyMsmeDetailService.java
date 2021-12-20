package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.CompanyMsmeDetailRepo;
import com.tecnics.einvoice.entity.CompanyMsmeDetail;

@Component
public class CompanyMsmeDetailService {

	@Autowired
	CompanyMsmeDetailRepo companyMsmeDetail;

	public List<CompanyMsmeDetail> findAll() {
		return (List<CompanyMsmeDetail>) companyMsmeDetail.findAll();
	}

	public Optional<CompanyMsmeDetail> findById(int id) {
		return (Optional<CompanyMsmeDetail>) companyMsmeDetail.findById(id);
	}

	public CompanyMsmeDetail update(int id, CompanyMsmeDetail obj) {
		return companyMsmeDetail.save(obj);
	}

	public CompanyMsmeDetail save(CompanyMsmeDetail obj) {
		return companyMsmeDetail.save(obj);

	}

	public void delete(int id) {
		 companyMsmeDetail.deleteById(id);
	}

	public Iterable<CompanyMsmeDetail> saveAll(List<CompanyMsmeDetail> obj) {
		return companyMsmeDetail.saveAll(obj);
	}

}
