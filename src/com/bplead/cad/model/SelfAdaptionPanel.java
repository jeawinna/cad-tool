package com.bplead.cad.model;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import org.apache.log4j.Logger;

import com.bplead.cad.ui.AbstractPanel;
import com.bplead.cad.util.Assert;

public interface SelfAdaptionPanel extends SelfAdaptionComponent {

	static final Logger logger = Logger.getLogger(SelfAdaptionPanel.class);

	@Override
	default void doSelfAdaption(Rectangle parentRec, Component component) {
		Assert.notNull(parentRec, "Parent rectangle must not be null");
		Assert.notNull(component, "Component must not be null");
		Assert.isInstanceOf(AbstractPanel.class, component, "Component must be [AbstractPanel] type");

		double horizontalProportion = getHorizontalProportion();
		double verticalProportion = getVerticalProportion();

		logger.debug("parentRec:" + parentRec + ",horizontalProportion:" + horizontalProportion + ",verticalProportion:"
				+ verticalProportion);
		Assert.isTrue(horizontalProportion > 0 && horizontalProportion <= 1,
				"Horizontal proportion must be greater than 0 less than 1 or equal to 1");
		Assert.isTrue(verticalProportion > 0 && verticalProportion <= 1,
				"Vertical proportion must be greater than 0 less than 1 or equal to 1");

		AbstractPanel panel = (AbstractPanel) component;
		// performance by proportion
		Double width = parentRec.width * getHorizontalProportion();
		Double height = parentRec.height * getVerticalProportion();
		logger.debug("width:" + width + ",height:" + height);
		panel.setPreferredSize(new Dimension(width.intValue(), height.intValue()));
	}
}
