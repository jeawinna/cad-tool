package com.bplead.cad.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.bplead.cad.util.Assert;

public class PromptTextField extends JComponent {

	class PromptTextFieldDimension {
		public int promptWidth;
		public int textWidth;
		public int height;

		public PromptTextFieldDimension(int promptWidth, int textWidth, int height) {
			this.promptWidth = promptWidth;
			this.textWidth = textWidth;
			this.height = height;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("PromptTextFieldDimension [promptWidth=");
			builder.append(promptWidth);
			builder.append(", textWidth=");
			builder.append(textWidth);
			builder.append(", height=");
			builder.append(height);
			builder.append("]");
			return builder.toString();
		}
	}

	private static final Logger logger = Logger.getLogger(PromptTextField.class);
	private static final long serialVersionUID = -6696586333097590589L;
	private JLabel prompt;
	private JTextField text;
	private PromptTextFieldDimension dimension;
	private LayoutManager layout;
	{
		// default flow layout and left alignment
		layout = getLayout() == null ? new FlowLayout(FlowLayout.LEFT) : getLayout();
	}

	public PromptTextField() {

	}

	public PromptTextField(JLabel prompt, JTextField text) {
		this(prompt, text, null);
	}

	public PromptTextField(JLabel prompt, JTextField text, PromptTextFieldDimension dimension) {
		Assert.notNull(prompt, "Prompt can not be null");
		Assert.notNull(text, "Prompt can not be null");
		logger.debug("prompt:" + prompt + ",text:" + text + ",dimension:" + dimension);
		this.prompt = prompt;
		this.text = text;
		this.dimension = dimension;
		initialize();
	}

	public PromptTextField(String prompt, String text) {
		this(new JLabel(prompt), new JTextField(text));
	}

	public PromptTextField(String prompt, String text, PromptTextFieldDimension dimension) {
		this(new JLabel(prompt), new JTextField(text), dimension);
	}

	public PromptTextFieldDimension getDimension() {
		return dimension;
	}

	public LayoutManager getLayout() {
		return layout;
	}

	public JLabel getPrompt() {
		return prompt;
	}

	public JTextField getText() {
		return text;
	}

	private void initialize() {
		setLayout(layout);

		if (dimension != null) {
			prompt.setPreferredSize(new Dimension(dimension.promptWidth, dimension.height));
			text.setPreferredSize(new Dimension(dimension.textWidth, dimension.height));
			setPreferredSize(new Dimension(dimension.promptWidth + dimension.textWidth, dimension.height));
		}

		add(prompt);
		add(text);
	}

	public boolean isEditable() {
		return text.isEditable();
	}

	public void setDimension(PromptTextFieldDimension dimension) {
		this.dimension = dimension;
	}

	public void setEditable(boolean editable) {
		text.setEditable(editable);
	}

	@Override
	public void setLayout(LayoutManager layout) {
		super.setLayout(layout);
		this.layout = layout;
	}

	public void setPrompt(JLabel prompt) {
		this.prompt = prompt;
	}

	public void setText(JTextField text) {
		this.text = text;
	}
}
