package com.bplead.cad.ui;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PromptTextField extends JComponent {

	private static final long serialVersionUID = -6696586333097590589L;

	public JLabel prompt;
	public JTextField text;

	public PromptTextField(String promptContent, String textDescription, boolean isPasswordTextField) {
		this.prompt = new JLabel(promptContent);
		if (isPasswordTextField) {
			text = new JPasswordField(textDescription);
		} else {
			text = new JTextField(textDescription);
		}
		add(prompt);
		add(text);
	}
}
