package com.bplead.cad.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Properties;

import com.bplead.cad.bean.DataContent;
import com.bplead.cad.bean.SimpleDocument;
import com.bplead.cad.bean.SimpleFolder;
import com.bplead.cad.bean.SimplePdmLinkProduct;
import com.bplead.cad.bean.client.Temporary;
import com.bplead.cad.bean.constant.RemoteMethod;
import com.bplead.cad.bean.io.Attachment;
import com.bplead.cad.bean.io.AttachmentModel;
import com.bplead.cad.bean.io.Document;
import com.bplead.cad.model.CustomPrompt;

import priv.lee.cad.util.ClientAssert;
import priv.lee.cad.util.ClientInstanceUtils;
import priv.lee.cad.util.PropertiesUtils;
import priv.lee.cad.util.StringUtils;

public class ClientUtils extends ClientInstanceUtils implements FilenameFilter {

	public static StartArguments args = new StartArguments();
	private static final String CONFIG_SUFFIX = "wt.document.config.file.suffix";
	private static final String OID = "oid";
	public static Temporary temprary = new Temporary();

	public static List<Attachment> buildAttachments(AttachmentModel model, String primarySuffix) {
		List<Attachment> attachments = model.getAttachments();
		for (Attachment attachment : attachments) {
			File file = new File(attachment.getAbsolutePath());
			if (file.getName().endsWith(ClientUtils.getConfigFileSuffix())) {
				continue;
			}

			attachment.setName(file.getName());
			attachment.setPrimary(file.getName().endsWith(primarySuffix));
		}
		return attachments;
	}

	public static boolean checkin(Document document) {
		ClientAssert.notNull(document, "Document is required");
		return invoke(RemoteMethod.CHECKIN, new Class<?>[] { Document.class }, new Object[] { document },
				Boolean.class);
	}

	public static DataContent download(List<SimpleDocument> documents) {
		ClientAssert.notEmpty(documents, "Documents is requried");
		return invoke(RemoteMethod.DOWNLOAD, new Class<?>[] { List.class }, new Object[] { documents },
				DataContent.class);
	}

	public static String getConfigFileSuffix() {
		return PropertiesUtils.readProperty(CONFIG_SUFFIX);
	}

	public static String getDocumentOid() {
		try {
			File directory = new File(temprary.getPreference().getCaxa().getCache());
			File[] files = directory.listFiles(new ClientUtils());
			if (files != null && files.length > 0) {
				ClientAssert.isTrue(files.length == 1, CustomPrompt.ONLY_ONE_PROPERTIES);

				Properties properties = new Properties();
				properties.load(new FileInputStream(files[0]));
				return properties.getProperty(OID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static SimpleFolder getSimpleFolders(SimplePdmLinkProduct product) {
		ClientAssert.notNull(product, "Product is requried");
		return invoke(RemoteMethod.GET_SIMPLE_FOLDERS, new Class<?>[] { SimplePdmLinkProduct.class },
				new Object[] { product }, SimpleFolder.class);
	}

	@SuppressWarnings("unchecked")
	public static List<SimplePdmLinkProduct> getSimplePdmLinkProducts() {
		return invoke(RemoteMethod.GET_SIMPLE_PRODUCTS, null, null, List.class);
	}

	@SuppressWarnings("unchecked")
	public static List<SimpleDocument> search(String number, String name) {
		ClientAssert.isTrue(StringUtils.hasText(number) || StringUtils.hasText(name), "Number or name is requried");
		return invoke(RemoteMethod.SEARCH, new Class<?>[] { String.class, String.class }, new Object[] { number, name },
				List.class);
	}

	@Override
	public boolean accept(File dir, String name) {
		return new File(dir, name).isFile() && name.endsWith(ClientUtils.getConfigFileSuffix());
	}

	public static class StartArguments {

		public static final String CAD = "cad";
		public static final String CAPP = "capp";
		private String type;

		public StartArguments() {

		}

		public StartArguments(String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
}
