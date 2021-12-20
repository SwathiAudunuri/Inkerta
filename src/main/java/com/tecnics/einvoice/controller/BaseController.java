package com.tecnics.einvoice.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tecnics.einvoice.constants.Constants;
import com.tecnics.einvoice.dao.UserDAO;
import com.tecnics.einvoice.entity.ErrorLog;
import com.tecnics.einvoice.exceptions.Ex;
import com.tecnics.einvoice.exceptions.InternalException;
import com.tecnics.einvoice.lang.Assert;
import com.tecnics.einvoice.log.BaseLogger;
import com.tecnics.einvoice.model.User;
import com.tecnics.einvoice.model.UserLoginDetails;
import com.tecnics.einvoice.service.ErrorLogService;
import com.tecnics.einvoice.util.JWTUtil;

/**
 * <p>
 * This is the base controller for all rest api end points
 * </p>
 * 
 * @since 1.0
 * @version 1.0
 */
public abstract class BaseController {

	BaseLogger log = BaseLogger.getBaseLogger(BaseController.class);

	@Autowired
	JWTUtil jWTUtil;
	
	@Autowired
	ErrorLogService errorLogService;
	
	@Autowired
	UserDAO userdao;

	/**
	 *
	 * @param ex a custom exception for all end points {@link InternalException}
	 * @return - returns a custom exception for end point
	 */
	@ExceptionHandler
	ResponseEntity<JSONObject> handleException(InternalException ex) {
		Assert.isNotNull(ex, "handleException must not null");
		return new ResponseEntity<JSONObject>(formatErrorMessage(ex), HttpStatus.OK); // convert api to end point
																						// response
	}

	/**
	 *
	 * @param ex - a custom exception for all end points {@link InternalException}
	 * @return - custom exception message as endpoint response . {@link JSONObject}
	 * 
	 *         <Pre>
	 *     {
	 *         "hasError" : true/false,
	 *         "results" : {@link JSONObject}
	 *         "timestamp" : "format of {dd-MM-yyyy hhmmss}",
	 *         "error" : {
	 *             "errorCode" : "XXXXXXX",
	 *             "errorMessage" : "Message",
	 *             "errorTrace" : "{@link StackTraceElement}"
	 *         }
	 *
	 *     }
	 *         </Pre>
	 *
	 */
	private JSONObject formatErrorMessage(InternalException ex) {
		String methodName = "formatErrorMessage";
		JSONObject exceptionMessage = null;
		try {
			exceptionMessage = new JSONObject();
			exceptionMessage.put(Constants.HAS_ERROR, true);
			JSONObject error = new JSONObject();
			error.put(Constants.ERROR_CODE, ex.getCode());
			error.put(Constants.ERROR_MESSAGE, ex.getMessage());
			StringWriter errorWriter = new StringWriter();
			if (ex.getThrowable() != null) {
				ex.getThrowable().printStackTrace(new PrintWriter(errorWriter));
			} else {
				ex.printStackTrace(new PrintWriter(errorWriter));
			}
			error.put(Constants.ERROR_TRACE, errorWriter.toString());
			exceptionMessage.put(Constants.ERROR, error);
			exceptionMessage.put(Constants.API_TIMESTAMP, new SimpleDateFormat("MM-dd-YYYY:hhmmss").format(new Date()));
		} catch (Exception e) {
			System.err.println(e);
		}
		return exceptionMessage;
	}

	/**
	 *
	 * @return - custom exception message as endpoint response . {@link JSONObject}
	 * 
	 *         <Pre>
	 *     {
	 *         "hasError" : true/false,
	 *         "results" : {@link JSONObject}
	 *         "timestamp" : "format of {dd-MM-yyyy hhmmss}",
	 *         "error" : {
	 *             "errorCode" : "XXXXXXX",
	 *             "errorMessage" : "Message",
	 *             "errorTrace" : "{@link StackTraceElement}"
	 *         }
	 *
	 *     }
	 *         </Pre>
	 *
	 */
	public JSONObject convertAPIResponse(JSONObject params, String handleName) throws InternalException {
		String methodName = "convertAPIResponse";
		JSONObject api = new JSONObject();
		Assert.isNotNull(params, "convertAPIResponse Null Params");
		try {
			api.put(Constants.HAS_ERROR, false);
			api.put(Constants.API_RESULTS, params);
		} catch (Exception ex) {
			System.err.println(ex);
			throw new InternalException(Ex.WEB_API_RES_CONVERT.getKey(),
					Ex.formatMessage(Ex.WEB_API_RES_CONVERT.getKeyMessage(), new String[] { handleName }));
		}
		return api;
	}

	/**
	 *
	 * @return - custom exception message as endpoint response . {@link JSONObject}
	 * 
	 *         <Pre>
	 *     {
	 *         "hasError" : true/false,
	 *         "results" : {@link JSONObject}
	 *         "timestamp" : "format of {dd-MM-yyyy hhmmss}",
	 *         "error" : {
	 *             "errorCode" : "XXXXXXX",
	 *             "errorMessage" : "Message",
	 *             "errorTrace" : "{@link StackTraceElement}"
	 *         }
	 *
	 *     }
	 *         </Pre>
	 *
	 */
	public JSONObject convertAPIResponse(JSONArray params, String handleName) throws InternalException {
		String methodName = "convertAPIResponse";
		JSONObject api = new JSONObject();
		Assert.isNotNull(params, "convertAPIResponse Null Params");
		try {
			api.put(Constants.HAS_ERROR, false);
			api.put(Constants.API_RESULTS, params);
		} catch (Exception ex) {
			System.err.println(ex);
			throw new InternalException(Ex.WEB_API_RES_CONVERT.getKey(),
					Ex.formatMessage(Ex.WEB_API_RES_CONVERT.getKeyMessage(), new String[] { handleName }));
		}
		return api;
	}



	/**
	 * Method inputs the Bearer Token Id and generates user Id from valid token
	 * @param token
	 * @return
	 */
	public String getUserName(String token) {
		String username = null;
		try {
			if (token != null && token.indexOf("Bearer") >= 0) { // header and not ignore
				token = token.substring(7);
				username = jWTUtil.getUserIdFromToken(token);
			}
		} catch (Exception e) {
			System.err.println(e);
			log.logErrorMessage("could not fetch user from token", e);
			return username;
		}
		return username;
	}
	
	public UserLoginDetails getUserObjFromToken(String token) {
		
		UserLoginDetails userObj = null;
		try {
			if (token != null && token.indexOf("Bearer") >= 0) { // header and not ignore
				token = token.substring(7);
				userObj = jWTUtil.getUserObjFromToken(token);
			}
		} catch (Exception e) {
			log.logErrorMessage("could not fetch user Object token", e);
			return userObj;
		}
		return userObj;
	}
	
	
	
	/**
	 * Method inputs the Bearer Token Id and generates user Id from valid token
	 * @param token
	 * @return
	 */
	public UserLoginDetails getUserDetails(String userId) {
		UserLoginDetails userDetails = new UserLoginDetails();
		User user = userdao.getUser(userId);
		userDetails.setUserId(userId);
		userDetails.setPartnerName(user.getPartnerName());
		userDetails.setRoles(user.getRoles());
		userDetails.setEmail(user.getEmail());
		userDetails.setPartnerId(user.getPartnerId());
		userdao.logUserSession(user);
		return userDetails;
	}
	
	
	/**@return - custom exception message as endpoint response 
	 * Common Method to log error messages - For all modules
	 * @param errlog
	 */
		public void logError(ErrorLog errlog) {
			try {
				errorLogService.save(errlog);
			}
			catch(Exception e) {
				System.err.println(e);
				log.logErrorMessage("Failed to load Error" + e.getMessage(), e);
				log.logInfoMessage(errlog.toString());
			}
			
		}
			
	/**
	 * Method to extract StackTrace as a String
	 */
		public String getStackTrace(Throwable aThrowable) {
			final Writer result = new StringWriter();
			final PrintWriter printWriter = new PrintWriter(result);
			aThrowable.printStackTrace(printWriter);
			return result.toString();
		}
		
		
		
	
}
