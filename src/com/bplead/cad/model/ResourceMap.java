package com.bplead.cad.model;

import java.awt.Window;

import javax.swing.Icon;

import com.bplead.cad.resource.ResourceBundle;
import com.bplead.cad.util.Assert;

public interface ResourceMap<T extends Window> {

	public static final String ICON = ".icon";

	public static final String RESOURCE = "resource.";

	public Icon getIcon(String name);

	public int getInt(String name);

	default String getResource(String name) {
		Assert.hasText(name, "name is requeried");
		ResourceBundle<T> bundle = new ResourceBundle<T>();
		return bundle.readResource(name);
	}

	public String getString(String name);
}
