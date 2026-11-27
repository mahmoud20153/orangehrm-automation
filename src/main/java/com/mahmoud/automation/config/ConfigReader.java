package com.mahmoud.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Central configuration manager.
 * Priority: JVM system property (-Dbrowser=firefox) > config.properties file.
 */
public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties;

    private ConfigReader() {
        // utility class - no instantiation
    }

    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream input =
                         ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
                if (input == null) {
                    throw new IllegalStateException("Config file not found on classpath: " + CONFIG_FILE);
                }
                properties.load(input);
            } catch (IOException e) {
                throw new IllegalStateException("Failed to load " + CONFIG_FILE, e);
            }
        }
        return properties;
    }

    /** Returns value for key. System property overrides the file (for CI/CD). */
    public static String get(String key) {
        String override = System.getProperty(key);
        if (override != null && !override.isBlank()) {
            return override.trim();
        }
        String value = getProperties().getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Missing configuration key: '" + key + "' in " + CONFIG_FILE);
        }
        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }


}