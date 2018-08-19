package com.bplead.cad.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.bean.SimplePdmLinkProduct;

import priv.lee.cad.model.Callback;
import priv.lee.cad.ui.AbstractPanel;
import priv.lee.cad.ui.Option;
import priv.lee.cad.ui.OptionPanel;
import priv.lee.cad.ui.PromptTextField;
import priv.lee.cad.util.Assert;

public class ContainerPanel extends AbstractPanel {

	private static final String BUTTON_ICON = "folder.search.icon";
	private static final String FOLDER_PROMPT = "folder.prompt";
	private static final String FOLDER_TITLE = "folder.title";
	private static final Logger logger = Logger.getLogger(ContainerPanel.class);
	private static final String PDM_PROMPT = "pdm.prompt";
	private static final String PDM_TITLE = "pdm.title";
	private static final long serialVersionUID = 1442969218942586007L;
	private SimplePdmLinkProduct product;

	@Override
	public double getHorizontalProportion() {
		return 1d;
	}

	@Override
	public double getVerticalProportion() {
		return 0.1d;
	}

	@Override
	public void initialize() {
		logger.info("initialize " + PDMLinkProductPanel.class + "...");
		add(new PDMLinkProductPanel());

		logger.info("initialize " + SubFolderPanel.class + "...");
		add(new SubFolderPanel());
	}

	class PDMLinkProductPanel extends SimpleButtonSetPanel {

		private static final long serialVersionUID = 5788762488066451045L;

		@Override
		public void actionPerformed(ActionEvent e) {
			new PdmLinkProductChooseDialog(this).activate();
		}

		@Override
		public void call(Object object) {
			Assert.notNull(object, "Callback object is required");
			Assert.isInstanceOf(SimplePdmLinkProduct.class, object,
					"Callback object must be a SimplePdmLinkProduct type");

			product = (SimplePdmLinkProduct) object;
			text.getText().setText(product.getName());
		}

		public SimplePdmLinkProduct getProduct() {
			return product;
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

	abstract static class SimpleButtonSetPanel extends AbstractPanel implements ActionListener, Callback {

		private static final long serialVersionUID = -5690721799689305895L;
		private final double BUTTON_PROPORTION = 0.3d;
		private final double HEIGHT_PROPORTION = 0.3d;
		private final double LABEL_PROPORTION = 0.15d;
		public PromptTextField text;
		private final double TEXT_PROPORTION = 0.65d;

		private Dimension getButtonPrerredSize() {
			BigDecimal width = new BigDecimal(getPreferredSize().height).multiply(new BigDecimal(BUTTON_PROPORTION));
			return new Dimension(width.intValue(), width.intValue());
		}

		@Override
		public double getHorizontalProportion() {
			return 0.47d;
		}

		@Override
		public double getVerticalProportion() {
			return 0.99d;
		}

		@Override
		public void initialize() {
			logger.info("modify " + getClass() + " to flow layout...");
			setLayout(new FlowLayout(FlowLayout.LEFT));

			logger.info("initialize " + getClass() + "  content...");
			// ~ initialize content
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), setTitle(),
					TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

			PromptTextField.PromptTextFieldDimension dimension = PromptTextField.newDimension(getPreferredSize(),
					LABEL_PROPORTION, TEXT_PROPORTION, HEIGHT_PROPORTION);
			text = PromptTextField.newInstance(setPrompt(), setText(), dimension);
			add(text);

			add(new OptionPanel(Arrays.asList(new Option(null, BUTTON_ICON, this, getButtonPrerredSize()))));
		}

		protected abstract String setButtonText();

		protected abstract String setPrompt();

		protected abstract String setText();

		protected abstract String setTitle();
	}

	class SubFolderPanel extends SimpleButtonSetPanel {

		private static final long serialVersionUID = 5788762488066451045L;

		@Override
		public void actionPerformed(ActionEvent e) {
			new FolderChooseDialog(this, product).activate();
		}

		@Override
		public void call(Object object) {
			Assert.notNull(object, "Callback object is required");

			text.getText().setText(object.toString());
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
