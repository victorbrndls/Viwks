package com.harystolho.utils;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

public class VersionComparator implements Comparator<String> {

	@Override
	public int compare(String last, String current) {

		String[] v1 = StringUtils.split(last, ".");
		String[] v2 = StringUtils.split(current, ".");

		int smallestVersion = Math.min(v1.length, v2.length);

		int loops = 0;

		for (int i = 0; i < smallestVersion; i++) {

			loops++;

			int result = v1[i].compareTo(v2[i]);

			if (result == 1) {
				return 1;
			} else if (result == -1) {
				return -1;
			}
		}

		if (loops == Math.max(v1.length, v2.length)) {
			return 0;
		}

		return -1;

	}

}
