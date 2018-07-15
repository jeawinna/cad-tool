package com.bplead.cad.ui;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Window;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.bplead.cad.model.ResourceMap;
import com.bplead.cad.model.SelfAdaptionComponent;
import com.bplead.cad.model.StyleToolkit;
import com.bplead.cad.model.impl.DefaultStyleToolkit;
import com.bplead.cad.model.impl.GlobalResourceMap;

public abstract class AbstractFrame extends JFrame implements SelfAdaptionComponent {

	private static final long serialVersionUID = 8333297262635054463L;
	private static final Logger logger = Logger.getLogger(AbstractFrame.class);
	protected ResourceMap resourceMap;
	protected StyleToolkit toolkit = new DefaultStyleToolkit();
	private LayoutManager layout = new FlowLayout(FlowLayout.CENTER);

	public <T extends Window> AbstractFrame(Class<T> clazz) {
		resourceMap = new GlobalResourceMap(clazz);
	}

	private void doSelfAdaption() {
		// get screen size
		Rectangle rec = toolkit.getScreenSize(this.getGraphicsConfiguration());
		logger.debug("rec:" + rec);

		doSelfAdaption(rec, this);
	}

	public ResourceMap getResourceMap() {
		return resourceMap;
	}

	protected abstract void initialize();

	public void newInstance(String title) {
		logger.info("initialize frame...");
		// initialize frame
		setVisible(true);
		setTitle(title);
		setLayout(layout);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		logger.info("do frame self adpaption...");
		// ~ do frame self adaption
		doSelfAdaption();

		// initialize custom content
		initialize();
	}

	public void setResourceMap(ResourceMap resourceMap) {
		this.resourceMap = resourceMap;
	}

	public void setToolkit(StyleToolkit toolkit) {
		this.toolkit = toolkit;
	}
}
