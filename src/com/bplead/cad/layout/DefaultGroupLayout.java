package com.bplead.cad.layout;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JComponent;

public class DefaultGroupLayout extends GroupLayout {

	private int hGap;
	private int vGap;
	private List<JComponent> components = new ArrayList<JComponent>();;

	public DefaultGroupLayout(Container container, int hGap, int vGap) {
		super(container);
		this.hGap = hGap;
		this.vGap = vGap;
		container.setLayout(this);
	}

	public DefaultGroupLayout addComponent(JComponent component) {
		components.add(component);
		return this;
	}

	public void layout() {
		if (components == null || components.isEmpty()) {
			return;
		}

		GroupLayout.SequentialGroup hsGroup = createSequentialGroup();
		GroupLayout.SequentialGroup vsGroup = createSequentialGroup();
		hsGroup.addGap(hGap);
		vsGroup.addGap(vGap);
		ParallelGroup hpGroup = createParallelGroup();
		for (int i = 0; i < components.size(); i++) {
			// auto layout horizontal
			hpGroup.addComponent(components.get(i));

			// auto layout vertical
			vsGroup.addGroup(createParallelGroup().addComponent(components.get(i)));
			vsGroup.addGap(vGap);
		}
		hsGroup.addGroup(hpGroup);
		hsGroup.addGap(hGap);
		setHorizontalGroup(hsGroup);
		setVerticalGroup(vsGroup);
	}
}
