package com.tecnics.einvoice.config;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.lang.NonNull;

/**
 * {@code ConfigFactory} is a configuration class used for reading the collections of data from configuration properties
 *
 * <h1>Usage Scenarios</h1>
 * <ul>
 *     <li> <strong>Read Integers </strong> : to know more @see getValueAsInt</li>
 *     <li> <strong>Read Strings </strong> : to know more @see getValueAsString</li>
 *     <li> <strong>Read Secured Strings </strong> : to know more @see getValueAsSecuredString</li>
 *     <li> <strong>Read Booleans </strong> : to know more @see getValueAsBoolean</li>
 * </ul>
 *

 *
 */
public class ConfigFactory {
	private static Properties properties;
	private static InputStream inputStream;
	private static InputStream appInputStream;
	private static JSONObject appJson = null;

	/**
	 *<p> This is a private method initializes  the configuration store . </p>
	 * @throws FileNotFoundException this exception throws if   {@code inputStream} or {@code properties} is null
	 * @throws IOException  this exception throws if   {@code inputStream} or {@code properties} is null
	 * @since  v1.0
	 */
	private static void initialize()  throws FileNotFoundException , IOException {
		if(properties == null)
			properties = new Properties();
		
		if(inputStream == null)
			inputStream = ConfigFactory.class.getResourceAsStream("/static/config.properties");
		properties.load(inputStream);

		if(appInputStream == null) {
			appInputStream = ConfigFactory.class.getResourceAsStream("/static/PrivateRoutes.json");
			//appJson = JSONObject.parse(appInputStream);
			appJson =new JSONObject(new JSONTokener(appInputStream));

		}

	}


	/**
	 *Constructs a String reading from configuration properties file
	 * @param key  this key must not null {@code null}
	 * @return value associated with key from the properties file
	 */
	public static String getValueAsString(String key) {
		String value  = null;
		try {
			initialize();
			value  = properties.getProperty(key);
		} catch (Exception genericException) {
			genericException.printStackTrace();
		}
		return value;
	}

	/**
	 *Constructs a integer reading from configuration properties file
	 * @param key  this key must not null {@code null}
	 * @return value associated with key from the properties file
	 */
	public static int getValueAsInt(String key) {
		int value  = -1;
		try {
			initialize();
			value  = Integer.parseInt(properties.getProperty(key));
		} catch (Exception genericException) {
			genericException.printStackTrace();
		}
		return value;
	}

	/**
	 *Constructs a boolean  reading from configuration properties file
	 * @param key  this key must not null {@code null}
	 * @return value associated with key from the properties file
	 */
	public static boolean getValueAsBoolean(String key) {
		boolean value = false; // default false always
		try {
			initialize();
			value =  Boolean.valueOf(properties.getProperty(key));
		} catch (Exception genericException) {
			genericException.printStackTrace();
		}
		return value;
	}

	/**
	 * Constructs a string reading from configuration properties file
	 * @param key  this key must not null {@code null}
	 * @return value associated with key from the properties file
	 */
	public static String getValueAsSecuredString(String key) {
		String value  = null;
		try {
			initialize();
			value  = properties.getProperty(key);
		} catch (Exception genericException) {
			genericException.printStackTrace();
		}
		return value;
	}

	/**
	 *
	 * @param roleName - role name must not null
	 * @return
	 */
	public JSONArray getNavigationObject(@NonNull  String roleName) {
		JSONArray navItems = null;
		String methodName  = "getNavigationObject";
		try {
			//initialize();
			appInputStream = ConfigFactory.class.getResourceAsStream("/static/PrivateRoutes.json");
			//appJson = JSONObject.parse(appInputStream);
			appJson = new JSONObject(new JSONTokener(appInputStream));

			JSONObject navigations = (JSONObject) appJson.get("navigations"); // get navigation elements from app json file // see static resource path for .json file
			navItems = new JSONArray();
			navItems = (JSONArray) navigations.get(roleName);
			if(navItems == null) {
				return new JSONArray();
			}
			// get common items
			JSONArray common = (JSONArray)  navigations.get("common");
			if(common != null)
				navItems.putAll(common);
			// add itemIds dynamically to the paths
			for(int x = 0; x < navItems.length(); x++) {
				addNavItemId((JSONObject) navItems.get(x));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return navItems;
	}

	private void addNavItemId(@NonNull JSONObject item)	{
		String methodName  = "addNavItemId";
		try {
			if(item.has("childs")) {
				JSONArray childs = (JSONArray) item.get("childs");
				for(int x = 0; x < childs.length() ; x++) {
					addNavItemId((JSONObject) childs.get(x));
				}
			}
			item.put("itemId", UUID.randomUUID().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
