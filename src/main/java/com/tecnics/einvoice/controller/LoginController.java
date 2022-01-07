package com.tecnics.einvoice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.exceptions.InternalException;
import com.tecnics.einvoice.log.BaseLogger;
import com.tecnics.einvoice.model.ResponseMessage;
import com.tecnics.einvoice.model.UserCredential;
import com.tecnics.einvoice.util.APIError;
import com.tecnics.einvoice.util.APIUtil;
import com.tecnics.einvoice.util.HashingUtil;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LoginController extends BaseController{
	BaseLogger logger = BaseLogger.getBaseLogger(LoginController.class);
	@Autowired
	private APIUtil apiUtil;
	@Autowired
	private HashingUtil hashingUtil;



	/**
	 * Primary endpoint for user login
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	@PostMapping(value = "/login")
	public ResponseEntity<ResponseMessage> handleLogon(@RequestBody UserCredential user) {
		try {
			if((user.getUser()!=null && user.getUser().equals("")) || (user.getPassword()!=null && user.getPassword().equals("")))
				return ResponseEntity.ok().body(new ResponseMessage(new APIError(Ex.LOGIN_USERID_PASSWORD_EMPTY.getKey(),
									Ex.LOGIN_USERID_PASSWORD_EMPTY.getKeyMessage())));
					
			ResponseEntity<ResponseMessage> response = (apiUtil.handleLogon(user.getUser(), hashingUtil.encryptKey(user.getPassword())));
			return response;

		} catch (Exception ex) {
			System.err.println(ex);
			log.logErrorMessage(ex.getMessage(), ex);
			throw new InternalException(Ex.LOGIN_HANDLE_EXCEPTION.getKey(),
					Ex.formatMessage(Ex.LOGIN_HANDLE_EXCEPTION.getKeyMessage(), new Object[] { user }), ex);
		}

	}

	/**
	 * End point test url to check service status
	 * 
	 * @return
	 */
	@PostMapping("/refresh_token")
	public String refreshToken(@RequestParam("securityToken") String securityToken) {
		return apiUtil.refreshToken(securityToken);
	}

}
