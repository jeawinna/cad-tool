package com.bplead.cad.util;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.bplead.cad.bean.client.Preference;
import com.bplead.cad.bean.io.Attachment;
import com.bplead.cad.bean.io.Document;
import com.bplead.cad.model.CustomPrompt;

import priv.lee.cad.util.Assert;
import priv.lee.cad.util.PropertiesUtils;
import priv.lee.cad.util.StringUtils;

public class ValidateUtils {

	private static final String DETAILNUM_FORMAT = "detail.number.format";
	private static String format;
	private static final Logger logger = Logger.getLogger(ValidateUtils.class);
	private static final String PRIMARY_SUFFIX = "wt.document.primary.file.suffix";
	private static String primarySuffix = PropertiesUtils.readProperty(PRIMARY_SUFFIX);
	static {
		format = PropertiesUtils.readProperty(DETAILNUM_FORMAT);
	}

	public static void checkin(Document document) {
		Assert.notNull(document, "Document is required");

		logger.info("Validate checkin begin...");

		Assert.hasText(document.getCad().getDetailNum(), CustomPrompt.DETAIL_NUMBER_NULL);

		Assert.isTrue(validateDetailNum(document.getCad().getDetailNum()), CustomPrompt.ERROR_DETAIL_NUMBER_FORMAT);

		if (document.getCad().getDetailNum().equals(document.getCad().getNumber())) {
			Assert.isTrue(!StringUtils.hasText(document.getCad().getJdeNum()), CustomPrompt.JDE_NUMBER_NOT_NULL);
		} else {
			Assert.isTrue(StringUtils.hasText(document.getCad().getJdeNum()), CustomPrompt.JDE_NUMBER_NULL);
		}

		List<Attachment> attachments = document.getAttachments();
		Assert.notEmpty(attachments, CustomPrompt.ATTACHMENTS_NULL);

		boolean containsExb = false;
		for (Attachment attachment : attachments) {
			Assert.isTrue(new File(attachment.getAbsolutePath()).exists(), CustomPrompt.FILE_NOT_EXSIT);

			if (attachment.getName().endsWith(primarySuffix)) {
				containsExb = true;
			}
		}
		Assert.isTrue(containsExb, CustomPrompt.MISSING_EXB_FILE);

		Assert.notNull(document.getContainer().getProduct(), CustomPrompt.PRODUCT_NULL);

		Assert.notNull(document.getContainer().getFolder(), CustomPrompt.FOLDER_NULL);

		logger.info("Validate checkin complete...");
	}

	public static void preference() {
		logger.info("Validate preference begin...");

		Preference preference = ClientUtils.temprary.getPreference();

		Assert.notNull(preference, CustomPrompt.PREFERENCE_NULL);

		Assert.notNull(preference.getCaxa(), CustomPrompt.PREFERENCE_CAXA_NULL);

		Assert.isTrue(new File(preference.getCaxa().getCache()).isDirectory(),
				CustomPrompt.ERROR_PREFERENCE_CAXA_CACHE);

		logger.info("Validate preference complete...");
	}

	private static boolean validateDetailNum(String detailNum) {
		return Pattern.compile(format).matcher(detailNum).matches();
	}
}
