package com.bplead.cad.ui;

import java.awt.Container;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.io.bean.CAD;
import com.bplead.cad.layout.DefaultGroupLayout;
import com.bplead.cad.ui.PromptTextField.PromptTextFieldDimension;

public class BasicAttributePanel extends AbstractPanel {

	private static final Logger logger = Logger.getLogger(BasicAttributePanel.class);
	private static final long serialVersionUID = 5723039852386303330L;

	public static BasicAttributePanel newInstance(Container parent, CAD cad) {
		BasicAttributePanel panel = initialize(BasicAttributePanel.class);
		panel.setCad(cad);
		panel.initialize(parent);
		return panel;
	}

	private CAD cad;
	private final double HEIGHT_PROPORTION = 0.1d;
	private final double HGAP_PROPORTION = 0.005d;
	private final double LABEL_PROPORTION = 0.08d;
	private final double TEXT_PROPORTION = 0.2d;
	private final String TITLE = "title";

	private final double VGAP_PROPORTION = 0.02d;

	private List<PromptTextField> conver2Texts() {
		// ~ reflect String type fields and convert to PromptTextField type
		List<PromptTextField> texts = new ArrayList<PromptTextField>();
		String value = "";
		PromptTextFieldDimension dimension = PromptTextField.newDimension(getPreferredSize(), LABEL_PROPORTION,
				TEXT_PROPORTION, HEIGHT_PROPORTION);
		Field[] fields = CAD.class.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			value = "";
			try {
				if (cad != null) {
					Object object = field.get(cad);
					if (!(object instanceof String)) {
						continue;
					}
					value = (String) object;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			PromptTextField text = PromptTextField.newInstance(getResourceMap().getString(field.getName()), value,
					dimension);
			texts.add(text);
		}
		return texts;
	}

	public CAD getCad() {
		return cad;
	}

	@Override
	public double getHorizontalProportion() {
		return 0.95d;
	}

	@Override
	public double getVerticalProportion() {
		return 0.35d;
	}

	@Override
	protected void initialize() {
		logger.info("initialize content...");
		// ~ initialize content
		setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), getResourceMap().getString(TITLE),
						TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

		logger.info("convert to PromptTextField...");
		List<PromptTextField> texts = conver2Texts();

		// ~ performance hGap and vGap
		int hGap = ((Double) (getPreferredSize().width * HGAP_PROPORTION)).intValue();
		int vGap = ((Double) (getPreferredSize().height * VGAP_PROPORTION)).intValue();
		logger.debug("hGap:" + hGap + ",vGap:" + vGap);

		logger.info("use default group layout...");
		DefaultGroupLayout layout = new DefaultGroupLayout(this, hGap, vGap);
		layout.addComponent(texts).layout(3);
	}

	public void setCad(CAD cad) {
		this.cad = cad;
	}
}
