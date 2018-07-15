package com.bplead.cad.ui;

import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel {

	private static final long serialVersionUID = 1508737322646907563L;
	protected String PREFIX;
	{
		PREFIX = getClass().getSimpleName() + ".";
	}

	public AbstractPanel() {
		initComponents();
	}

	protected abstract void initComponents();

	protected String getResourceName(String name) {
		return PREFIX + name;
	}
}
