package com.bplead.cad.io.bean;

public class CADLink extends CAD {

	private String description;
	private String order;
	private String quantity;
	private String singleWeight;

	public CADLink() {

	}

	public String getDescription() {
		return description;
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

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CADLink [quantity=");
		builder.append(quantity);
		builder.append(", number=");
		builder.append(getNumber());
		builder.append(", name=");
		builder.append(getName());
		builder.append(", weight=");
		builder.append(getWeight());
		builder.append(", jdeNum=");
		builder.append(getJdeNum());
		builder.append(", description=");
		builder.append(description);
		builder.append(", singleWeight=");
		builder.append(singleWeight);
		builder.append(", order=");
		builder.append(order);
		builder.append("]");
		return builder.toString();
	}
}
