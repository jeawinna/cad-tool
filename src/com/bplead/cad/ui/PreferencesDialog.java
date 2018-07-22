package com.bplead.cad.ui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.ui.PromptTextField.PromptTextFieldDimension;

public class PreferencesDialog extends AbstractDialog {

	private static final Logger logger = Logger.getLogger(PreferencesDialog.class);
	private static final long serialVersionUID = -2875157877197653599L;

	public static void main(String[] args) {
		new PreferencesDialog().newInstance(null);
	}

	public PreferencesDialog() {
		super(PreferencesDialog.class);
	}

	@Override
	public double getHorizontalProportion() {
		return 0.4d;
	}

	@Override
	public double getVerticalProportion() {
		return 0.35d;
	}

	@Override
	protected void initialize() {
		logger.info("initialize preferences content...");
		add(new PreferencesPanel(this));

		logger.info("initialize option content...");
		add(new OptionPanel(this));

		logger.info("initialize completed...");
	}

	private class CancelActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class ConfirmActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class FindCaxaCacheActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class FindCaxaExeActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class FindDefaultFolderActionListenner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private class FindDefaultPdmActionListenner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	class OptionPanel extends AbstractPanel {

		private static final long serialVersionUID = 2106959274498664555L;
		private final String CANCEL_BUTTON_DISPLAY = "cancel";
		private final String CONFIRM_BUTTON_DISPLAY = "confirm";

		public OptionPanel(Container parent) {
			super(parent);
		}

		@Override
		public double getHorizontalProportion() {
			return 0.5d;
		}

		@Override
		public double getVerticalProportion() {
			return 0.2d;
		}

		@Override
		protected void initialize() {
			// ~ add buttons
			JButton confirm = new JButton(getResourceMap().getString(getResourceName(CONFIRM_BUTTON_DISPLAY)));
			confirm.addActionListener(new ConfirmActionListener());
			add(confirm);

			JButton cancel = new JButton(getResourceMap().getString(getResourceName(CANCEL_BUTTON_DISPLAY)));
			cancel.addActionListener(new CancelActionListener());
			add(cancel);
		}
	}

	class PreferencesPanel extends AbstractPanel {

		private static final long serialVersionUID = -5076567817803672184L;
		private final String CAXA_CACHE = "caxa.cache";
		private final String CAXA_EXE = "caxa.exe";
		private final String DEFAULT_FOLDER = "default.folder";
		private final String DEFAULT_PDM = "default.pdm";
		private final double HEIGHT_PROPORTION = 0.08d;
		private final double LABEL_PROPORTION = 0.2d;
		private final String OPEN = "open";
		private final double TEXT_PROPORTION = 0.55d;
		private final String TITLE = "title";
		private final String WINDCHILL_URL = "wnc.url";

		public PreferencesPanel(Container parent) {
			super(parent);
		}

		private String getCaxaCache() {
			return "CaxaCache";
		}

		private String getCaxaExe() {
			return "CaxaExe";
		}

		private String getDefaultFolder() {
			return "Folder";
		}

		private String getDefaultPdm() {
			return "Pdm";
		}

		@Override
		public double getHorizontalProportion() {
			return 0.95d;
		}

		@Override
		public double getVerticalProportion() {
			return 0.75d;
		}

		private String getWnctUrl() {
			return "http://plm.teg.cn/Windchill";
		}

		@Override
		protected void initialize() {
			setLayout(new FlowLayout(FlowLayout.LEFT));

			// set panel border to be title and etched type
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
					getResourceMap().getString(getResourceName(TITLE)), TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

			// ~ add components
			PromptTextFieldDimension dimension = new PromptTextField().new PromptTextFieldDimension(getPreferredSize(),
					LABEL_PROPORTION, TEXT_PROPORTION, HEIGHT_PROPORTION);
			PromptTextField url = new PromptTextField(getResourceMap().getString(getResourceName(WINDCHILL_URL)),
					getWnctUrl(), dimension);
			add(url);

			PromptTextField exe = new PromptTextField(getResourceMap().getString(getResourceName(CAXA_EXE)),
					getCaxaExe(), dimension);
			add(exe);
			JButton openExe = new JButton(getResourceMap().getString(getResourceName(OPEN)));
			openExe.addActionListener(new FindCaxaExeActionListener());
			add(openExe);

			PromptTextField cache = new PromptTextField(getResourceMap().getString(getResourceName(CAXA_CACHE)),
					getCaxaCache(), dimension);
			add(cache);
			JButton openCache = new JButton(getResourceMap().getString(getResourceName(OPEN)));
			openCache.addActionListener(new FindCaxaCacheActionListener());
			add(openCache);

			PromptTextField pdm = new PromptTextField(getResourceMap().getString(getResourceName(DEFAULT_PDM)),
					getDefaultPdm(), dimension);
			add(pdm);
			JButton openPdm = new JButton(getResourceMap().getString(getResourceName(OPEN)));
			openPdm.addActionListener(new FindDefaultPdmActionListenner());
			add(openPdm);

			PromptTextField folder = new PromptTextField(getResourceMap().getString(getResourceName(DEFAULT_FOLDER)),
					getDefaultFolder(), dimension);
			add(folder);
			JButton openFolder = new JButton(getResourceMap().getString(getResourceName(OPEN)));
			openPdm.addActionListener(new FindDefaultFolderActionListenner());
			add(openFolder);
		}
	}
}
