package com.bplead.cad.ui;

import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

public class BasicAttributePanel extends AbstractPanel {

	private static final Logger logger = Logger.getLogger(BasicAttributePanel.class);
	private static final long serialVersionUID = 5723039852386303330L;
	private final String TITLE = "title";

	public BasicAttributePanel(Container parent) {
		super(parent);
	}

	@Override
	public double getHorizontalProportion() {
		return 0.95d;
	}

	@Override
	public double getVerticalProportion() {
		return 0.35d;
	}

	@Override
	protected void initialize() {
		logger.info("initialize content...");
		// ~ initialize content
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				getResourceMap().getString(getResourceName(TITLE)), TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

	}
}
