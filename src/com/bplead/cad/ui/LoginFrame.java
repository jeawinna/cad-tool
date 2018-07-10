package com.bplead.cad.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LoginFrame extends AbstractFrame {

	private static final long serialVersionUID = -8688157705470416228L;

	public static void main(String[] args) {
		new LoginFrame().newInstance("Test");

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

class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public LoginPanel() {

	}
}
