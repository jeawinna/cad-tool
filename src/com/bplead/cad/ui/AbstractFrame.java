package com.bplead.cad.ui;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.bplead.cad.model.ResourceMap;
import com.bplead.cad.model.ResourceMapContainer;
import com.bplead.cad.model.SelfAdaptionComponent;
import com.bplead.cad.model.StyleToolkit;
import com.bplead.cad.model.impl.DefaultStyleToolkit;
import com.bplead.cad.model.impl.GlobalResourceMap;
import com.bplead.cad.util.StringUtils;

public abstract class AbstractFrame extends JFrame implements SelfAdaptionComponent, ResourceMapContainer {

	private static final Logger logger = Logger.getLogger(AbstractFrame.class);
	private static final long serialVersionUID = 8333297262635054463L;
	private LayoutManager layout = new FlowLayout(FlowLayout.CENTER);
	protected ResourceMap resourceMap;
	private String TITLE = "title";
	protected StyleToolkit toolkit = new DefaultStyleToolkit();

	public <T extends Window> AbstractFrame(Class<T> clazz) {
		resourceMap = new GlobalResourceMap(clazz);
	}

	private void doSelfAdaption() {
		// get screen size
		Rectangle rec = toolkit.getScreenSize(this.getGraphicsConfiguration());
		logger.debug("rec:" + rec);

		doSelfAdaption(rec, this);
	}

	@Override
	public ResourceMap getResourceMap() {
		return resourceMap;
	}

	protected abstract void initialize();

	public void newInstance(String title) {
		logger.info("initialize frame...");
		// ~ initialize frame
		setVisible(true);
		setTitle(StringUtils.isEmpty(title) ? getResourceMap().getString(TITLE) : title);
		setLayout(layout);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		logger.info("do frame self adpaption...");
		doSelfAdaption();

		logger.info("initialize custom content...");
		initialize();

		logger.info("validate the whole frame...");
		validate();
	}

	public void setResourceMap(ResourceMap resourceMap) {
		this.resourceMap = resourceMap;
	}

	public void setToolkit(StyleToolkit toolkit) {
		this.toolkit = toolkit;
	}
}
