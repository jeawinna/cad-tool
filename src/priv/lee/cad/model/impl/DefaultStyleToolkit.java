package com.bplead.cad.model.impl;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.bplead.cad.model.ResourceMap;
import com.bplead.cad.model.StyleToolkit;
import com.bplead.cad.util.Assert;

public class DefaultStyleToolkit implements StyleToolkit {

	private static final String CHECK_IN_MENU_ITEM = "menu.option.item1";
	private static final String CHECK_OUT_MENU_ITEM = "menu.option.item2";
	private static final String FILE_MENU = "menu.file";
	private static final String FONT_NAME = "font.name";
	private static final String FONT_SIZE = "font.size";
	private static final String FONT_STYLE = "font.style";
	private static final String OPTION_MENU = "menu.option";
	private static final String PREFERENCES_MENU_ITEM = "menu.file.item2";
	private static final String QUIT_MENU_ITEM = "menu.file.item1";
	private static ResourceMap resourceMap;
	{
		resourceMap = new GlobalResourceMap();
	}

	@Override
	public Font getFont() {
		String name = resourceMap.getString(FONT_NAME);
		String style = resourceMap.getString(FONT_STYLE);
		String size = resourceMap.getString(FONT_SIZE);
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

	@Override
	public JMenuBar getStandardMenuBar() {
		// ~ add menu bar
		JMenu file = new JMenu(resourceMap.getString(FILE_MENU));
		file.getPopupMenu().setLightWeightPopupEnabled(false);
		JMenu option = new JMenu(resourceMap.getString(OPTION_MENU));
		option.getPopupMenu().setLightWeightPopupEnabled(false);

		JMenuItem quit = new JMenuItem(resourceMap.getString(QUIT_MENU_ITEM));
		JMenuItem preferences = new JMenuItem(resourceMap.getString(PREFERENCES_MENU_ITEM));
		file.add(quit);
		file.addSeparator();
		file.add(preferences);

		JMenuItem checkin = new JMenuItem(resourceMap.getString(CHECK_IN_MENU_ITEM));
		JMenuItem checkout = new JMenuItem(resourceMap.getString(CHECK_OUT_MENU_ITEM));
		option.add(checkin);
		option.addSeparator();
		option.add(checkout);

		JMenuBar bar = new JMenuBar();
		bar.add(file);
		bar.add(option);
		return bar;
	}
}
