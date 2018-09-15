package com.bplead.cad.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import org.apache.log4j.Logger;

import com.bplead.cad.bean.io.CAPP;
import com.bplead.cad.model.CustomStyleToolkit;
import com.bplead.cad.util.ClientUtils;

import priv.lee.cad.model.Callback;
import priv.lee.cad.model.StyleToolkit;
import priv.lee.cad.ui.AbstractFrame;
import priv.lee.cad.util.Assert;
import priv.lee.cad.util.PropertiesUtils;
import priv.lee.cad.util.XmlUtils;

public class CAPPMainFrame extends AbstractFrame implements Callback {

	private static Logger logger = Logger.getLogger(CADMainFrame.class);
	private static final long serialVersionUID = -583597043543602853L;

	public static void main(String[] args) {
		new CAPPMainFrame().activate();
	}

	private BasicAttributePanel attributePanel;
	private CAPP capp;
	private final String CAPP_REPOSITORY = "capp.xml.repository";
	private ContainerPanel containerPanel;
	private TabAttributePanel tabAttributePanel;
	private StyleToolkit toolkit = new CustomStyleToolkit();

	public CAPPMainFrame() {
		super(CAPPMainFrame.class);
		setToolkit(toolkit);
	}

	@Override
	public synchronized void call(Object object) {
		notifyAll();
	}

	@Override
	public double getHorizontalProportion() {
		return 0.6d;
	}

	private File getRepository() {
		if (ClientUtils.temprary == null || ClientUtils.temprary.getPreference() == null
				|| ClientUtils.temprary.getPreference().getCaxa() == null
				|| !new File(ClientUtils.temprary.getPreference().getCaxa().getCache()).exists()) {
			return null;
		}
		return new File(ClientUtils.temprary.getPreference().getCaxa().getCache() + CAPP_REPOSITORY);
	}

	@Override
	public double getVerticalProportion() {
		return 0.99d;
	}

	private void initCAPP() {
		if (getRepository() == null) {
			startPreferencesDialog(this);
			dispose();
		} else {
			File xml = getRepository();
			Assert.notNull(xml, "CAD tool initialize failed.Please check file /CAXA_CACHE"
					+ PropertiesUtils.readProperty(CAPP_REPOSITORY) + " is exsits");
			this.capp = XmlUtils.read(xml, CAPP.class);
			logger.debug("capp:" + capp);
		}
	}

	@Override
	public void initialize() {
		logger.info("initialize " + getClass() + " CAPP...");
		initCAPP();

		if (capp == null) {
			return;
		}

		logger.info("initialize " + getClass() + " menu bar...");
		setJMenuBar(toolkit.getStandardMenuBar(new CheckinActionListenner(), new CheckoutActionListenner()));

		logger.info("initialize " + getClass() + " container panel...");
		containerPanel = new ContainerPanel();
		getContentPane().add(containerPanel);

		logger.info("initialize " + getClass() + " basic attribute panel...");
		attributePanel = new BasicAttributePanel(capp);
		attributePanel.setLabelProportion(0.1d);
		getContentPane().add(attributePanel);

		logger.info("initialize " + getClass() + " tab attribute panel...");
		tabAttributePanel = new TabAttributePanel(capp.getMpmParts());
		getContentPane().add(tabAttributePanel);
	}

	private void startPreferencesDialog(Callback container) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				PreferencesDialog dialog = new PreferencesDialog(container);
				dialog.activate();
			}
		});
	}

	public class CheckinActionListenner implements ActionListener, FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			// TODO
			return false;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO
		}
	}

	public class CheckoutActionListenner implements ActionListener, Callback {

		@Override
		public void actionPerformed(ActionEvent e) {
			new SearchForDownloadDialog(this).activate();
		}

		@Override
		public void call(Object object) {

		}
	}
}
