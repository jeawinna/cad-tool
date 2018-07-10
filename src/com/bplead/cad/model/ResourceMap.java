package com.bplead.cad.model;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.bplead.cad.util.Assert;
import com.bplead.cad.util.PropertiesUtils;

public interface ResourceMap {

	public static final String ICON = ".icon";

	public static final String RESOURCE = "resource.";

	public Icon getIcon(Class<? extends JComponent> clazz);

	public Icon getIcon(String name);

	public int getInt(Class<? extends JComponent> clazz, ResourceType type);

	public int getInt(String name);

	default String getResource(String name) {
		Assert.hasText(name, "name is requeried");
		return PropertiesUtils.readProperty(name);
	}

	public String getString(Class<? extends JComponent> clazz, ResourceType type);

	public String getString(String name);
}
