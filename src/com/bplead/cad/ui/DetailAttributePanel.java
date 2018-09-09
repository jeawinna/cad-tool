package com.bplead.cad.ui;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.bplead.cad.bean.io.CAD;
import com.bplead.cad.bean.io.CADLink;

import priv.lee.cad.ui.AbstractPanel;
import priv.lee.cad.util.Assert;

public class DetailAttributePanel extends AbstractPanel {

	private static final Logger logger = Logger.getLogger(DetailAttributePanel.class);
	private static final long serialVersionUID = -206359105088128179L;
	private CAD cad;
	private String[][] datas;
	private String[] names;
	private double TABLE_HEIGTH_PROPORTION = 0.9d;
	private double TABLE_WIDTH_PROPORTION = 0.98d;
	private final String TITLE = "title";

	public DetailAttributePanel(CAD cad) {
		this.cad = cad;
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
		return 0.45d;
	}

	@Override
	public void initialize() {
		logger.info("initialize content...");
		// ~ initialize content
		setBorder(
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), getResourceMap().getString(TITLE),
						TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, toolkit.getFont()));

		logger.info("initialize detail table...");
		initTableData(cad.getDetail());
		Dimension dimension = getPreferredSize();
		JScrollPane sp = new JScrollPane(new JTable(datas, names));
		sp.setPreferredSize(new Dimension((int) (dimension.width * TABLE_WIDTH_PROPORTION),
				(int) (dimension.height * TABLE_HEIGTH_PROPORTION)));
		add(sp);
	}

	private void initTableData(List<CADLink> links) {
		Field[] fields = CADLink.class.getDeclaredFields();
		names = new String[fields.length];
		datas = new String[links == null || links.isEmpty() ? 0 : links.size()][names.length];
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			String name = getResourceMap().getString(field.getName());
			Assert.hasText(name, "Table column name[" + field.getName() + "] must not be null");
			names[i] = name;
			if (links == null || links.isEmpty()) {
				continue;
			}
			for (int j = 0; j < links.size(); j++) {
				CADLink link = links.get(j);
				Object value = "";
				try {
					value = field.get(link);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.debug(name + "=" + value);
				datas[j][i] = String.valueOf(value);
			}
		}
	}

	public void setCad(CAD cad) {
		this.cad = cad;
	}
}
