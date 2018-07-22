package com.bplead.cad.ui;

import java.io.File;

import org.apache.log4j.Logger;

import com.bplead.cad.io.bean.CAD;
import com.bplead.cad.util.Assert;
import com.bplead.cad.util.PropertiesUtils;
import com.bplead.cad.util.XmlUtils;

public class CADMainFrame extends AbstractFrame {

	private static final Logger logger = Logger.getLogger(CADMainFrame.class);
	private static final long serialVersionUID = -1719424691262349744L;

	public static void main(String[] args) {
		logger.info("begin to start...");
		new CADMainFrame().newInstance(null);
		logger.info("ready...");
	}

	private CAD cad;

	private final String CAD_REPOSITORY = "cad.xml.repository";

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

	private void initCAD() {
		File xml = new File(XmlUtils.class.getResource(PropertiesUtils.readProperty(CAD_REPOSITORY)).getPath());
		this.cad = XmlUtils.parse(xml, CAD.class);
		logger.debug("cad:" + cad);
	}

	@Override
	protected void initialize() {
		logger.info("initialize CAD...");
		initCAD();
		Assert.notNull(cad, "CAD initialize failed.Please check the " + PropertiesUtils.readProperty(CAD_REPOSITORY));

		logger.info("initialize menu bar...");
		setJMenuBar(toolkit.getStandardMenuBar());

		logger.info("initialize container panel...");
		getContentPane().add(new ContainerPanel(this));

		logger.info("initialize basic attribute panel...");
		getContentPane().add(new BasicAttributePanel(this, cad));

		logger.info("initialize detail attribute panel...");
		getContentPane().add(new DetailAttributePanel(this, cad));
	}
}
