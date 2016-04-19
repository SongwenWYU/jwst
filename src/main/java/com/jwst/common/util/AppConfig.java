package com.jwst.common.util;

import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility to look up configurable commands and resources
 */
public class AppConfig {

    private static Logger log = LoggerFactory.getLogger(AppConfig.class);
    private static PropertiesConfiguration prop;

    static {
        try {
            prop = new PropertiesConfiguration(AppConfig.class.getClassLoader().getResource("").getPath() + "/appConfig.properties");
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
        }
    }
    /**
     * gets the property from config
     *
     * @param name property name
     * @return configuration property
     */
    public static String getProperty(String name) {

        return prop.getString(name);
    }

    /**
     * gets the property from config and replaces placeholders
     *
     * @param name           property name
     * @param replacementMap name value pairs of place holders to replace
     * @return configuration property
     */
    public static String getProperty(String name, Map<String, String> replacementMap) {

        String value = prop.getString(name);
        if (StringUtils.isNotEmpty(value)) {
            //iterate through map to replace text
            Set<String> keySet = replacementMap.keySet();
            for (String key : keySet) {
                //replace values in string
                String rVal = replacementMap.get(key);
                value = value.replace("${" + key + "}", rVal);
            }
        }
        return value;
    }

    /**
     * removes property from the config
     *
     * @param name property name
     */
    public static void removeProperty(String name) {

        //remove property
        try {
            prop.clearProperty(name);
            prop.save();
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
        }
    }

    /**
     * updates the property in the config
     *
     * @param name property name
     * @param value property value
     */
    public static void updateProperty(String name, String value) {

        //remove property
        try {
            prop.setProperty(name, value);
            prop.save();
        } catch (Exception ex) {
            log.error(ex.toString(), ex);
        }
    }
	public static void main(String[] args) {
		System.out.println(AppConfig.getProperty("datasource.driverClassName"));
	}

}