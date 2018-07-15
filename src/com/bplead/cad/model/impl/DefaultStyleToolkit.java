package com.bplead.cad.model.impl;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

import com.bplead.cad.model.StyleToolkit;
import com.bplead.cad.util.Assert;
import com.bplead.cad.util.PropertiesUtils;

public class DefaultStyleToolkit implements StyleToolkit {

	private static String PREFIX = "default.";
	private static String DEFAULT_FONT_NAME = PREFIX + "font.name";
	private static String DEFAULT_FONT_STYLE = PREFIX + "font.style";
	private static String DEFAULT_FONT_SIZE = PREFIX + "font.size";

	@Override
	public Font getFont() {
		String name = PropertiesUtils.readProperty(DEFAULT_FONT_NAME);
		String style = PropertiesUtils.readProperty(DEFAULT_FONT_STYLE);
		String size = PropertiesUtils.readProperty(DEFAULT_FONT_SIZE);
		Assert.hasText(name, "Could not found default font name in client-instance.properties");
		Assert.isNumeric(style, "Default font style must be numeric");
		Assert.isNumeric(size, "Default font size must be numeric");
		return new Font(name, Integer.parseInt(style), Integer.parseInt(size));
	}

	@Override
	public Rectangle getScreenSize(GraphicsConfiguration config) {
		Assert.notNull(config, "Config must not be null");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(config);
		return new Rectangle(screenInsets.left, screenInsets.top,
				screenSize.width - screenInsets.left - screenInsets.right,
				screenSize.height - screenInsets.top - screenInsets.bottom);
	}
}
