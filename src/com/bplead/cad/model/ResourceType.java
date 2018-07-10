package com.bplead.cad.model;

public enum ResourceType {

	TITLE("title"), TEXT("text"), SHORTDESCRIPTION("shortDescription");

	String type;

	ResourceType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
