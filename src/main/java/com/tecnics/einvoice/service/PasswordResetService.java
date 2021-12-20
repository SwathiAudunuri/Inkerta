package com.tecnics.einvoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tecnics.einvoice.Repo.AuthenticationRepo;
import com.tecnics.einvoice.Repo.PasswordResetRepo;
import com.tecnics.einvoice.Repo.UserAuthorizationKeyRepo;
import com.tecnics.einvoice.Repo.UserManagementRepo;
import com.tecnics.einvoice.entity.Authorizationskeys;
import com.tecnics.einvoice.entity.PasswordReset;
import com.tecnics.einvoice.entity.PasswordResetRequest;
import com.tecnics.einvoice.entity.UserAuthorizationKey;
import com.tecnics.einvoice.entity.UserManagement;
import com.tecnics.einvoice.util.HashingUtil;

@Component
public class PasswordResetService {

	public PasswordResetService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private PasswordResetRepo passwordResetRepo;

	@Autowired
	private AuthenticationRepo authenticationRepo;

	@Autowired
	private UserAuthorizationKeyRepo userAuthorizationKeyRepo;

	@Autowired
	private UserManagementRepo managementRepo;

	@Autowired
	private HashingUtil hashingUtil;

	public PasswordResetService(PasswordResetRepo passwordResetRepo, AuthenticationRepo authenticationRepo,
			UserManagementRepo managementRepo) {
		this.passwordResetRepo = passwordResetRepo;
		this.authenticationRepo = authenticationRepo;
		this.managementRepo = managementRepo;
	}

	public PasswordReset generatePasswordResetid(String user_id) {
		PasswordReset obj = new PasswordReset();
		try {
			obj.setUser_id(user_id);
			return passwordResetRepo.save(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return passwordResetRepo.save(obj);
	}

	public PasswordResetRequest resetPassword(PasswordResetRequest obj) {
		return null;
	}

	public String updatePassword(PasswordResetRequest request) {
		Authorizationskeys authorizationKeys = authenticationRepo.findByUserId(request.getUser_id());
		UserManagement userManagement = managementRepo.findByUserId(request.getUser_id());

		userManagement.setUser_alias(request.getAliasName());

		authorizationKeys.setAuthorizationKey(hashingUtil.encryptKey(request.getPassword()));
		authorizationKeys.setUser_id(request.getUser_id());
		try {
			managementRepo.save(userManagement);
			authenticationRepo.save(authorizationKeys);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
		return "Successfully Updated..!";
	}
}
