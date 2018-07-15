package com.bplead.cad.ui;

import java.awt.Window;

import javax.swing.JFrame;

import com.bplead.cad.model.ResourceMap;
import com.bplead.cad.model.StyleToolkit;
import com.bplead.cad.model.impl.DefaultStyleToolkit;
import com.bplead.cad.model.impl.GlobalResourceMap;

public abstract class AbstractFrame extends JFrame {

	private static final long serialVersionUID = 8333297262635054463L;
	protected ResourceMap resourceMap;
	protected StyleToolkit toolkit = new DefaultStyleToolkit();

	public <T extends Window> AbstractFrame(Class<T> clazz) {
		resourceMap = new GlobalResourceMap(clazz);
	}

	public ResourceMap getResourceMap() {
		return resourceMap;
	}

	public abstract void newInstance(String title);

	public void setResourceMap(ResourceMap resourceMap) {
		this.resourceMap = resourceMap;
	}

	public void setToolkit(StyleToolkit toolkit) {
		this.toolkit = toolkit;
	}
}
