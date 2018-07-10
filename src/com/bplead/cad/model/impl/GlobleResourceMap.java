package com.bplead.cad.model.impl;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import org.apache.log4j.Logger;

import com.bplead.cad.model.ResourceMap;
import com.bplead.cad.model.ResourceType;
import com.bplead.cad.util.Assert;
import com.bplead.cad.util.ObjectUtils;

public class GlobleResourceMap implements ResourceMap {

	private static final Logger logger = Logger.getLogger(GlobleResourceMap.class);

	@Override
	public Icon getIcon(Class<? extends JComponent> clazz) {
		logger.debug("clazz:" + clazz);
		return getIcon(buildResourceName(clazz, null));
	}

	@Override
	public Icon getIcon(String name) {
		String icon = getResource(name);
		logger.debug("name:" + name + ",icon:" + icon);
		Assert.hasText(icon, "can not find name[" + name + "] configuration");
		return new ImageIcon(icon);
	}

	@Override
	public int getInt(Class<? extends JComponent> clazz, ResourceType type) {
		return getInt(buildResourceName(clazz, type));
	}

	@Override
	public int getInt(String name) {
		String value = getResource(name);
		logger.debug("name:" + name + ",value:" + value);
		Assert.isNumeric(value, "not a numreic value");
		return Integer.valueOf(value);
	}

	@Override
	public String getString(Class<? extends JComponent> clazz, ResourceType type) {
		return getString(buildResourceName(clazz, type));
	}

	@Override
	public String getString(String name) {
		return getResource(name);
	}

	private String buildResourceName(Class<? extends JComponent> clazz, ResourceType type) {
		return ObjectUtils.isEmpty(type) ? RESOURCE + clazz.getName() + ICON
				: RESOURCE + clazz.getName() + type.getType();
	}
}
