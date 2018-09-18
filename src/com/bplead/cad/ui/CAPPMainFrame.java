package com.bplead.cad.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import com.bplead.cad.bean.io.Attachment;
import com.bplead.cad.bean.io.CAPP;
import com.bplead.cad.bean.io.Container;
import com.bplead.cad.bean.io.Document;
import com.bplead.cad.model.CustomStyleToolkit;
import com.bplead.cad.util.ClientUtils;
import com.bplead.cad.util.FTPUtils;
import com.bplead.cad.util.ValidateUtils;

import priv.lee.cad.model.Callback;
import priv.lee.cad.ui.AbstractFrame;
import priv.lee.cad.util.ClientAssert;
import priv.lee.cad.util.PropertiesUtils;
import priv.lee.cad.util.XmlUtils;

public class CAPPMainFrame extends AbstractFrame implements Callback {

	private static Logger logger = Logger.getLogger(CADMainFrame.class);
	private static final long serialVersionUID = -583597043543602853L;
	private BasicAttributePanel attributePanel;
	private CAPP capp;
	private final String CAPP_REPOSITORY = "capp.xml.repository";
	private ContainerPanel containerPanel;
	private TabAttributePanel tabAttributePanel;
	private CustomStyleToolkit toolkit = new CustomStyleToolkit(this);

	public CAPPMainFrame() {
		super(CAPPMainFrame.class);
		setToolkit(toolkit);
	}

	@Override
	public void call(Object object) {
		reload();
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
		return new File(ClientUtils.temprary.getPreference().getCaxa().getCache()
				+ PropertiesUtils.readProperty(CAPP_REPOSITORY));
	}

	@Override
	public double getVerticalProportion() {
		return 0.99d;
	}

	private void initCAPP() {
		if (getRepository() == null) {
			toolkit.startPreferencesDialog(this);
			dispose();
		} else {
			File xml = getRepository();
			ClientAssert.notNull(xml, "CAD tool initialize failed.Please check file /CAXA_CACHE"
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

	public class CheckinActionListenner implements ActionListener {

		private final String DOC_TYPE_PRIFIX = "capp.manuregu.";
		private final String DOC_TYPE_SUFFIX = ".document.type";
		private final String PRIMARY_SUFFIX = "capp.document.primary.file.suffix";
		private String primarySuffix = PropertiesUtils.readProperty(PRIMARY_SUFFIX);

		@Override
		public void actionPerformed(ActionEvent e) {
			ValidateUtils.validatePreference();

			Document document = buildDocument();
			ValidateUtils.validateCheckin(document);

			CheckinWorker worker = new CheckinWorker(document);
			worker.execute();
		}

		private Document buildDocument() {
			ClientUtils.buildAttachments(capp, primarySuffix);

			Document document = new Document(null, capp.getManuRegulationName(), null);
			document.setOid(ClientUtils.getDocumentOid(primarySuffix, capp.getAttachments()));
			document.setContainer(new Container(containerPanel.pdmlinkProductPanel.getProduct(),
					containerPanel.subFolderPanel.getFolder()));
			document.setObject(capp);
			document.setType(getDocType());
			return document;
		}

		private String getDocType() {
			return PropertiesUtils.readProperty(DOC_TYPE_PRIFIX + capp.getManuRegulationName() + DOC_TYPE_SUFFIX);
		}
	}

	private class CheckinWorker extends SwingWorker<Boolean, PopProgress.PromptProgress> implements Callback {

		private Document document;
		private PopProgress progress;
		private final String PROMPT_0 = "checkin.prompt.0";
		private final String PROMPT_100 = "checkin.prompt.100";
		private final String PROMPT_50 = "checkin.prompt.50";

		public CheckinWorker(Document document) {
			this.document = document;
			this.progress = new PopProgress(this);
			progress.activate();
		}

		@Override
		public void call(Object object) {

		}

		@Override
		protected Boolean doInBackground() throws Exception {
			logger.info("start...");
			List<Attachment> attachments = document.getObject().getAttachments();
			publish(new PopProgress.PromptProgress(getResourceMap().getString(PROMPT_0), 0));
			for (Attachment attachment : attachments) {
				File file = new File(attachment.getAbsolutePath());
				FTPUtils.newInstance().upload(file);
			}
			publish(new PopProgress.PromptProgress(getResourceMap().getString(PROMPT_50), 50));
			boolean successed = ClientUtils.checkin(document);
			logger.info("complete...");
			return successed;
		}

		@Override
		protected void done() {
			publish(new PopProgress.PromptProgress(getResourceMap().getString(PROMPT_100), 100));
		}

		@Override
		protected void process(List<PopProgress.PromptProgress> chunks) {
			progress.setProgress(chunks.get(0));
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
