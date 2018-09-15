package com.bplead.cad.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import com.bplead.cad.bean.io.Attachment;
import com.bplead.cad.bean.io.CAD;
import com.bplead.cad.bean.io.Container;
import com.bplead.cad.bean.io.Document;
import com.bplead.cad.model.CustomStyleToolkit;
import com.bplead.cad.util.ClientUtils;
import com.bplead.cad.util.ValidateUtils;

import priv.lee.cad.model.Callback;
import priv.lee.cad.ui.AbstractFrame;
import priv.lee.cad.util.Assert;
import priv.lee.cad.util.FTPUtils;
import priv.lee.cad.util.PropertiesUtils;
import priv.lee.cad.util.XmlUtils;

public class CADMainFrame extends AbstractFrame implements Callback {

	private static final long serialVersionUID = -1719424691262349744L;
	protected BasicAttributePanel basicAttributePanel;
	private CAD cad;
	private final String CAD_REPOSITORY = "cad.xml.repository";
	protected ContainerPanel containerPanel;
	protected DetailAttributePanel detailAttributePanel;
	private final Logger logger = Logger.getLogger(CADMainFrame.class);
	protected CustomStyleToolkit toolkit = new CustomStyleToolkit(this);

	public CADMainFrame() {
		super(CADMainFrame.class);
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
				+ PropertiesUtils.readProperty(CAD_REPOSITORY));
	}

	@Override
	public double getVerticalProportion() {
		return 0.99d;
	}

	private void initCAD() {
		if (getRepository() == null) {
			toolkit.startPreferencesDialog(this);
			dispose();
		} else {
			File xml = getRepository();
			Assert.notNull(xml, "CAD tool initialize failed.Please check file /CAXA_CACHE"
					+ PropertiesUtils.readProperty(CAD_REPOSITORY) + " is exsits");
			this.cad = XmlUtils.read(xml, CAD.class);
			logger.debug("cad:" + cad);
		}
	}

	@Override
	public void initialize() {
		logger.info("initialize " + getClass() + " CAD...");
		initCAD();

		if (cad == null) {
			return;
		}

		logger.info("initialize " + getClass() + " menu bar...");
		setJMenuBar(toolkit.getStandardMenuBar(new CheckinActionListenner(), new CheckoutActionListenner()));

		logger.info("initialize " + getClass() + " container panel...");
		containerPanel = new ContainerPanel();
		getContentPane().add(containerPanel);

		logger.info("initialize " + getClass() + " basic attribute panel...");
		basicAttributePanel = new BasicAttributePanel(cad);
		getContentPane().add(basicAttributePanel);

		logger.info("initialize " + getClass() + " detail attribute panel...");
		detailAttributePanel = new DetailAttributePanel(cad);
		getContentPane().add(detailAttributePanel);
	}

	public class CheckinActionListenner implements ActionListener {

		private static final String DOC_TYPE = "wt.document.type";
		private String docType;
		private final String PRIMARY_SUFFIX = "wt.document.primary.file.suffix";
		private String primarySuffix = PropertiesUtils.readProperty(PRIMARY_SUFFIX);
		{
			docType = PropertiesUtils.readProperty(DOC_TYPE);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ValidateUtils.validatePreference();

			Document document = buildDocument();
			ValidateUtils.validateCheckin(document);

			CheckinWorker worker = new CheckinWorker(document);
			worker.execute();
		}

		private Document buildDocument() {
			Document document = new Document(null, cad.getName(), null);
			document.setOid(ClientUtils.getDocumentOid());
			document.setContainer(new Container(containerPanel.pdmlinkProductPanel.getProduct(),
					containerPanel.subFolderPanel.getFolder()));
			document.setObject(cad);
			document.setAttachments(ClientUtils.buildAttachments(primarySuffix));
			document.setType(docType);
			return document;
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
			List<Attachment> attachments = document.getAttachments();
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
