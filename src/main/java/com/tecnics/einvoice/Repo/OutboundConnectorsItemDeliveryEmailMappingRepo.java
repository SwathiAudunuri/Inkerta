package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.OutboundConnectorsAutopost;
import com.tecnics.einvoice.entity.OutboundConnectorsItemDeliveryEmailMapping;


public interface OutboundConnectorsItemDeliveryEmailMappingRepo extends CrudRepository<OutboundConnectorsItemDeliveryEmailMapping, Integer>{
	
	List<OutboundConnectorsItemDeliveryEmailMapping> findByConnectorId(Integer connectorId);


}
