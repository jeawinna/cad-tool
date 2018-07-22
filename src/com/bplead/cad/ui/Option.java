package com.bplead.cad.ui;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.bplead.cad.model.ResourceMap;
import com.bplead.cad.model.ResourceMapper;
import com.bplead.cad.model.impl.ComponentResourceMap;
import com.bplead.cad.util.Assert;
import com.bplead.cad.util.StringUtils;

public class Option extends JButton implements ResourceMapper {

	public static final String CONFIRM_BUTTON = "confirm";
	public static final String BROWSE_BUTTON = "browse";
	private static final String PREFIX = Option.class.getSimpleName().toLowerCase();
	protected static ResourceMap resourceMap = new ComponentResourceMap(PREFIX, Option.class);
	private static final long serialVersionUID = -7593230479709858017L;

	public static Option newCancelOption(Window window) {
		return CancelOption.newInstance(window);
	}

	public static Option newCancelOption(Window window, String name) {
		return CancelOption.newInstance(window, name);
	}

	public static Option newInstance(String name, String icon, ActionListener action) {
		Assert.isTrue(StringUtils.isEmpty(name) || StringUtils.isEmpty(icon),
				"Option name or icon must not be all null");
		return newInstance(name, icon, action, null);
	}

	public static Option newInstance(String name, String icon, ActionListener action, Dimension preferredSize) {
		return new Option(name, icon, action, preferredSize);
	}

	private Option(String name, String icon, ActionListener action, Dimension preferredSize) {
		super(StringUtils.isEmpty(name) ? null : resourceMap.getString(name),
				StringUtils.isEmpty(icon) ? null : resourceMap.getIcon(icon));

		if (action != null) {
			addActionListener(action);
		}

		if (preferredSize != null) {
			setPreferredSize(preferredSize);
		}
	}

	@Override
	public ResourceMap getResourceMap() {
		return resourceMap;
	}

	@Override
	public void setResourceMap(ResourceMap resourceMap) {

	}

	static class CancelOption implements ActionListener {

		private static final String CANCEL = "cancel";

		public static Option newInstance(Window window) {
			Assert.notNull(window, "Window(parent) must not be null");
			return Option.newInstance(CANCEL, null, new CancelOption(window), null);
		}

		public static Option newInstance(Window window, String name) {
			Assert.notNull(window, "Window(parent) must not be null");
			return Option.newInstance(name, null, new CancelOption(window), null);
		}

		private Window window;

		private CancelOption(Window window) {
			this.window = window;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			window.dispose();
		}
	}
}
