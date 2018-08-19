package com.bplead.cad.model;

import java.awt.Component;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.bplead.cad.ui.CADMainFrame;

import priv.lee.cad.model.impl.DefaultStyleToolkit;

public class CustomStyleToolkit extends DefaultStyleToolkit {

	private static final String PREFERENCES_MENU_ITEM = "menu.file.item2";

	@Override
	public JMenuBar getStandardMenuBar() {
		JMenuBar menuBar = super.getStandardMenuBar();
		JMenu file = menuBar.getMenu(0);

		JMenuItem preferences = new JMenuItem(resourceMap.getString(PREFERENCES_MENU_ITEM));
		preferences.addActionListener(new CADMainFrame().new PrefencesActionListener());

		file.addSeparator();
		file.add(preferences);

		JMenu option = menuBar.getMenu(1);
		for (int i = 0; i < option.getMenuComponentCount(); i++) {
			Component component = option.getMenuComponent(i);
			if (component instanceof JMenuItem) {
				JMenuItem menuItem = (JMenuItem) component;
				if (menuItem.getText().equals(resourceMap.getString(CHECK_IN_MENU_ITEM))) {
					menuItem.addActionListener(new CADMainFrame().new CheckinActionListenner());
					continue;
				}

				if (menuItem.getText().equals(resourceMap.getString(CHECK_OUT_MENU_ITEM))) {
					menuItem.addActionListener(new CADMainFrame().new CheckoutActionListenner());
					continue;
				}
			}
		}
		return menuBar;
	}
}
