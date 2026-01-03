package org.citysim.util;

import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties props = new Properties();

    static {
        try (InputStream file = ConfigLoader.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (file == null) {
                throw new RuntimeException("config.properties file not found in resources!");
            }

            props.load(file);

        } catch (Exception ex) {
            throw new RuntimeException("Failed to load config.properties", ex);
        }
    }

    public static int getInt(String key) {
        try {
            return Integer.parseInt(getRequired(key));
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Invalid int value for key: " + key);
        }
    }

    public static double getDouble(String key) {
        try {
            return Double.parseDouble(getRequired(key));
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Invalid double value for key: " + key);
        }
    }

    private static String getRequired(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing config property: " + key);
        }
        return value;
    }
}
