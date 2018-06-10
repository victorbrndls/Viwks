package com.harystolho.task;

public enum TaskUnit {

	SECOND(1000), MINUTE(60 * 1000), HOUR(60 * 60 * 1000), DAY(24 * 60 * 60 * 100);

	int multiplier;

	TaskUnit(int multiplier) {
		this.multiplier = multiplier;
	}

	public int getMultiplier() {
		return this.multiplier;
	}

}
