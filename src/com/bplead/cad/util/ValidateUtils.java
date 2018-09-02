package com.bplead.cad.util;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.bplead.cad.bean.io.CAD;
import com.bplead.cad.model.CustomPrompt;

import priv.lee.cad.util.Assert;
import priv.lee.cad.util.PropertiesUtils;
import priv.lee.cad.util.StringUtils;

public class ValidateUtils {

	private static final String EXB = "exb";
	private static final Logger logger = Logger.getLogger(ValidateUtils.class);
	private static final String DETAILNUM_FORMAT = "detail.number.format";
	private static String format;
	static {
		format = PropertiesUtils.readProperty(DETAILNUM_FORMAT);
	}

	public static void checkin(CAD cad) {
		Assert.notNull(cad, "CAD is required");

		logger.info("Validate checkin begin...");

		Assert.hasText(cad.getDetailNum(), CustomPrompt.DETAIL_NUMBER_NULL);

		Assert.isTrue(validateDetailNum(cad.getDetailNum()), CustomPrompt.ERROR_DETAIL_NUMBER_FORMAT);

		if (cad.getDetailNum().equals(cad.getNumber())) {
			Assert.isTrue(!StringUtils.hasText(cad.getJdeNum()), CustomPrompt.JDE_NUMBER_NOT_NULL);
		} else {
			Assert.isTrue(StringUtils.hasText(cad.getJdeNum()), CustomPrompt.JDE_NUMBER_NULL);
		}

		ArrayList<String> attachments = cad.getAttachments();
		Assert.notEmpty(attachments, CustomPrompt.ATTACHMENTS_NULL);

		boolean containsExb = false;
		for (String attachment : attachments) {
			if (attachment.endsWith(EXB)) {
				containsExb = true;
			}

			File file = new File(attachment);
			Assert.isTrue(file.exists(), CustomPrompt.FILE_NOT_EXSIT);
		}

		Assert.isTrue(containsExb, CustomPrompt.MISSING_EXB_FILE);

		logger.info("Validate checkin complete...");
	}

	private static boolean validateDetailNum(String detailNum) {
		return Pattern.compile(format).matcher(detailNum).matches();
	}
}
