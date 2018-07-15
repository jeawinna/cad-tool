package com.bplead.cad.model.impl;

import java.awt.Font;

import com.bplead.cad.model.StyleToolkit;
import com.bplead.cad.util.Assert;
import com.bplead.cad.util.PropertiesUtils;

public class DefaultStyleToolkit implements StyleToolkit {

	private static String PREFIX = "default.";
	private static String DEFAULT_FONT_NAME = PREFIX + "font.name";
	private static String DEFAULT_FONT_STYLE = PREFIX + "font.style";
	private static String DEFAULT_FONT_SIZE = PREFIX + "font.size";

	public Font getFont() {
		String name = PropertiesUtils.readProperty(DEFAULT_FONT_NAME);
		String style = PropertiesUtils.readProperty(DEFAULT_FONT_STYLE);
		String size = PropertiesUtils.readProperty(DEFAULT_FONT_SIZE);
		Assert.hasText(name, "Could not found default font name in client-instance.properties");
		Assert.isNumeric(style, "Default font style must be numeric");
		Assert.isNumeric(size, "Default font size must be numeric");
		return new Font(name, Integer.parseInt(style), Integer.parseInt(size));
	}

}
