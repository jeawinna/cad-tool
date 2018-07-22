package com.bplead.cad.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bplead.cad.io.bean.CAD;

public class XmlUtils {

	private static final String GET_METHOD_PREFIX = "get";
	private static final Logger logger = Logger.getLogger(XmlUtils.class);
	private static final String SET_METHOD_PREFIX = "set";

	private static Field findField(Class<?> clazz, String name) {
		Field field = null;
		try {
			field = clazz.getDeclaredField(name);
		} catch (Exception e) {
			logger.warn("Class:" + clazz + " found no field named:" + name);
		} finally {
			if (field != null) {
				field.setAccessible(true);
				return field;
			}

			Class<?> superClass = clazz.getSuperclass();
			logger.debug("Loop superClass:" + superClass + " field...");
			if (!Object.class.equals(superClass)) {
				field = findField(superClass, name);
			}
		}
		return field;
	}

	private static Method findMethod(Object object, String name, Object value)
			throws NoSuchMethodException, SecurityException {
		if (value == null) {
			return object.getClass().getMethod(name);
		} else {
			return object.getClass().getMethod(name, value.getClass());
		}
	}

	private static Object getValue(Object object, Method method)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		method.setAccessible(true);
		return method.invoke(object);
	}

	public static void main(String[] args) {
		File xml = new File(XmlUtils.class.getResource(PropertiesUtils.readProperty("cad.xml.repository")).getPath());
		logger.debug(XmlUtils.parse(xml, CAD.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <T> T parse(Element element, T object) {
		if (element == null || object == null) {
			return null;
		}

		// ~ reflect to convert *.xml file to JavaBean
		List<Element> elements = (List<Element>) element.elements();
		for (Element subElement : elements) {
			try {
				Field field = findField(object.getClass(), subElement.getName());
				if (field == null) {
					continue;
				}
				logger.debug("field name:" + subElement.getName() + ",actual field:" + field);
				String name = field.getName();
				Type type = field.getGenericType();
				Object value = null;
				if (type instanceof ParameterizedType) {
					Class<?> actualType = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
					ArrayList list = (ArrayList) getValue(object,
							findMethod(object, GET_METHOD_PREFIX + uppcase(name), null));
					if (list == null) {
						list = new ArrayList();
					}
					list.add(parse(subElement, actualType.newInstance()));
					value = list;
				} else {
					value = subElement.getText();
				}
				logger.debug("name:" + name + ",type:" + type + ",value:" + value);
				if (value == null) {
					continue;
				}
				setValue(object, findMethod(object, SET_METHOD_PREFIX + uppcase(name), value), value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return object;
	}

	public static <T> T parse(File xml, Class<T> clatt) {
		Assert.notNull(xml, "Xml file is required");
		logger.debug("xml:" + xml + ",clatt:" + clatt);
		try {
			Document document = new SAXReader().read(xml);
			T object = clatt.newInstance();
			return parse(document.getRootElement(), object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void setValue(Object object, Method method, Object value)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		method.setAccessible(true);
		method.invoke(object, value);
	}

	private static String uppcase(String name) {
		byte[] bytes = name.getBytes();
		bytes[0] = (byte) ((char) bytes[0] - 'a' + 'A');
		return new String(bytes);
	}
}
