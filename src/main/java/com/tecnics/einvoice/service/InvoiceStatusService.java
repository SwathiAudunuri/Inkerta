package com.tecnics.einvoice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.ErrorLogRepo;
import com.tecnics.einvoice.Repo.InvoiceQueryRepo;
import com.tecnics.einvoice.Repo.InvoiceStatusRepo;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.entity.InvoiceQuery;
import com.tecnics.einvoice.entity.InvoiceStatus;

import com.tecnics.einvoice.response.TransactionResponse;

@Component
public class InvoiceStatusService extends BaseService {
	
	@Autowired
	InvoiceStatusRepo invoiceStatusRepo;
	
	@Autowired
	ErrorLogRepo errorLogRepo;
	

	public List<InvoiceStatus> findAll() {
		return (List<InvoiceStatus>) invoiceStatusRepo.findAll();
	}
	
	
	public List<InvoiceStatus> findByPartnerTypeAndStatusKey(String partnerType,String statusKey) {
		return (List<InvoiceStatus>) invoiceStatusRepo.findByPartnerTypeAndStatusKey(partnerType,statusKey);
	}
	
	
}
