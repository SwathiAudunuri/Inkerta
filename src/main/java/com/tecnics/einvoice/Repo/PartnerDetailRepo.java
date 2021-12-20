package com.tecnics.einvoice.Repo;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.tecnics.einvoice.entity.PartnerDetail;

public interface PartnerDetailRepo extends CrudRepository<PartnerDetail, String> {

	List<PartnerDetail> findByPartnerId(String partnerId);

	
}
