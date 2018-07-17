package com.bplead.cad.layout;

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JComponent;

import com.bplead.cad.util.Assert;

public class DefaultGroupLayout extends GroupLayout {

	private List<JComponent> components = new ArrayList<JComponent>();
	private int hGap;
	private int vGap;;

	public DefaultGroupLayout(Container container, int hGap, int vGap) {
		super(container);
		this.hGap = hGap;
		this.vGap = vGap;
		container.setLayout(this);
	}

	public DefaultGroupLayout addComponent(JComponent component) {
		this.components.add(component);
		return this;
	}

	public DefaultGroupLayout addComponent(List<? extends JComponent> components) {
		this.components.addAll(components);
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
		for (JComponent component : components) {
			// auto layout horizontal
			hpGroup.addComponent(component);

			// auto layout vertical
			vsGroup.addGroup(createParallelGroup().addComponent(component));
			vsGroup.addGap(vGap);
		}
		hsGroup.addGroup(hpGroup);
		hsGroup.addGap(hGap);
		setHorizontalGroup(hsGroup);
		setVerticalGroup(vsGroup);
	}

	public void layout(int perRow) {
		Assert.isTrue(perRow > 0, "Per line must greater than 0");
		if (components == null || components.isEmpty()) {
			return;
		}
		// TODO
	}
}
