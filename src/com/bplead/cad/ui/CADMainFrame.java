package com.bplead.cad.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.apache.log4j.Logger;

import com.bplead.cad.io.bean.CAD;
import com.bplead.cad.model.CustomStyleToolkit;

import priv.lee.cad.model.Callback;
import priv.lee.cad.model.StyleToolkit;
import priv.lee.cad.ui.AbstractFrame;
import priv.lee.cad.util.Assert;
import priv.lee.cad.util.PropertiesUtils;
import priv.lee.cad.util.XmlUtils;

public class CADMainFrame extends AbstractFrame {

	private static final Logger logger = Logger.getLogger(CADMainFrame.class);
	private static final long serialVersionUID = -1719424691262349744L;

	public static void main(String[] args) {
		logger.info("begin to start...");
		new CADMainFrame().activate();
		logger.info("ready...");
	}

	private CAD cad;
	private final String CAD_REPOSITORY = "cad.xml.repository";
	private StyleToolkit toolkit = new CustomStyleToolkit();

	public CADMainFrame() {
		super(CADMainFrame.class);

		setToolkit(toolkit);
	}

	@Override
	public double getHorizontalProportion() {
		return 0.6;
	}

	@Override
	public double getVerticalProportion() {
		return 0.99;
	}

	private void initCAD() {
		File xml = new File(XmlUtils.class.getResource(PropertiesUtils.readProperty(CAD_REPOSITORY)).getPath());
		this.cad = XmlUtils.parse(xml, CAD.class);
		logger.debug("cad:" + cad);
	}

	@Override
	public void initialize() {
		logger.info("initialize " + getClass() + " CAD...");
		initCAD();
		Assert.notNull(cad, "CAD initialize failed.Please check the " + PropertiesUtils.readProperty(CAD_REPOSITORY));

		logger.info("initialize " + getClass() + " menu bar...");
		setJMenuBar(toolkit.getStandardMenuBar());

		logger.info("initialize " + getClass() + " container panel...");
		getContentPane().add(new ContainerPanel());

		logger.info("initialize " + getClass() + " basic attribute panel...");
		getContentPane().add(new BasicAttributePanel(cad));

		logger.info("initialize " + getClass() + " detail attribute panel...");
		getContentPane().add(new DetailAttributePanel(cad));
	}

	public class PrefencesActionListener implements ActionListener, Callback {

		@Override
		public void actionPerformed(ActionEvent e) {
			new PreferencesDialog(this).activate();
		}

		@Override
		public void call(Object object) {

		}
	}
}
