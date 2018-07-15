package com.bplead.cad.ui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import com.bplead.cad.model.SelfAdaptionPanel;
import com.bplead.cad.util.Assert;

public abstract class AbstractPanel extends JPanel implements SelfAdaptionPanel {

	private static final long serialVersionUID = 1508737322646907563L;
	private Container parent;
	protected String PREFIX;
	private LayoutManager layout = new FlowLayout(FlowLayout.CENTER);
	{
		PREFIX = getClass().getSimpleName() + ".";
	}

	public AbstractPanel(Container parent) {
		Assert.notNull(parent, "Parent must not be null");
		this.parent = parent;
		initComponents();
	}

	public Container getParent() {
		return parent;
	}

	public void setParent(Container parent) {
		this.parent = parent;
	}

	protected String getResourceName(String name) {
		return PREFIX + name;
	}

	protected void initComponents() {
		// set layout
		setLayout(layout);

		logger.info("do panel self adpaption...");
		// ~ do frame self adaption
		doSelfAdaption(parent.getBounds(), this);

		// initialize custom content
		initialize();
	}

	protected abstract void initialize();
}
