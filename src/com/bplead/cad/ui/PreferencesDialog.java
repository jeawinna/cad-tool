package com.bplead.cad.ui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
		add(PreferencesPanel.newInstance(this));

		logger.info("initialize option content...");
		Option confirm = Option.newInstance(Option.CONFIRM_BUTTON_DISPLAY, null, new ConfirmActionListener());
		add(OptionPanel.newInstance(this, Arrays.asList(confirm, Option.newCancelOption(this))));

		logger.info("initialize completed...");
	}

	private class ConfirmActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private static class FindCaxaCacheActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private static class FindCaxaExeActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private static class FindDefaultFolderActionListenner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	private static class FindDefaultPdmActionListenner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	static class PreferencesPanel extends AbstractPanel {

		private static final long serialVersionUID = -5076567817803672184L;
		private final String CAXA_CACHE = "caxa.cache";
		private final String CAXA_EXE = "caxa.exe";
		private final String DEFAULT_FOLDER = "default.folder";
		private final String DEFAULT_PDM = "default.pdm";
		private final double HEIGHT_PROPORTION = 0.08d;
		private final double LABEL_PROPORTION = 0.18d;
		private final String OPEN = "open";
		private final double TEXT_PROPORTION = 0.55d;
		private final String TITLE = "title";
		private final String WINDCHILL_URL = "wnc.url";

		public static PreferencesPanel newInstance(Container parent) {
			PreferencesPanel panel = initialize(PreferencesPanel.class);
			panel.initialize(parent);
			return panel;
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
					getResourceMap().getString((TITLE)), TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

			// ~ add components
			PromptTextFieldDimension dimension = PromptTextField.newDimension(getPreferredSize(), LABEL_PROPORTION,
					TEXT_PROPORTION, HEIGHT_PROPORTION);
			PromptTextField url = PromptTextField.newInstance(getResourceMap().getString((WINDCHILL_URL)), getWnctUrl(),
					dimension);
			add(url);

			PromptTextField exe = PromptTextField.newInstance(getResourceMap().getString((CAXA_EXE)), getCaxaExe(),
					dimension);
			add(exe);
			JButton openExe = new JButton(getResourceMap().getString((OPEN)));
			openExe.addActionListener(new FindCaxaExeActionListener());
			add(openExe);

			PromptTextField cache = PromptTextField.newInstance(getResourceMap().getString((CAXA_CACHE)),
					getCaxaCache(), dimension);
			add(cache);
			JButton openCache = new JButton(getResourceMap().getString((OPEN)));
			openCache.addActionListener(new FindCaxaCacheActionListener());
			add(openCache);

			PromptTextField pdm = PromptTextField.newInstance(getResourceMap().getString((DEFAULT_PDM)),
					getDefaultPdm(), dimension);
			add(pdm);
			JButton openPdm = new JButton(getResourceMap().getString((OPEN)));
			openPdm.addActionListener(new FindDefaultPdmActionListenner());
			add(openPdm);

			PromptTextField folder = PromptTextField.newInstance(getResourceMap().getString((DEFAULT_FOLDER)),
					getDefaultFolder(), dimension);
			add(folder);
			JButton openFolder = new JButton(getResourceMap().getString((OPEN)));
			openPdm.addActionListener(new FindDefaultFolderActionListenner());
			add(openFolder);
		}
	}
}
