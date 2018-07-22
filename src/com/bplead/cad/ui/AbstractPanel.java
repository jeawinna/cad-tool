package com.bplead.cad.ui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Window;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.bplead.cad.model.ResourceMap;
import com.bplead.cad.model.ResourceMapper;
import com.bplead.cad.model.SelfAdaptionPanel;
import com.bplead.cad.model.StyleToolkit;
import com.bplead.cad.model.impl.DefaultStyleToolkit;
import com.bplead.cad.model.impl.GlobalResourceMap;
import com.bplead.cad.util.Assert;

public abstract class AbstractPanel extends JPanel implements SelfAdaptionPanel, ResourceMapper {

	private static final Logger logger = Logger.getLogger(AbstractPanel.class);
	private static final long serialVersionUID = 1508737322646907563L;

	public static <T extends AbstractPanel> T initialize(Class<T> clatt) {
		T panel = null;
		try {
			panel = clatt.newInstance();
		} catch (Exception e) {
			logger.fatal(clatt + " new instance failed");
			e.printStackTrace();
		}
		return panel;
	}

	private LayoutManager layout = new FlowLayout(FlowLayout.CENTER);
	protected Container parent;
	protected String PREFIX;
	private ResourceMap resourceMap;
	protected StyleToolkit toolkit = new DefaultStyleToolkit();

	{
		PREFIX = getClass().getSimpleName();
	}

	public AbstractPanel() {

	}

	public AbstractPanel(Container parent) {
		Assert.notNull(parent, "Parent must not be null");

		initialize(parent);
	}

	public Container getParent() {
		return parent;
	}

	@Override
	public ResourceMap getResourceMap() {
		return resourceMap;
	}

	protected void initComponents() {
		logger.info("initialize panel...");
		setLayout(layout);

		logger.info("do panel self adpaption...");
		// ~ do panel self adaption
		doSelfAdaption(parent.getPreferredSize(), this);

		logger.info("initialize custom content...");
		initialize();
	}

	protected abstract void initialize();

	public void initialize(Container parent) {
		this.parent = parent;
		this.resourceMap = initWindowResourceMap(parent);

		initComponents();
	}

	protected ResourceMap initWindowResourceMap(Container container) {
		if (container instanceof Window) {
			Window window = (Window) container;
			logger.info("find window:" + container);
			return new GlobalResourceMap(PREFIX, window.getClass());
		}
		return initWindowResourceMap(container.getParent());
	}

	public void setParent(Container parent) {
		this.parent = parent;
	}

	@Override
	public void setResourceMap(ResourceMap resourceMap) {
		this.resourceMap = resourceMap;
	}

	public void setToolkit(StyleToolkit toolkit) {
		this.toolkit = toolkit;
	}
}
