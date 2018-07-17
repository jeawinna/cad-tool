package com.bplead.cad.ui;

import java.awt.Container;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.io.bean.CAD;
import com.bplead.cad.layout.DefaultGroupLayout;
import com.bplead.cad.ui.PromptTextField.PromptTextFieldDimension;
import com.bplead.cad.util.PropertiesUtils;
import com.bplead.cad.util.XmlUtils;

public class BasicAttributePanel extends AbstractPanel {

	private static final Logger logger = Logger.getLogger(BasicAttributePanel.class);
	private static final long serialVersionUID = 5723039852386303330L;
	private CAD cad;
	private final String CAD_REPOSITORY = "cad.xml.repository";
	private final double HEIGHT_PROPORTION = 0.1d;
	private final double HGAP_PROPORTION = 0.02d;
	private final double LABEL_PROPORTION = 0.1d;
	private final double TEXT_PROPORTION = 0.4d;
	private final String TITLE = "title";
	private final double VGAP_PROPORTION = 0.05d;

	public BasicAttributePanel(Container parent) {
		super(parent);
	}

	private List<PromptTextField> conver2Texts() {
		// ~ reflect String type fields and convert to PromptTextField type
		List<PromptTextField> texts = new ArrayList<PromptTextField>();
		String value = "";
		PromptTextFieldDimension dimension = new PromptTextField().new PromptTextFieldDimension(getPreferredSize(),
				LABEL_PROPORTION, TEXT_PROPORTION, HEIGHT_PROPORTION);
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
			PromptTextField text = new PromptTextField(getResourceMap().getString(getResourceName(field.getName())),
					value, dimension);
			texts.add(text);
		}
		return texts;
	}

	@Override
	public double getHorizontalProportion() {
		return 0.95d;
	}

	@Override
	public double getVerticalProportion() {
		return 0.35d;
	}

	private void initCAD() {
		File xml = new File(XmlUtils.class.getResource(PropertiesUtils.readProperty(CAD_REPOSITORY)).getPath());
		this.cad = XmlUtils.parse(xml, CAD.class);
		logger.debug("cad:" + cad);
	}

	@Override
	protected void initialize() {
		logger.info("initialize CAD...");
		initCAD();

		logger.info("initialize content...");
		// ~ initialize content
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				getResourceMap().getString(getResourceName(TITLE)), TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

		logger.info("convert to PromptTextField...");
		List<PromptTextField> texts = conver2Texts();

		// ~ use default group layout
		int hGap = ((Double) (getPreferredSize().width * HGAP_PROPORTION)).intValue();
		int vGap = ((Double) (getPreferredSize().height * VGAP_PROPORTION)).intValue();
		logger.debug("hGap:" + hGap + ",vGap:" + vGap);

		DefaultGroupLayout layout = new DefaultGroupLayout(this, hGap, vGap);
		layout.addComponent(texts).layout();
	}
}
