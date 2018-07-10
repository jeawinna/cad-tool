package com.bplead.cad.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = -8688157705470416228L;

	public static void main(String[] argd) {
		new LoginFrame().newInstance("Test");
	}

	public void newInstance(String title) {
		init(title);

		getContentPane().add(new LoginPanel());

		validate();

		setVisible(true);
	}

	private void init(String title) {
		setTitle(title);
		setBounds(100, 100, 250, 100);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public LoginPanel() {
		this.setLayout(new BorderLayout());
		this.add(new PromptTextField("Test", "value", false), BorderLayout.CENTER);
	}
}
