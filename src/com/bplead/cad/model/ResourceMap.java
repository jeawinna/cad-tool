package com.bplead.cad.model;

import java.awt.Window;

import javax.swing.Icon;

import org.apache.log4j.Logger;

import com.bplead.cad.resource.ResourceBundle;
import com.bplead.cad.util.Assert;

public abstract class ResourceMap {

	private static final Logger logger = Logger.getLogger(ResourceMap.class);
	public static final String ICON = ".icon";
	public static final String RESOURCE = "resource.";

	private Class<? extends Window> clazz;

	public ResourceMap(Class<? extends Window> clazz) {
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
