package com.bplead.cad.model;

import java.awt.Component;
import java.awt.Rectangle;

import org.apache.log4j.Logger;

import com.bplead.cad.util.Assert;

public interface SelfAdaptionComponent {

	static final Logger logger = Logger.getLogger(SelfAdaptionComponent.class);

	public double getHorizontalProportion();

	public double getVerticalProportion();

	default void doSelfAdaption(Rectangle rec, Component component) {
		Assert.notNull(rec, "Rectangle must not be null");
		Assert.notNull(component, "Component must not be null");

		double horizontalProportion = getHorizontalProportion();
		double verticalProportion = getVerticalProportion();

		logger.debug("rec:" + rec + ",horizontalProportion:" + horizontalProportion + ",verticalProportion:"
				+ verticalProportion);
		Assert.isTrue(horizontalProportion > 0 && horizontalProportion <= 1,
				"Horizontal proportion must be greater than 0 less than 1 or equal to 1");
		Assert.isTrue(verticalProportion > 0 && verticalProportion <= 1,
				"Vertical proportion must be greater than 0 less than 1 or equal to 1");

		// performance by proportion
		Double width = rec.width * horizontalProportion;
		Double height = rec.height * verticalProportion;
		Double x = (rec.width - width) / 2;
		Double y = (rec.height - height) / 2;
		logger.debug("x:" + x + ",y:" + y + ",width:" + width + ",height:" + height);
		component.setBounds(new Rectangle(x.intValue(), y.intValue(), width.intValue(), height.intValue()));
	}
}
