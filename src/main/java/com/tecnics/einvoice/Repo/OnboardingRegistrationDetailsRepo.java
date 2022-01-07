package com.tecnics.einvoice.Repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.tecnics.einvoice.entity.OnboardingRegistrationDetails;


public interface OnboardingRegistrationDetailsRepo extends CrudRepository<OnboardingRegistrationDetails, String> {
	
	List<OnboardingRegistrationDetails> findByGstin(String gstin);
	Optional<OnboardingRegistrationDetails> findByGstinAndOnboardingstatus(String gstin, String onboardingstatus);
	//@Query("SELECT t FROM onboarding_registration_details t where t.gstin=:gstin and t.onboardingStatus=:onboardingStatus")
	//Optional<OnboardingRegistrationDetails>  findByGstinAndOnboardingStatus( @Param ("gstin") String gstin,@Param ("onboardingStatus") String onboardingStatus);

}
