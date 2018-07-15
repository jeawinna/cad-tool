package com.bplead.cad.resource;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.bplead.cad.util.Assert;
import com.bplead.cad.util.PropertiesUtils;

public class ResourceBundle {

	private Logger logger = Logger.getLogger(ResourceBundle.class);

	private final String RESOURCE = "instance.resource";

	private Properties properties = new Properties();

	private Class<?> clazz;

	public ResourceBundle(Class<?> clazz) {
		this.clazz = clazz;
		initResource();
	}

	private void initResource() {
		try {
			String resource = PropertiesUtils.readProperty(RESOURCE) + clazz.getSimpleName() + ".properties";
			logger.debug("resource:" + resource);
			properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(resource));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String readResource(String resource) {
		Assert.hasText(resource, "resource is required");
		return (String) properties.get(resource);
	}
}
