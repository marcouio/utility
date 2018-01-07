package com.molinari.utility.servicesloader;

public enum LoaderLevel {
	
	BASE(1), IMPLEMENTED(10), EXTENSION1(100), EXTENSION2(1000);
	
	private final int value;
	LoaderLevel(int level) {
		this.value = level;
	}
	
	public int getValue() {
		return value;
	}
}
