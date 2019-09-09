package com.realtimetech.ktest.filter;

import com.realtimetech.ktest.annotation.Test;

public class IgnoreFilter extends Filter{

	@Override
	public boolean filter(Test annotation) {
		for(String target : this.targets) {
			if(target.equals(annotation.testName())) {
				return false;
			}
		}
		return true;
	}

}
