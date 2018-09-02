package com.bplead.cad.model;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.bplead.cad.ui.CADMainFrame;

import priv.lee.cad.model.impl.DefaultStyleToolkit;

public class CustomStyleToolkit extends DefaultStyleToolkit {

	private static final String PREFERENCES_MENU_ITEM = "menu.file.item2";

	@Override
	public JMenuBar getStandardMenuBar(ActionListener checkinListener, ActionListener checkoutListener) {
		JMenuBar menuBar = super.getStandardMenuBar(checkinListener, checkoutListener);
		JMenu file = menuBar.getMenu(0);

		JMenuItem preferences = new JMenuItem(resourceMap.getString(PREFERENCES_MENU_ITEM));
		preferences.addActionListener(new CADMainFrame().new PrefencesActionListener());

		file.addSeparator();
		file.add(preferences);
		return menuBar;
	}
}
