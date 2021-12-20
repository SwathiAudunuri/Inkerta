package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.NotificationsRepo;
import com.tecnics.einvoice.entity.NotificationsEntitiy;

@Component
public class NotificationsService {
	
	@Autowired
	NotificationsRepo notificationsRepo;

	public List<NotificationsEntitiy> findAll() {
		return (List<NotificationsEntitiy>) notificationsRepo.findAll();
	}

	public Optional<NotificationsEntitiy> findById(int id) {
		return notificationsRepo.findById(id);
	}

	public NotificationsEntitiy save(NotificationsEntitiy obj, String userName) {
		return notificationsRepo.save(obj);
	}

	public void delete(int id) {
		notificationsRepo.deleteById(id);
	}



}
