package com.bplead.cad.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.bplead.cad.model.MiddleAlignGap;
import com.bplead.cad.util.Assert;

public class PromptTextField extends JComponent {

	private static final Logger logger = Logger.getLogger(PromptTextField.class);

	private static final long serialVersionUID = -6696586333097590589L;
	private PromptTextFieldDimension dimension;
	private MiddleAlignGap gap = new MiddleAlignGap(5, 5);
	private LayoutManager layout = new FlowLayout(FlowLayout.LEFT, gap.hGap, gap.vGap);
	private JLabel prompt;
	private JTextField text;

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
			logger.debug("promptWidth:" + dimension.promptWidth + ",textWidth:" + dimension.textWidth + ",height:"
					+ dimension.height);
			prompt.setPreferredSize(new Dimension(dimension.promptWidth, dimension.height));
			text.setPreferredSize(new Dimension(dimension.textWidth, dimension.height));
			setPreferredSize(new Dimension(dimension.promptWidth + dimension.textWidth + gap.hGap * 3,
					dimension.height + gap.vGap * 2));
		}

		if (logger.isDebugEnabled()) {
			prompt.setBorder(BorderFactory.createLineBorder(Color.RED));
			text.setBorder(BorderFactory.createLineBorder(Color.RED));
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
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

	public void setGap(MiddleAlignGap gap) {
		this.gap = gap;
	}

	public void setPrompt(JLabel prompt) {
		this.prompt = prompt;
	}

	public void setText(JTextField text) {
		this.text = text;
	}

	class PromptTextFieldDimension {
		public int height;
		public int promptWidth;
		public int textWidth;

		public PromptTextFieldDimension(Dimension parentSize, double promptWidthProportion, double textWidthProportion,
				double heightProportion) {
			Assert.notNull(parentSize, "Parent size must not be null");
			Assert.isTrue(promptWidthProportion > 0 && promptWidthProportion <= 1,
					"Prompt width proportion must be greater than 0 less than 1 or equal to 1");
			Assert.isTrue(textWidthProportion > 0 && textWidthProportion <= 1,
					"Text width proportion must be greater than 0 less than 1 or equal to 1");
			Assert.isTrue(heightProportion > 0 && heightProportion <= 1,
					"Height proportion must be greater than 0 less than 1 or equal to 1");

			this.promptWidth = ((Double) (parentSize.width * promptWidthProportion)).intValue();
			this.textWidth = ((Double) (parentSize.width * textWidthProportion)).intValue();
			this.height = ((Double) (parentSize.height * heightProportion)).intValue();
		}

		public PromptTextFieldDimension(int promptWidth, int textWidth, int height) {
			Assert.isTrue(promptWidth > 0, "Prompt width needs to be greater than 0");
			Assert.isTrue(textWidth > 0, "Text width needs to be greater than 0");
			Assert.isTrue(height > 0, "height needs to be greater than 0");

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
}
