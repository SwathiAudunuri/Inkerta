package com.tecnics.einvoice.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnics.einvoice.entity.Roles;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.exceptions.InternalException;
import com.tecnics.einvoice.util.APIUtil;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class NavigationController extends BaseController {

	@Autowired
	APIUtil apiUtil;


	@PostMapping("/navitems")
	public ResponseEntity<String> handleGetNavigationItems(@RequestBody Roles roles) {
		  String methodName = "handleGetNavigationItems"; JSONObject response = null;
			try { response = convertAPIResponse(apiUtil.getNavigationItems(roles),
		  methodName); return
		 ResponseEntity.status(HttpStatus.OK).body(response.toString()); } catch
		  (Exception ex) { throw new
		 InternalException(Ex.APP_NAVIGATION_HANDLE_EXCEPTION.getKey(),
		 Ex.APP_NAVIGATION_HANDLE_EXCEPTION.getKeyMessage(), ex); }
		 }


}
