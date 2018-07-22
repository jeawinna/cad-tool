package com.bplead.cad.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

public class PdmLinkProductChooseDialog extends AbstractDialog implements ActionListener {

	private static final Logger logger = Logger.getLogger(PdmLinkProductChooseDialog.class);
	private static final long serialVersionUID = 39269794179304687L;

	public static void main(String[] args) {
		new PdmLinkProductChooseDialog().newInstance(null);
	}

	public PdmLinkProductChooseDialog() {
		super(PdmLinkProductChooseDialog.class);
	}

	@Override
	public double getHorizontalProportion() {
		return 0.5d;
	}

	@Override
	public double getVerticalProportion() {
		return 0.5d;
	}

	@Override
	protected void initialize() {
		logger.info("initialize pdm content...");
		add(PdmLinkProductsPanel.newInstance(this));

		logger.info("initialize option content...");
		Option confirm = Option.newInstance(Option.CONFIRM_BUTTON, null, this);
		add(OptionPanel.newInstance(this, Arrays.asList(confirm, Option.newCancelOption(this))));

		logger.info("initialize completed...");
	}

	static class PdmLinkProductsPanel extends AbstractPanel {

		private static final long serialVersionUID = -9183060824080475562L;
		private final String TITLE = "title";

		public static PdmLinkProductsPanel newInstance(Container parent) {
			PdmLinkProductsPanel panel = initialize(PdmLinkProductsPanel.class);
			panel.initialize(parent);
			return panel;
		}

		@Override
		public double getHorizontalProportion() {
			return 0.95d;
		}

		@Override
		public double getVerticalProportion() {
			return 0.8d;
		}

		@Override
		protected void initialize() {
			// set panel border to be title and etched type
			setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
					getResourceMap().getString(TITLE), TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
