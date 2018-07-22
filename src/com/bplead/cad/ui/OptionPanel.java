package com.bplead.cad.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.List;

import javax.swing.BorderFactory;

import org.apache.log4j.Logger;

import com.bplead.cad.model.MiddleAlignGap;
import com.bplead.cad.util.Assert;

public class OptionPanel extends AbstractPanel {

	private static final Logger logger = Logger.getLogger(OptionPanel.class);
	private static final long serialVersionUID = 2106959274498664555L;

	public static OptionPanel newInstance(Container parent, List<Option> options) {
		Assert.notNull(parent, "Container(parent) must not be null");
		Assert.notEmpty(options, "Options must not be null");

		OptionPanel panel = initialize(OptionPanel.class);
		panel.setOptions(options);
		panel.setDoSelfAdaption(false);
		panel.initialize(parent);
		return panel;
	}

	private MiddleAlignGap gap = new MiddleAlignGap(2, 2);
	private LayoutManager layout = new FlowLayout(FlowLayout.LEFT, gap.hGap, gap.vGap);
	private List<Option> options;

	public OptionPanel() {

	}

	@Override
	public double getHorizontalProportion() {
		return 0d;
	}

	public List<Option> getOptions() {
		return options;
	}

	@Override
	public double getVerticalProportion() {
		return 0d;
	}

	@Override
	protected void initialize() {
		setLayout(layout);

		logger.info("add options...");
		// ~ add options
		for (Option option : options) {
			add(option);
		}

		if (logger.isDebugEnabled()) {
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
	}

	public void setGap(MiddleAlignGap gap) {
		this.gap = gap;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}
}