package com.bplead.cad.util;

import java.util.List;

import com.bplead.cad.bean.SimpleDocument;
import com.bplead.cad.bean.SimpleFolder;
import com.bplead.cad.bean.SimplePdmLinkProduct;
import com.bplead.cad.bean.client.Temporary;
import com.bplead.cad.bean.constant.RemoteMethod;

import priv.lee.cad.util.Assert;
import priv.lee.cad.util.ClientInstanceUtils;
import priv.lee.cad.util.StringUtils;

public class ClientUtils extends ClientInstanceUtils {

	public static Temporary temprary = new Temporary();

	public static SimpleFolder getSimpleFolders(SimplePdmLinkProduct product) {
		return invoke(RemoteMethod.GET_SIMPLE_FOLDERS, new Class<?>[] { SimplePdmLinkProduct.class },
				new Object[] { product }, SimpleFolder.class);
	}

	@SuppressWarnings("unchecked")
	public static List<SimplePdmLinkProduct> getSimplePdmLinkProducts() {
		return invoke(RemoteMethod.GET_SIMPLE_PRODUCTS, null, null, List.class);
	}

	@SuppressWarnings("unchecked")
	public static List<SimpleDocument> search(String number, String name) {
		Assert.isTrue(StringUtils.hasText(number) || StringUtils.hasText(name), "Number or name is requried");
		return invoke(RemoteMethod.SEARCH, new Class<?>[] { String.class, String.class }, new Object[] { number, name },
				List.class);
	}
}
