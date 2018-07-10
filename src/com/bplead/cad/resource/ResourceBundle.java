package com.bplead.cad.resource;

import java.awt.Window;
import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.bplead.cad.util.Assert;
import com.bplead.cad.util.PropertiesUtils;

public class ResourceBundle<T extends Window> {

	private Logger logger = Logger.getLogger(ResourceBundle.class);

	private final String RESOURCE = "intance.resource";

	private Properties properties = new Properties();

	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	public ResourceBundle() {
		Type superclass = getClass().getGenericSuperclass();
		Assert.isInstanceOf(ParameterizedType.class, superclass, "invalid type.need:"
				+ ParameterizedType.class.getName() + ",actual:" + superclass.getClass().getName());

		ParameterizedType parameterizedType = (ParameterizedType) superclass;

		Type[] typeArray = parameterizedType.getActualTypeArguments();
		Assert.notEmpty(typeArray, "parameterized type is required");
		Assert.isTrue(typeArray.length < 1, "parameterized type is required");

		clazz = (Class<T>) typeArray[0];

		initResource();
	}

	private void initResource() {
		try {
			String resource = PropertiesUtils.readProperty(RESOURCE) + File.pathSeparator + clazz.getSimpleName()
					+ ".properties";
			logger.debug("resource:" + resource);
			properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(resource));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public String readResource(String resource) {
		Assert.hasText(resource, "resource is required");
		return (String) properties.get(resource);
	}
}
