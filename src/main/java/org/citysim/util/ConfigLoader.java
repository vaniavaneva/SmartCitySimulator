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
            validate();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load config.properties", ex);
        }
    }

    public static int getInt(String key) {
        String value = getRequired(key);
        int parsed;
        try {
            parsed = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Invalid int value for key " + key + ": " + value);
        }
        if (parsed <= 0) {
            throw new RuntimeException("Value for key " + key + " must be positive");
        }
        return parsed;
    }

    public static double getDouble(String key) {
        String value = getRequired(key);
        double parsed;
        try {
            parsed = Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Invalid double value for key " + key + ": " + value);
        }
        if (parsed <= 0) {
            throw new RuntimeException("Value for key " + key + " must be positive");
        }
        return parsed;
    }

    private static String getRequired(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing required config property: " + key);
        }
        value = value.trim();
        if (value.isEmpty()) {
            throw new RuntimeException("Config property " + key + " is empty");
        }
        return value;
    }

    private static void validate() {
        String[] keys = {
                "simulation.duration",
                "simulation.duration",
                "bike.rent.probability",
                "min.charge",
                "max.charge",
                "air.quality.threshold",
                "max.history",
                "thread.pool.size"
        };

        for (String key : keys) {
            if (!props.containsKey(key)) {
                throw new RuntimeException("Config property missing: " + key);
            }
        }
    }
}