package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.OutboundConnectorsGSTIN;

public interface OutboundConnectorsGSTINRepo  extends CrudRepository<OutboundConnectorsGSTIN, Integer>{
	
	OutboundConnectorsGSTIN findByGstin(String gstin);
	List<OutboundConnectorsGSTIN> findByConnectorId(Integer connectorId);

}