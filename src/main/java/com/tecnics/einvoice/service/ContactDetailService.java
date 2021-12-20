package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ContactDetailRepo;
import com.tecnics.einvoice.entity.ContactDetail;

@Component
public class ContactDetailService {

	@Autowired
	ContactDetailRepo contactDetailRepo;

	public List<ContactDetail> findAll() {
		return (List<ContactDetail>) contactDetailRepo.findAll();
	}

	public Optional<ContactDetail> findById(int id) {
		return (Optional<ContactDetail>) contactDetailRepo.findById(id);
	}

	public ContactDetail update(int id, ContactDetail obj) {
		return contactDetailRepo.save(obj);
	}

	public ContactDetail save(ContactDetail obj) {
		return contactDetailRepo.save(obj);

	}

	public void delete(int id) {
		 contactDetailRepo.deleteById(id);
	}

	public Iterable<ContactDetail> saveAll(List<ContactDetail> obj) {
		return contactDetailRepo.saveAll(obj);
	}


}
