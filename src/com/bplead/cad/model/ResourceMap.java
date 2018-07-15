package com.bplead.cad.model;

import java.awt.Window;

import javax.swing.Icon;

import com.bplead.cad.resource.ResourceBundle;
import com.bplead.cad.util.Assert;

public abstract class ResourceMap {

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
		return new ResourceBundle(clazz).readResource(name);
	}

	public abstract String getString(String name);
}
