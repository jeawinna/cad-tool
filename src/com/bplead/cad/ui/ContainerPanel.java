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

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.ui.PromptTextField.PromptTextFieldDimension;

public class ContainerPanel extends AbstractPanel {

	private static final Logger logger = Logger.getLogger(ContainerPanel.class);
	private static final long serialVersionUID = 1442969218942586007L;
	private final String FOLDER_BUTTON_ICON = "folder.search.icon";
	private final String FOLDER_PROMPT = "folder.prompt";
	private final String FOLDER_TITLE = "folder.title";
	private final String PDM_BUTTON_ICON = "pdm.search.icon";
	private final String PDM_PROMPT = "pdm.prompt";
	private final String PDM_TITLE = "pdm.title";

	public ContainerPanel(Container parent) {
		super(parent);
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
		add(new PDMLinkProductPanel(this));

		logger.info("initialize SubFolder panel...");
		add(new SubFolderPanel(this));

		// set flow layout horizontal gap
		FlowLayout layout = (FlowLayout) getLayout();
		layout.setHgap(getHGap());

		if (logger.isDebugEnabled()) {
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
	}

	class PDMLinkProductPanel extends SimpleButtonSetPanel {

		private static final long serialVersionUID = 5788762488066451045L;

		public PDMLinkProductPanel(Container parent) {
			super(parent);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.debug("open pdmlinkproduct dialog performed");
		}

		@Override
		protected Icon setButtonIcon() {
			return getResourceMap().getIcon(getResourceName(PDM_BUTTON_ICON));
		}

		@Override
		protected String setButtonText() {
			return null;
		}

		@Override
		protected String setPrompt() {
			return getResourceMap().getString(getResourceName(PDM_PROMPT));
		}

		@Override
		protected String setText() {
			return "Test PDMLinkProduct";
		}

		@Override
		protected String setTitle() {
			return getResourceMap().getString(getResourceName(PDM_TITLE));
		}
	}

	abstract class SimpleButtonSetPanel extends AbstractPanel implements ActionListener {

		private static final long serialVersionUID = -5690721799689305895L;
		private final double BUTTON_PROPORTION = 0.3d;
		private final double HEIGHT_PROPORTION = 0.3d;
		private final double LABEL_PROPORTION = 0.15d;
		private final double TEXT_PROPORTION = 0.65d;

		public SimpleButtonSetPanel(Container parent) {
			super(parent);
		}

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

			JButton open = new JButton(setButtonIcon());
			open.setIcon(setButtonIcon());
			open.setPreferredSize(getButtonPrerredSize());
			open.addActionListener(this);
			add(open);
		}

		protected abstract Icon setButtonIcon();

		protected abstract String setButtonText();

		protected abstract String setPrompt();

		protected abstract String setText();

		protected abstract String setTitle();
	}

	class SubFolderPanel extends SimpleButtonSetPanel {

		private static final long serialVersionUID = 5788762488066451045L;

		public SubFolderPanel(Container parent) {
			super(parent);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			logger.debug("open subfolder dialog performed");
		}

		@Override
		protected Icon setButtonIcon() {
			return getResourceMap().getIcon(getResourceName(FOLDER_BUTTON_ICON));
		}

		@Override
		protected String setButtonText() {
			return null;
		}

		@Override
		protected String setPrompt() {
			return getResourceMap().getString(getResourceName(FOLDER_PROMPT));
		}

		@Override
		protected String setText() {
			return "/test/folder";
		}

		@Override
		protected String setTitle() {
			return getResourceMap().getString(getResourceName(FOLDER_TITLE));
		}
	}
}
