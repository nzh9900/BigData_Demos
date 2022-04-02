/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ni.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author DongTao
 * @since 2019-01-18
 * <p>
 * property utils single instance
 */
@Slf4j
public class PropertyUtils {

    private static final Properties PROPERTIES = new Properties();

    private static final PropertyUtils propertyUtils = new PropertyUtils();

    public static final String APPLICATION_PROPERTIES_PATH = "/application-im.properties";

    private PropertyUtils() {
        init();
    }

    private void init() {
        String[] propertyFiles = new String[]{APPLICATION_PROPERTIES_PATH};
        for (String fileName : propertyFiles) {
            InputStream fis = null;
            try {
                fis = PropertyUtils.class.getResourceAsStream(fileName);
                PROPERTIES.load(fis);

            } catch (IOException e) {
                log.error(e.getMessage(), e);
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                        log.error("Unable to close resource", ex);
                    }
                }
                System.exit(1);
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException ex) {
                        log.error("Unable to close resource", ex);
                    }
                }
            }
        }
    }

    /**
     * get property value
     *
     * @param key property name
     * @return property value
     */
    public static String getString(String key) {
        return PROPERTIES.getProperty(key.trim());
    }

    /**
     * get property value
     *
     * @param key property name
     * @return get property int value , if key == null, then return -1
     */
    public static int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * @param key          key
     * @param defaultValue default value
     * @return property value
     */
    public static int getInt(String key, int defaultValue) {
        String value = getString(key);
        if (value == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.info(e.getMessage(), e);
        }
        return defaultValue;
    }

    /**
     * @param key          key
     * @param defaultValue default value
     * @return property value
     */
    public static String getString(String key, String defaultValue) {
        String value = getString(key).trim();
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }

        return value;
    }

    /**
     * get property value
     *
     * @param key property name
     * @return property value
     */
    public static Boolean getBoolean(String key) {
        String value = PROPERTIES.getProperty(key.trim());
        if (null != value) {
            return Boolean.parseBoolean(value);
        }

        return false;
    }

    /**
     * get property long value
     *
     * @param key        key
     * @param defaultVal default value
     * @return property value
     */
    public static long getLong(String key, long defaultVal) {
        String val = getString(key);
        return val == null ? defaultVal : Long.parseLong(val);
    }

    /**
     * @param key key
     * @return property value
     */
    public static long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * @param key        key
     * @param defaultVal default value
     * @return property value
     */
    public double getDouble(String key, double defaultVal) {
        String val = getString(key);
        return val == null ? defaultVal : Double.parseDouble(val);
    }


    /**
     * get array
     *
     * @param key      property name
     * @param splitStr separator
     * @return property value through array
     */
    public static String[] getArray(String key, String splitStr) {
        String value = getString(key);
        if (value == null) {
            return new String[0];
        }
        try {
            String[] propertyArray = value.split(splitStr);
            return propertyArray;
        } catch (NumberFormatException e) {
            log.info(e.getMessage(), e);
        }
        return new String[0];
    }

    /**
     * @param key          key
     * @param type         type
     * @param defaultValue default value
     * @param <T>          T
     * @return get enum value
     */
    public <T extends Enum<T>> T getEnum(String key, Class<T> type,
                                         T defaultValue) {
        String val = getString(key);
        return val == null ? defaultValue : Enum.valueOf(type, val);
    }

    /**
     * get all properties with specified prefix, like: fs.
     *
     * @param prefix prefix to search
     * @return all properties with specified prefix
     */
    public static Map<String, String> getPrefixedProperties(String prefix) {
        Map<String, String> matchedProperties = new HashMap<>();
        for (String propName : PROPERTIES.stringPropertyNames()) {
            if (propName.startsWith(prefix)) {
                matchedProperties.put(propName, PROPERTIES.getProperty(propName));
            }
        }
        return matchedProperties;
    }
}
