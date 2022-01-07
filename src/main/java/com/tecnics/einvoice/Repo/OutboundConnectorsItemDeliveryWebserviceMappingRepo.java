package com.tecnics.einvoice.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.OutboundConnectorsAutopost;
import com.tecnics.einvoice.entity.OutboundConnectorsItemDeliveryWebserviceMapping;


public interface OutboundConnectorsItemDeliveryWebserviceMappingRepo extends CrudRepository<OutboundConnectorsItemDeliveryWebserviceMapping, Integer>{
	
	List<OutboundConnectorsItemDeliveryWebserviceMapping> findByConnectorId(Integer connectorId);


}

