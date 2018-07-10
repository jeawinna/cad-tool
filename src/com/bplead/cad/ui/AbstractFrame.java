package com.bplead.cad.ui;

import java.awt.Window;

import javax.swing.JFrame;

import com.bplead.cad.model.ResourceMap;
import com.bplead.cad.model.impl.GlobleResourceMap;

public abstract class AbstractFrame extends JFrame {

	private static final long serialVersionUID = 8333297262635054463L;

	protected ResourceMap<?> resourceMap;

	public <T extends Window> AbstractFrame(Class<T> clazz) {
		resourceMap = new GlobleResourceMap<T>();
	}

	public ResourceMap<?> getResourceMap() {
		return this.resourceMap;
	}

	public abstract void newInstance();
}
