package nl.geoxplore;

import java.io.InputStream;
import java.util.Properties;

public class GeoFormats {

	public static String propertyValueFor(String key) {
		return propertyValueFor(key, null);
	}

	public static String propertyValueFor(String key, String defaultValue) {
		Properties prop = new Properties();
		try (InputStream input = GeoFormats.class.getClassLoader().getResourceAsStream("config.properties")) {
			prop.load(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return prop.getProperty(key, defaultValue);
	}

}
