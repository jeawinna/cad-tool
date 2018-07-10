package com.bplead.cad.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.bplead.cad.util.PropertiesUtils;

public class LoginFrame extends AbstractFrame {

	class LoginPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private final String HOST_LABEL_DISPLAY = LoginPanel.class.getSimpleName() + ".hostName";
		private final String HOST_URL = "host.url";
		private final String HOST_EDITABLE = "host.editable";

		public LoginPanel() {
			JLabel hostLabel = new JLabel(getResourceMap().getString(HOST_LABEL_DISPLAY));

			JTextField hostText = new JTextField(getHost());
			hostText.setEditable(isHostEditable());

			add(hostLabel);
			add(hostText);
		}

		private String getHost() {
			return PropertiesUtils.readProperty(HOST_URL);
		}

		private boolean isHostEditable() {
			return Boolean.parseBoolean(PropertiesUtils.readProperty(HOST_EDITABLE));
		}
	}

	private static final long serialVersionUID = -8688157705470416228L;

	public static void main(String[] args) {
		new LoginFrame().newInstance("Test");

	}

	public LoginFrame() {
		super(LoginFrame.class);
	}

	private void init(String title) {

		setTitle(title);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);

		setSize(new Dimension(600, 300));

		setVisible(true);

		validate();
	}

	@Override
	public void newInstance() {
		newInstance(getTitle());
	}

	public void newInstance(String title) {
		init(title);

		getContentPane().add(new LoginPanel());

		validate();
	}
}
