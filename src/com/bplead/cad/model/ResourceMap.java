package com.bplead.cad.model;

import javax.swing.Icon;

import org.apache.log4j.Logger;

import com.bplead.cad.resource.ResourceBundle;
import com.bplead.cad.util.Assert;

public abstract class ResourceMap {

	public static final String ICON = ".icon";
	private static final Logger logger = Logger.getLogger(ResourceMap.class);
	protected static final String SEPERATOR = ".";

	private Class<?> clazz;

	public ResourceMap() {

	}

	public ResourceMap(Class<?> clazz) {
		Assert.notNull(clazz, "Class extends java.awt.Window required");
		this.clazz = clazz;
	}

	public abstract Icon getIcon(String name);

	public abstract int getInt(String name);

	protected String getResource(String name) {
		logger.debug("clazz:" + clazz + ",name:" + name);
		return new ResourceBundle(clazz).readResource(name);
	}

	public abstract String getString(String name);
}
