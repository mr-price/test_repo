package utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppProperties {
	private Properties properties = new Properties();
	private static AppProperties appProperties = null;

	private AppProperties() {

	}

	public static Properties getProperties() throws IOException {
		if (AppProperties.appProperties == null) {
			AppProperties.appProperties = new AppProperties();
			String propFileName = "config.properties";
			InputStream inputStream = AppProperties.class.getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				try {
					
					AppProperties.appProperties.properties.load(inputStream);
				} catch (IOException e) {
					throw e;
				}
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found ");
			}
		}
		return appProperties.properties;
	}

}
