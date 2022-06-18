package com.molinari.utility.graphic;

public class PercentageDimension {

	private boolean percentage;
	private int width;
	private int height;
	
	public PercentageDimension() {
		
	}
	
	public PercentageDimension(boolean percentage, int width, int height) {
		super();
		this.percentage = percentage;
		this.width = width;
		this.height = height;
	}
	public boolean isPercentage() {
		return percentage;
	}
	public void setPercentage(boolean percentage) {
		this.percentage = percentage;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
}
