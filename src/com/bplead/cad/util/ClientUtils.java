package com.bplead.cad.util;

import java.util.List;

import com.bplead.cad.bean.SimplePdmLinkProduct;
import com.bplead.cad.bean.client.Temporary;
import com.bplead.cad.bean.constant.RemoteMethod;

import priv.lee.cad.util.ClientInstanceUtils;

public class ClientUtils extends ClientInstanceUtils {

	public static Temporary temprary = new Temporary();

	@SuppressWarnings("unchecked")
	public static List<SimplePdmLinkProduct> getSimplePdmLinkProducts() {
		return invoke(RemoteMethod.GET_SIMPLE_PRODUCT, null, null, List.class);
	}
}
