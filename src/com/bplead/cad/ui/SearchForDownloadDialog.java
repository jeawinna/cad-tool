package com.bplead.cad.ui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.ui.PromptTextField.PromptTextFieldDimension;

public class SearchForDownloadDialog extends AbstractDialog implements ActionListener {

	private static LayoutManager layout = new FlowLayout(FlowLayout.LEFT);
	private static final Logger logger = Logger.getLogger(SearchForDownloadDialog.class);

	private static final long serialVersionUID = 1336292047030719519L;

	public static void main(String[] args) {
		new SearchForDownloadDialog().newInstance(null);
	}

	public SearchForDownloadDialog() {
		super(SearchForDownloadDialog.class);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public double getHorizontalProportion() {
		return 0.5;
	}

	@Override
	public double getVerticalProportion() {
		return 0.5;
	}

	@Override
	protected void initialize() {
		setLayout(layout);

		logger.info("initialize search conditions content...");
		add(SearchConditionsPanel.newInstance(this));

		logger.info("initialize search result content...");
		add(SearchResultPanel.newInstance(this));

		logger.info("initialize download setting content...");
		add(DownloadSettingPanel.newInstance(this));

		logger.info("initialize completed...");
	}

	static class DownloadSettingPanel extends AbstractPanel implements ActionListener {

		private static final long serialVersionUID = -6481481565984135229L;

		public static DownloadSettingPanel newInstance(Container parent) {
			DownloadSettingPanel panel = initialize(DownloadSettingPanel.class);
			panel.initialize(parent);
			return panel;
		}

		private final String DOWNLOAD_TO = "downloadTo";
		private final double HEIGHT_PROPORTION = 0.5d;
		private final double LABEL_PROPORTION = 0.1d;
		private final double TEXT_PROPORTION = 0.5d;

		@Override
		public void actionPerformed(ActionEvent e) {

		}

		@Override
		public double getHorizontalProportion() {
			return 0.95d;
		}

		@Override
		public double getVerticalProportion() {
			return 0.1d;
		}

		@Override
		protected void initialize() {
			setLayout(layout);

			PromptTextFieldDimension dimension = PromptTextField.newDimension(getPreferredSize(), LABEL_PROPORTION,
					TEXT_PROPORTION, HEIGHT_PROPORTION);
			PromptTextField setting = PromptTextField.newInstance((getResourceMap().getString(DOWNLOAD_TO)), null,
					dimension);
			setting.setLabelAligment(SwingConstants.LEFT);
			add(setting);

			logger.info("initialize browser option...");
			Option browse = Option.newInstance(Option.BROWSE_BUTTON, null, this);
			Option confirm = Option.newInstance(Option.CONFIRM_BUTTON, null, this);
			add(OptionPanel.newInstance(this, Arrays.asList(browse, confirm, Option.newCancelOption((Window) parent))));
		}
	}

	static class SearchConditionsPanel extends AbstractPanel implements ActionListener {

		private static final String NAME = "name";
		private static final String NUMBER = "number";
		private static final String SEARCH = "search";
		private static final long serialVersionUID = 7488199863056895133L;

		public static SearchConditionsPanel newInstance(Container parent) {
			SearchConditionsPanel panel = initialize(SearchConditionsPanel.class);
			panel.initialize(parent);
			return panel;
		}

		private final double HEIGHT_PROPORTION = 0.3d;
		private final double LABEL_PROPORTION = 0.05d;
		private final double TEXT_PROPORTION = 0.2d;
		private final String TITLE = "title";

		@Override
		public void actionPerformed(ActionEvent e) {

		}

		@Override
		public double getHorizontalProportion() {
			return 0.95d;
		}

		@Override
		public double getVerticalProportion() {
			return 0.2d;
		}

		@Override
		protected void initialize() {
			// set panel border to be title and etched type
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
					getResourceMap().getString(TITLE), TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

			PromptTextFieldDimension dimension = PromptTextField.newDimension(getPreferredSize(), LABEL_PROPORTION,
					TEXT_PROPORTION, HEIGHT_PROPORTION);
			PromptTextField number = PromptTextField.newInstance((getResourceMap().getString(NUMBER)), null, dimension);
			number.setLabelAligment(SwingConstants.CENTER);
			add(number);

			PromptTextField name = PromptTextField.newInstance((getResourceMap().getString(NAME)), null, dimension);
			name.setLabelAligment(SwingConstants.CENTER);
			add(name);

			Option search = Option.newInstance(SEARCH, null, this);
			add(OptionPanel.newInstance(this, Arrays.asList(search)));
		}
	}

	static class SearchResultPanel extends AbstractPanel {

		private static final long serialVersionUID = -7416585921364617464L;

		public static SearchResultPanel newInstance(Container parent) {
			SearchResultPanel panel = initialize(SearchResultPanel.class);
			panel.initialize(parent);
			return panel;
		}

		private final String TITLE = "title";

		@Override
		public double getHorizontalProportion() {
			return 0.95d;
		}

		@Override
		public double getVerticalProportion() {
			return 0.6d;
		}

		@Override
		protected void initialize() {
			// set panel border to be title and etched type
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
					getResourceMap().getString(TITLE), TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

		}
	}
}
