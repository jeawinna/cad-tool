package com.bplead.cad.model;

import java.awt.Component;
import java.awt.Dimension;

import org.apache.log4j.Logger;

import com.bplead.cad.ui.AbstractPanel;
import com.bplead.cad.util.Assert;

public interface SelfAdaptionPanel extends SelfAdaptionComponent {

	static final Logger logger = Logger.getLogger(SelfAdaptionPanel.class);

	@Override
	default void doSelfAdaption(Cloneable cloneable, Component component) {
		Assert.notNull(cloneable, "Cloneable must not be null");
		Assert.notNull(component, "Component must not be null");
		Assert.isInstanceOf(AbstractPanel.class, component, "Component must extends AbstractPanel");
		Assert.isInstanceOf(Dimension.class, cloneable, "Cloneable must extends Dimension");

		double horizontalProportion = getHorizontalProportion();
		double verticalProportion = getVerticalProportion();
		Dimension dimension = (Dimension) cloneable;

		logger.debug("cloneable:" + dimension + ",horizontalProportion:" + horizontalProportion + ",verticalProportion:"
				+ verticalProportion);
		Assert.isTrue(horizontalProportion > 0 && horizontalProportion <= 1,
				"Horizontal proportion must be greater than 0 less than 1 or equal to 1");
		Assert.isTrue(verticalProportion > 0 && verticalProportion <= 1,
				"Vertical proportion must be greater than 0 less than 1 or equal to 1");

		AbstractPanel panel = (AbstractPanel) component;
		// performance by proportion
		Double width = dimension.width * getHorizontalProportion();
		Double height = dimension.height * getVerticalProportion();
		logger.debug(panel + ",width:" + width + ",height:" + height);
		panel.setPreferredSize(new Dimension(width.intValue(), height.intValue()));
	}
}
