package com.wuhulala.kafka.consumer;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.io.IOException;
import java.util.Properties;

/**
 * @ClassName: PropertyHolder
 * @Description: 属性文件
 *
 */
public class PropertyHolder extends PropertyPlaceholderConfigurer {

	private static Properties props;

	@Override
	public Properties mergeProperties() throws IOException {
        props = super.mergeProperties();
		return props;
	}

	public static String getProperty(String key) {
		return props.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	public static Properties getProps() {
		return props;
	}
}
