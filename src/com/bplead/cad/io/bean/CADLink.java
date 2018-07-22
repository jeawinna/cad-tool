package com.bplead.cad.io.bean;

public class CADLink {

	private String description;
	private String jdeNum;
	private String material;
	private String name;
	private String number;
	private String order;
	private String quantity;
	private String singleWeight;
	private String weight;

	public CADLink() {

	}

	public String getDescription() {
		return description;
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

	public String getOrder() {
		return order;
	}

	public String getQuantity() {
		return quantity;
	}

	public String getSingleWeight() {
		return singleWeight;
	}

	public String getWeight() {
		return weight;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public void setOrder(String order) {
		this.order = order;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public void setSingleWeight(String singleWeight) {
		this.singleWeight = singleWeight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CADLink [description=");
		builder.append(description);
		builder.append(", jdeNum=");
		builder.append(jdeNum);
		builder.append(", material=");
		builder.append(material);
		builder.append(", name=");
		builder.append(name);
		builder.append(", number=");
		builder.append(number);
		builder.append(", order=");
		builder.append(order);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", singleWeight=");
		builder.append(singleWeight);
		builder.append(", weight=");
		builder.append(weight);
		builder.append("]");
		return builder.toString();
	}
}
