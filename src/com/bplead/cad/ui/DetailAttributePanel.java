package com.bplead.cad.ui;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

public class DetailAttributePanel extends AbstractPanel {

	private static final long serialVersionUID = -206359105088128179L;
	private final String TITLE = "title";

	public DetailAttributePanel(Container parent) {
		super(parent);
	}

	@Override
	public double getHorizontalProportion() {
		return 0.95d;
	}

	@Override
	public double getVerticalProportion() {
		return 0.45d;
	}

	@Override
	protected void initialize() {
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				getResourceMap().getString(getResourceName(TITLE)), TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

	}
}
