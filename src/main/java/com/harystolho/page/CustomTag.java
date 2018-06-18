package com.harystolho.page;

import java.util.HashSet;
import java.util.Set;

public class CustomTag {

	private String outerHtml;
	private String cssSelector;
	private Set<String> classes;
	private String id;
	private boolean isVisible;

	public CustomTag(String outerHtml, String cssSelector) {
		this.outerHtml = outerHtml;
		this.cssSelector = cssSelector;
		classes = new HashSet<>();
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

	public Set<String> getClasses() {
		return classes;
	}

	public void setClasses(Set<String> classes) {
		classes.clear();

		classes.forEach((item) -> {
			this.classes.add("." + item);
		});
	}

	public void addClasses(Set<String> classes) {
		classes.forEach((item) -> {
			this.classes.add("." + item);
		});
	}

	public void addClass(String text) {
		classes.add("." + text);
	}

	public void setId(String id) {
		if (!id.isEmpty()) {
			this.id = "#" + id;
		}
	}

	public String getId() {
		return id;
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
