package com.harystolho.task;

/**
 * A enum to make exchanges among time units
 * 
 * @author Harystolho
 *
 */
public enum TaskUnit {

	SECOND(1000, "Second(s)"), MINUTE(60 * 1000, "Minute(s)"), HOUR(60 * 60 * 1000, "Hour(s)"), DAY(24 * 60 * 60 * 100,
			"Hour(s)");

	int multiplier;
	String name;

	TaskUnit(int multiplier, String name) {
		this.multiplier = multiplier;
		this.name = name;
	}

	public int getMultiplier() {
		return this.multiplier;
	}

	public String getName() {
		return this.name;
	}

}
