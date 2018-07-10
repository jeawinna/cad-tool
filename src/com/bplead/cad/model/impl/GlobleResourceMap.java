package com.bplead.cad.model.impl;

import java.awt.Window;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import com.bplead.cad.model.ResourceMap;
import com.bplead.cad.util.Assert;

public class GlobleResourceMap<T extends Window> implements ResourceMap<T> {

	private static final Logger logger = Logger.getLogger(GlobleResourceMap.class);

	@Override
	public Icon getIcon(String name) {
		String icon = getResource(name);
		logger.debug("name:" + name + ",icon:" + icon);
		Assert.hasText(icon, "can not find name[" + name + "] configuration");
		return new ImageIcon(icon);
	}

	@Override
	public int getInt(String name) {
		String value = getResource(name);
		logger.debug("name:" + name + ",value:" + value);
		Assert.isNumeric(value, "not a numreic value");
		return Integer.valueOf(value);
	}

	@Override
	public String getString(String name) {
		return getResource(name);
	}
}
