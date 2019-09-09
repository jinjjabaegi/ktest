package com.realtimetech.ktest.filter;

import com.realtimetech.ktest.annotation.Test;

public class GroupFilter extends Filter {

	@Override
	public boolean filter(Test annotation) {
		for (String target : targets) {
			if (target.equals(annotation.testGroupName())) {
				return true;
			}
		}
		return false;
	}
}
