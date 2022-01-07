package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.OutboundConnectors;


public interface OutboundConnectorsRepo extends CrudRepository<OutboundConnectors, Integer>{
	
	
	List<OutboundConnectors> findByPartnerId(String partnerId);

}


