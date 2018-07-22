package com.bplead.cad.io.bean;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.bplead.cad.util.XmlUtils;

public class CAD {

	private static final Logger logger = Logger.getLogger(CAD.class);

	public static CAD newInstance(File xml) {
		logger.info("initialize CAD...");
		return XmlUtils.parse(xml, CAD.class);
	}

	private ArrayList<CADLink> detail;
	private String detailNum;
	private String indexPage;
	private String jdeNum;
	private String material;
	private String name;
	private String number;
	private String pageSize;
	private String proportion;
	private String size;
	private String weight;

	public ArrayList<CADLink> getDetail() {
		return detail;
	}

	public String getDetailNum() {
		return detailNum;
	}

	public String getIndexPage() {
		return indexPage;
	}

	public String getJdeNum() {
		return jdeNum;
	}

	public String getMaterial() {
		return material;
	}

	public String getName() {
		return name;
	}

	public String getNumber() {
		return number;
	}

	public String getPageSize() {
		return pageSize;
	}

	public String getProportion() {
		return proportion;
	}

	public String getSize() {
		return size;
	}

	public String getWeight() {
		return weight;
	}

	public void setDetail(ArrayList<CADLink> detail) {
		this.detail = detail;
	}

	public void setDetailNum(String detailNum) {
		this.detailNum = detailNum;
	}

	public void setIndexPage(String indexPage) {
		this.indexPage = indexPage;
	}

	public void setJdeNum(String jdeNum) {
		this.jdeNum = jdeNum;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CAD [detail=");
		builder.append(detail);
		builder.append(", detailNum=");
		builder.append(detailNum);
		builder.append(", indexPage=");
		builder.append(indexPage);
		builder.append(", jdeNum=");
		builder.append(jdeNum);
		builder.append(", material=");
		builder.append(material);
		builder.append(", name=");
		builder.append(name);
		builder.append(", number=");
		builder.append(number);
		builder.append(", pageSize=");
		builder.append(pageSize);
		builder.append(", proportion=");
		builder.append(proportion);
		builder.append(", size=");
		builder.append(size);
		builder.append(", weight=");
		builder.append(weight);
		builder.append("]");
		return builder.toString();
	}
}
