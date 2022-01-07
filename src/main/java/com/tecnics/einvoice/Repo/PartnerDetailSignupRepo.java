package com.tecnics.einvoice.Repo;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.tecnics.einvoice.entity.PartnerDetails;
import com.tecnics.einvoice.entity.PartnerDetailSignup;

public interface PartnerDetailSignupRepo extends CrudRepository<PartnerDetailSignup, String> {


	
}
