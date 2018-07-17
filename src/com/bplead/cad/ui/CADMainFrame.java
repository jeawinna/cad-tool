package com.bplead.cad.ui;

import org.apache.log4j.Logger;

public class CADMainFrame extends AbstractFrame {

	private static final Logger logger = Logger.getLogger(CADMainFrame.class);
	private static final long serialVersionUID = -1719424691262349744L;

	public static void main(String[] args) {
		logger.info("begin to start...");
		new CADMainFrame().newInstance(null);
		logger.info("ready...");
	}

	public CADMainFrame() {
		super(CADMainFrame.class);
	}

	@Override
	public double getHorizontalProportion() {
		return 0.6;
	}

	@Override
	public double getVerticalProportion() {
		return 0.9;
	}

	@Override
	protected void initialize() {
		logger.info("initialize menu bar...");
		setJMenuBar(toolkit.getStandardMenuBar());

		logger.info("initialize container panel...");
		getContentPane().add(new ContainerPanel(this));

		logger.info("initialize basic attribute panel...");
		getContentPane().add(new BasicAttributePanel(this));

		logger.info("initialize detail attribute panel...");
		getContentPane().add(new DetailAttributePanel(this));
	}
}
