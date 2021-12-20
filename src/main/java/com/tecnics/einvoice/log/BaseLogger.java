/*
 * Licensed Materials - Property of Tecnics Integration Technologies Pvt Ltd.
 *   (C) Copyright Tecnics Corp. 2021
 */

package com.tecnics.einvoice.log;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * <p>Application Logger</p>
 * @author  shashi kumar
 * @version  1.0
 * @since  1.0
 */
public class BaseLogger {

	private static  InputStream inputStream;
	private  static Properties properties;
	private  Logger logger = null;
	private Logger errorLogger  = null;
	
	static {
		// set system property 
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 System.setProperty("current.date", dateFormat.format(new Date()).toString().replace(":", "_"));
	}

	/**
	 *
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static  void initialize() throws FileNotFoundException , IOException {
		if(properties == null)
			properties =  new Properties();
		if(inputStream == null)
			inputStream = BaseLogger.class.getResourceAsStream("/static/log4j.properties");
		properties.load(inputStream);
	}

	/**
	 *
	 * @param clazzName logger class name
	 */
	protected  BaseLogger(String  clazzName) {
		try {
			this.logger = Logger.getLogger(clazzName);
			this.errorLogger = Logger.getLogger("ExceptionAppender");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * @param clazz - logger class name {@link Class}
	 * @return
	 */
	public static  BaseLogger getBaseLogger(@SuppressWarnings("rawtypes") Class clazz) {
		try {
			initialize();
			PropertyConfigurator.configure(properties);
			return  new BaseLogger(clazz.getName());
		} catch (Exception e) {}
		return null;
	}

	/**
	 *
	 * @return {@code BaseLogger}
	 */
	private Logger getLogger() {
		return this.logger;
	}

	/**
	 *
	 * @return {@Code Logger}
	 */
	private Logger getErrorLogger() {
		return this.errorLogger;
	}

	/**
	 *
	 * @return {@code true} if info enabled
	 */
	public boolean isInfoEnabled() {
		return getLogger().isEnabledFor(Level.INFO);
	}

	/**
	 *
	 * @return {@code true} if trace enabled
	 */
	public boolean isTraceEnabled() {
		return getLogger().isEnabledFor(Level.TRACE);
	}

	/**
	 *
	 * @return {@code true} if debug enabled
	 */
	public boolean isDebugEnabled() {
		return getLogger().isEnabledFor(Level.DEBUG);
	}

	/**
	 *
	 * @return {@code true} if error enabled
	 */
	public boolean isErrorEnabled() {
		return getErrorLogger().isEnabledFor(Level.ERROR);
	}

	/**
	 *
	 * @param methodName - Logs Method Entry Details
	 */
	public void logMethodEntry(String methodName) {
		if(isTraceEnabled())
			getLogger().log(BaseLogger.class.getName(), Level.INFO, "Enter "+ methodName, null);
	}

	/**
	 *
	 * @param methodName  Logs Method End Details
	 */
	public void logMethodEnd(String methodName) {
		if(isTraceEnabled())
			getLogger().log(BaseLogger.class.getName(), Level.INFO, "End "+ methodName, null);
	}

	/**
	 *
	 * @param methodName - Method Name for trace
	 * @param params - list of params
	 */
	public void logMethodEntryTrace(String methodName,String... params) {
		if(isTraceEnabled())
			getLogger().log(BaseLogger.class.getName(), Level.INFO, "Enter "+ methodName +" \n" + getTraceParams(params), null);
	}

	/**
	 *
	 * @param methodName
	 * @param params1
	 * @param params
	 */
	public void logMethodEntryTrace(String methodName,String[] params1,String... params) {
		if(isTraceEnabled())
			getLogger().log(BaseLogger.class.getName(), Level.INFO, "Enter "+ methodName +" \n" + getTraceParams(params1) + "\n"+getTraceParams(params), null);
	}

	/**
	 *
	 * @param params
	 * @return
	 */
	private String getTraceParams(String... params) {
		String param = "";
		if(params != null) {
			for(String s : params) {
				param += s + " -- ";
			}
		}
		return param;
	}

	/**
	 *
	 * @param message
	 */
	public void logInfoMessage(String message) {
		if(isInfoEnabled())
			getLogger().log(BaseLogger.class.getName(), Level.INFO,message, null);
	}

	/**
	 *
	 * @param message
	 */
	public void logDebugMessage(String message) {
		if(isDebugEnabled())
			getLogger().log(BaseLogger.class.getName(), Level.DEBUG,message, null);
	}

	/**
	 *
	 * @param message
	 * @param params
	 */
	public void logDebugMessage(String message,String... params) {
		if(isDebugEnabled())
			getLogger().log(BaseLogger.class.getName(), Level.DEBUG,formatParams(message, params), null);
	}

	/**
	 *
	 * @param message
	 */
	public void logTraceMessage(String message) {
		if(isTraceEnabled())
			getLogger().log(BaseLogger.class.getName(), Level.TRACE,message, null);
	}

	/**
	 *
	 * @param message
	 * @param params
	 */
	public void logDetailedInfoMessage(String message,String... params) {
		if(isInfoEnabled()) 
			getLogger().log(BaseLogger.class.getName(), Level.INFO,formatMessage(message, params), null);
	}

	/**
	 *
	 * @param message
	 * @param e
	 */
	public void logErrorMessage(String message,Throwable e) {
		if(isErrorEnabled())
			getErrorLogger().log(BaseLogger.class.getName(), Level.ERROR,message, e);
	}

	/**
	 *
	 * @param message
	 * @param formats
	 * @return
	 */
	private String formatMessage(String message , String... formats) {
		for(int x = 0; x < formats.length ; x++) {
			message  = message.replace("{"+(x+1)+"}",formats[x]);
		}
		return message;
	}

	/**
	 *
	 * @param message
	 * @param formats
	 * @return
	 */
	private String formatParams(String message , String... formats) {
		StringBuilder sb = new StringBuilder();
		for(int x = 0; x < formats.length ; x++) {
			sb.append(formats[x]).append(" ");
		}
		return message.concat(" --").concat(sb.toString());
	}


}
