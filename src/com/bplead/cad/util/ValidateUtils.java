package com.bplead.cad.util;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.bplead.cad.bean.SimpleFolder;
import com.bplead.cad.bean.SimplePdmLinkProduct;
import com.bplead.cad.bean.client.Preference;
import com.bplead.cad.bean.io.Attachment;
import com.bplead.cad.bean.io.CAD;
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

	public static void validateCaxaCache(String caxaCache) {
		Assert.hasText(caxaCache, CustomPrompt.ERROR_PREFERENCE_CAXA_CACHE);
		Assert.isTrue(new File(caxaCache).isDirectory(), CustomPrompt.ERROR_PREFERENCE_CAXA_CACHE);
	}

	public static void validateCaxaExe(String caxaExe) {
		Assert.hasText(caxaExe, CustomPrompt.PREFERENCE_CAXA_EXE_NULL);
		Assert.isTrue(new File(caxaExe).exists(), CustomPrompt.ERROR_PREFERENCE_CAXA_EXE);

	}

	public static void validateCheckin(Document document) {
		Assert.notNull(document, "Document is required");

		logger.info("Validate checkin begin...");

		List<Attachment> attachments = document.getAttachments();
		Assert.notEmpty(attachments, CustomPrompt.ATTACHMENTS_NULL);

		if (ClientUtils.StartArguments.CAD.equals(ClientUtils.args.getType())) {
			CAD cad = (CAD) document.getObject();

			Assert.hasText(cad.getDetailNum(), CustomPrompt.DETAIL_NUMBER_NULL);

			Assert.isTrue(validateDetailNum(cad.getDetailNum()), CustomPrompt.ERROR_DETAIL_NUMBER_FORMAT);
			if (cad.getDetailNum().equals(cad.getNumber())) {
				Assert.isTrue(!StringUtils.hasText(cad.getJdeNum()), CustomPrompt.JDE_NUMBER_NOT_NULL);
			} else {
				Assert.isTrue(StringUtils.hasText(cad.getJdeNum()), CustomPrompt.JDE_NUMBER_NULL);
			}

			validateExb(attachments);
		}

		validateProduct(document.getContainer().getProduct());

		validateFolder(document.getContainer().getFolder());

		validateType(document.getType());

		logger.info("Validate checkin complete...");
	}

	public static boolean validateDetailNum(String detailNum) {
		return Pattern.compile(format).matcher(detailNum).matches();
	}

	public static void validateExb(List<Attachment> attachments) {
		boolean containsExb = false;
		for (Attachment attachment : attachments) {
			Assert.isTrue(new File(attachment.getAbsolutePath()).exists(), CustomPrompt.FILE_NOT_EXSIT);

			if (attachment.getName().endsWith(primarySuffix)) {
				containsExb = true;
			}
		}
		Assert.isTrue(containsExb, CustomPrompt.MISSING_EXB_FILE);
	}

	public static void validateFolder(SimpleFolder folder) {
		Assert.notNull(folder, CustomPrompt.FOLDER_NULL);
	}

	public static void validatePreference() {
		logger.info("Validate preference begin...");

		Preference preference = ClientUtils.temprary.getPreference();

		Assert.notNull(preference, CustomPrompt.PREFERENCE_NULL);

		Assert.notNull(preference.getCaxa(), CustomPrompt.PREFERENCE_CAXA_NULL);

		validateCaxaCache(preference.getCaxa().getCache());

		validateCaxaExe(preference.getCaxa().getLocation());

		logger.info("Validate preference complete...");
	}

	public static void validateProduct(SimplePdmLinkProduct product) {
		Assert.notNull(product, CustomPrompt.PRODUCT_NULL);
	}

	public static void validateType(String type) {
		Assert.hasText(type, CustomPrompt.DOC_TYPE_NULL);
	}

	public static void validateUrl(String url) {
		Assert.hasText(url, CustomPrompt.URL_NULL);
	}
}
