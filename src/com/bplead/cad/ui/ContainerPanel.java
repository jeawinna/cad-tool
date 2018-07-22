package com.bplead.cad.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.ui.PromptTextField.PromptTextFieldDimension;

public class ContainerPanel extends AbstractPanel {

	private static final String BUTTON_ICON = "folder.search.icon";
	private static final String FOLDER_PROMPT = "folder.prompt";

	private static final String FOLDER_TITLE = "folder.title";

	private static final Logger logger = Logger.getLogger(ContainerPanel.class);
	private static final String PDM_PROMPT = "pdm.prompt";
	private static final String PDM_TITLE = "pdm.title";
	private static final long serialVersionUID = 1442969218942586007L;
	public static ContainerPanel newInstance(Container parent) {
		ContainerPanel panel = initialize(ContainerPanel.class);
		panel.initialize(parent);
		return panel;
	}

	private int getHGap() {
		Component[] components = getComponents();
		if (components == null || components.length == 0) {
			return 0;
		}

		BigDecimal width = BigDecimal.ZERO;
		for (Component component : components) {
			width = width.add(new BigDecimal(component.getPreferredSize().width));
		}
		return new BigDecimal(getPreferredSize().width).subtract(width)
				.divide(new BigDecimal(components.length + 1), RoundingMode.DOWN).intValue();
	}

	@Override
	public double getHorizontalProportion() {
		return 1d;
	}

	@Override
	public double getVerticalProportion() {
		return 0.1d;
	}

	@Override
	protected void initialize() {
		logger.info("initialize PDMLinkProduct panel...");
		add(PDMLinkProductPanel.newInstance(this));

		logger.info("initialize SubFolder panel...");
		add(SubFolderPanel.newInstance((this)));

		// set flow layout horizontal gap
		FlowLayout layout = (FlowLayout) getLayout();
		layout.setHgap(getHGap());

		if (logger.isDebugEnabled()) {
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
	}

	static class PDMLinkProductPanel extends SimpleButtonSetPanel {

		private static final long serialVersionUID = 5788762488066451045L;

		public static PDMLinkProductPanel newInstance(Container parent) {
			PDMLinkProductPanel panel = initialize(PDMLinkProductPanel.class);
			panel.initialize(parent);
			return panel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.debug("open pdmlinkproduct dialog performed");
		}

		@Override
		protected String setButtonText() {
			return null;
		}

		@Override
		protected String setPrompt() {
			return getResourceMap().getString(PDM_PROMPT);
		}

		@Override
		protected String setText() {
			return "Test PDMLinkProduct";
		}

		@Override
		protected String setTitle() {
			return getResourceMap().getString(PDM_TITLE);
		}
	}

	abstract static class SimpleButtonSetPanel extends AbstractPanel implements ActionListener {

		private static final long serialVersionUID = -5690721799689305895L;
		public static SimpleButtonSetPanel newInstance(Container parent) {
			SimpleButtonSetPanel panel = initialize(SimpleButtonSetPanel.class);
			panel.initialize(parent);
			return panel;
		}
		private final double BUTTON_PROPORTION = 0.3d;
		private final double HEIGHT_PROPORTION = 0.3d;
		private final double LABEL_PROPORTION = 0.15d;

		private final double TEXT_PROPORTION = 0.65d;

		private Dimension getButtonPrerredSize() {
			BigDecimal width = new BigDecimal(getPreferredSize().height).multiply(new BigDecimal(BUTTON_PROPORTION));
			return new Dimension(width.intValue(), width.intValue());
		}

		@Override
		public double getHorizontalProportion() {
			return 0.462d;
		}

		@Override
		public double getVerticalProportion() {
			return 0.99d;
		}

		@Override
		protected void initialize() {
			logger.info("modify to flow layout...");
			setLayout(new FlowLayout(FlowLayout.LEFT));

			logger.info("initialize content...");
			// ~ initialize content
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), setTitle(),
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

			PromptTextFieldDimension dimension = new PromptTextField().new PromptTextFieldDimension(getPreferredSize(),
					LABEL_PROPORTION, TEXT_PROPORTION, HEIGHT_PROPORTION);
			PromptTextField text = new PromptTextField(setPrompt(), setText(), dimension);
			add(text);

			add(OptionPanel.newInstance(this,
					Arrays.asList(Option.newInstance(null, BUTTON_ICON, this, getButtonPrerredSize()))));
		}

		protected abstract String setButtonText();

		protected abstract String setPrompt();

		protected abstract String setText();

		protected abstract String setTitle();
	}

	static class SubFolderPanel extends SimpleButtonSetPanel {

		private static final long serialVersionUID = 5788762488066451045L;

		public static SubFolderPanel newInstance(Container parent) {
			SubFolderPanel panel = initialize(SubFolderPanel.class);
			panel.initialize(parent);
			return panel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.debug("open subfolder dialog performed");
		}

		@Override
		protected String setButtonText() {
			return null;
		}

		@Override
		protected String setPrompt() {
			return getResourceMap().getString(FOLDER_PROMPT);
		}

		@Override
		protected String setText() {
			return "/test/folder";
		}

		@Override
		protected String setTitle() {
			return getResourceMap().getString(FOLDER_TITLE);
		}
	}
}
