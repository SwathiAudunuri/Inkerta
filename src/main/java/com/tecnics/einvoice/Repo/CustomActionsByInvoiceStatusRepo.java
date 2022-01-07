package com.tecnics.einvoice.Repo;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import com.tecnics.einvoice.entity.CustomActionsByInvoiceStatus;
import com.tecnics.einvoice.constants.SQLQueries;

public interface CustomActionsByInvoiceStatusRepo  extends CrudRepository<CustomActionsByInvoiceStatus, Integer> {
	List<CustomActionsByInvoiceStatus> findByInvoiceStatus(String invoiceStatus);
	List<CustomActionsByInvoiceStatus> findByActionId(Integer actionId);

}
