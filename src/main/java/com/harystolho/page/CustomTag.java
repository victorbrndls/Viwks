package com.harystolho.page;

public class CustomTag {

	private String outerHtml;
	private String cssSelector;
	private boolean isVisible;
	
	public CustomTag(String outerHtml, String cssSelector) {
		this.outerHtml = outerHtml;
		this.cssSelector = cssSelector;
		isVisible = true;
	}

	public String getOuterHtml() {
		return outerHtml;
	}

	public void setOuterHtml(String outerHtml) {
		this.outerHtml = outerHtml;
	}

	public String getCssSelector() {
		return cssSelector;
	}

	public void setCssSelector(String cssSelector) {
		this.cssSelector = cssSelector;
	}

	public void setVisible(boolean bool) {
		isVisible = bool;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public String toString() {
		return outerHtml;
	}

}
